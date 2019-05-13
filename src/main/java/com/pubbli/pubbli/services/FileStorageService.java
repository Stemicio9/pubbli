package com.pubbli.pubbli.services;


import com.pubbli.pubbli.exceptions.FileStorageException;
import com.pubbli.pubbli.exceptions.MyFileNotFoundException;
import com.pubbli.pubbli.model.Foto;
import com.pubbli.pubbli.model.Tuttifile;
import com.pubbli.pubbli.model.Video;
import com.pubbli.pubbli.properties.FileStorageProperties;
import com.pubbli.pubbli.repository.FotoRepository;
import com.pubbli.pubbli.repository.TuttifileRepository;
import com.pubbli.pubbli.repository.VideoRepository;
import io.humble.video.*;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.humble.video.awt.MediaPictureConverterFactory.convertToType;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private final Path fotoStorageLocation;

    private final Path videoStorageLocation;

    private final Path tuttifileStorageLocation;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TuttifileRepository tuttifileRepository;


    List<Path> listaStorageLocations = new LinkedList<>();

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {


        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.fotoStorageLocation= Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        this.videoStorageLocation=  Paths.get(fileStorageProperties.getUploadDirVideo())
                .toAbsolutePath().normalize();

        this.tuttifileStorageLocation = Paths.get(fileStorageProperties.getTuttifile())
                .toAbsolutePath().normalize();

        this.listaStorageLocations.add(Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize());
        this.listaStorageLocations.add(Paths.get(fileStorageProperties.getUploadDirVideo())
                .toAbsolutePath().normalize());

        this.listaStorageLocations.add(fotoStorageLocation);
        this.listaStorageLocations.add(videoStorageLocation);
        this.listaStorageLocations.add(tuttifileStorageLocation);

        try {
          //  Files.createDirectories(this.fileStorageLocation);
            for(Path path : listaStorageLocations){
                Files.createDirectories(path);
            }

        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {  //FOTO
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());




        System.out.println("FILE NAME = "+fileName);

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fotoStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String pathtotale=targetLocation.toString();

            Foto f=new Foto();
            f.setUrlFoto(pathtotale);
            fotoRepository.save(f);
            System.out.println("FOTO = "+f.getIdFoto());
            long id=f.getIdFoto();

            CompletableFuture.runAsync(()-> {
                        trasformaFotoInVideoNuovo(file,id);
                    });
            //TrasformaFotoinVideo
            //SalvainTUTTIFILE

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public void trasformafotoinvideo(MultipartFile file ){

        String nuovonome=this.tuttifileStorageLocation.toString()+StringUtils.cleanPath(file.getOriginalFilename());;




        final Muxer muxer= Muxer.make(nuovonome,null,null);

        final MuxerFormat format = muxer.getFormat();



        System.out.println("FORMAT = "+format);

        final Codec codec = Codec.findEncodingCodec(format.getDefaultVideoCodecId());

        final Rational framerate= Rational.make(1,1);

        final double duration=15;



        Encoder encoder = Encoder.make(codec);

        encoder.setWidth(200);
        encoder.setHeight(150);
        // We are going to use 420P as the format because that's what most video formats these days use
        final PixelFormat.Type pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
        encoder.setPixelFormat(pixelformat);
        encoder.setTimeBase(framerate);

        try {

            /** Open the encoder. */
            encoder.open(null, null);


            /** Add this stream to the muxer. */
            muxer.addNewStream(encoder);

            /** And open the muxer for business. */
            muxer.open(null, null);

            MediaPictureConverter converter = null;
            final MediaPicture picture = MediaPicture.make(
                    encoder.getWidth(),
                    encoder.getHeight(),
                    pixelformat);
            picture.setTimeBase(framerate);

            final MediaPacket packet = MediaPacket.make();

            for (int i = 0; i < duration / framerate.getDouble(); i++) {
                /** Make the screen capture && convert image to TYPE_3BYTE_BGR */
                InputStream in = new ByteArrayInputStream(file.getBytes());
                BufferedImage immaine = ImageIO.read(in);

                final BufferedImage screen = convertToType(immaine, BufferedImage.TYPE_3BYTE_BGR);

                /** This is LIKELY not in YUV420P format, so we're going to convert it using some handy utilities. */
                if (converter == null)
                    converter = MediaPictureConverterFactory.createConverter(screen, picture);
                converter.toPicture(picture, screen, i);

                do {
                    encoder.encode(packet, picture);
                    if (packet.isComplete())
                        muxer.write(packet, false);
                } while (packet.isComplete());

                /** now we'll sleep until it's time to take the next snapshot. */
                Thread.sleep((long) (1000 * framerate.getDouble()));
            }

            do {
                encoder.encode(packet, null);
                if (packet.isComplete())
                    muxer.write(packet,  false);
            } while (packet.isComplete());

            /** Finally, let's clean up after ourselves. */
            muxer.close();



        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void trasformaFotoInVideoNuovo(MultipartFile file,long id){
        ArrayList<String> links = new ArrayList<>();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String name= getFileNameWithoutExtension(fileName);



        Path targetLocation = this.fotoStorageLocation.resolve(fileName);
        String pathtotale = targetLocation.toAbsolutePath().toString() ;

        System.out.println("PATH TOTALE" + pathtotale);
        links.add(pathtotale);



        String nuovonome = this.tuttifileStorageLocation.toAbsolutePath().toString() +"\\"+ name+".mp4" ;



        System.out.println("NUOVO NOME" + nuovonome);

        convertJPGtoMovie(links,nuovonome);

        Tuttifile tf=new Tuttifile();
        tf.setIdTuttifile(id);
        tf.setUrlTuttifile(nuovonome);
        tuttifileRepository.save(tf);

    }

    private static String getFileNameWithoutExtension(String file) {
        String fileName = "";

        try {

                String name = file;
                fileName = name.replaceFirst("[.][^.]+$", "");

        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }

        return fileName;

    }

    public static void convertJPGtoMovie(ArrayList<String> links, String vidPath)
    {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath,640,720);
        try {
            recorder.setFrameRate(1);
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
            recorder.setFormat("mp4");
            recorder.setVideoQuality(0); // maximum quality
            recorder.start();
            for (int i=0;i<15;i++)
            {
                recorder.record(grabberConverter.convert(cvLoadImage(links.get(0))));
            }
            recorder.stop();
        }
        catch (org.bytedeco.javacv.FrameRecorder.Exception e){
            e.printStackTrace();
        }
    }

    public String getflepath(String nomefile){
        Path targetLocation = this.tuttifileStorageLocation.resolve(nomefile);
        return targetLocation.toAbsolutePath().toString();
    }

    public String storeVideo(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.videoStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Path targetLocation1 = this.tuttifileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation1, StandardCopyOption.REPLACE_EXISTING);
            String pathtotale=targetLocation.toString();

            Video v = new Video();
            v.setUrlVideo(pathtotale);
            videoRepository.save(v);

            String nuovonome = this.tuttifileStorageLocation.toAbsolutePath().toString()+"\\"+fileName;

            Tuttifile tf=new Tuttifile();
            tf.setUrlTuttifile(nuovonome);
            tf.setIdTuttifile(v.getIdVideo());
            tuttifileRepository.save(tf);


            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeTuttiFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.tuttifileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fotoStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public Resource loadFileAsResourceDaTuttiFile(String fileName) {
        try {
            Path filePath = this.tuttifileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }


    public List<Tuttifile> getAllPhoto(){
        List<Tuttifile> result = new LinkedList<>();
        try{

            File folder = fotoStorageLocation.toFile();
            File[] listOfFiles = folder.listFiles();


            for (int i = 0; i < listOfFiles.length; i++) {



                if (listOfFiles[i].isFile()) {

                    Path path = this.tuttifileStorageLocation.resolve(listOfFiles[i].getName());

                    String url= path.toString();

                    Tuttifile tf=new Tuttifile();

                    tf.setUrlTuttifile(listOfFiles[i].getName());

                    tf.setIdTuttifile(getIdFromUrl(url));

                    result.add(tf);
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }


            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public long getIdFromUrl(String url){
        try {
            System.out.println(url);
            Tuttifile tf = tuttifileRepository.findByUrlTuttifile(url);
            return tf.getIdTuttifile();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }


    public List<Tuttifile> getAllVideo(){
        List<Tuttifile> result = new LinkedList<>();
        try{

            File folder = videoStorageLocation.toFile();
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    Path path = this.tuttifileStorageLocation.resolve(listOfFiles[i].getName());

                    String url= path.toString();

                    Tuttifile tf=new Tuttifile();

                    tf.setUrlTuttifile(listOfFiles[i].getName());

                    tf.setIdTuttifile(getIdFromUrl(url));

                    result.add(tf);
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }


            return result;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }





}
