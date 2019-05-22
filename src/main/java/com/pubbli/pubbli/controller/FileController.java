package com.pubbli.pubbli.controller;

import com.pubbli.pubbli.model.Foto;
import com.pubbli.pubbli.model.Tuttifile;
import com.pubbli.pubbli.payload.UploadFileResponse;
import com.pubbli.pubbli.repository.FotoRepository;
import com.pubbli.pubbli.repository.TuttifileRepository;
import com.pubbli.pubbli.repository.VideoRepository;
import com.pubbli.pubbli.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin()
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TuttifileRepository tuttifileRepository;

    @PostMapping("/uploadFile") //FOTO
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadVideo") //Video
    public UploadFileResponse uploadVideo(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeVideo(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }


    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/downloadFileDaFoto/{fileName:.+}")
    public ResponseEntity<Resource> downloadDaFoto(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        String nomecompleto = fileName + ".mp4";

        Resource resource = fileStorageService.loadFileAsResource(fileName);



        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/downloadFileDaTuttiFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadDaTuttiFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        String nomecompleto = fileName + ".mp4";

        Resource resource = fileStorageService.loadFileAsResourceDaTuttiFile(fileName);



        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(value ="/AAA/{fileName:.+}", produces = "video/mp4")
    public ResponseEntity<StreamingResponseBody> AAA(@PathVariable String fileName) {



        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            Resource resource = fileStorageService.loadFileAsResourceDaTuttiFile(fileName);
            InputStream st = resource.getInputStream();

            StreamingResponseBody s = new StreamingResponseBody() {
                @Override
                public void writeTo(OutputStream outputStream) throws IOException {
                    copiastream(st, outputStream);
                }
            };
            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(s);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }





    public void copiastream(InputStream in, OutputStream out) throws IOException {

        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1)
                break;
            out.write(buffer, 0, bytesRead);
        }
    }

    @RequestMapping(value = "/scarica/{filename:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> provanonstreaming(@PathVariable String filename, HttpServletResponse response) {
        ResponseEntity<byte[]> result = null;
        try {
            //Resource resource = fileStorageService.loadFileAsResourceDaTuttiFile(filename);

            String percorso = fileStorageService.getflepath(filename);
            Path path = Paths.get(percorso);
            byte[] video =  Files.readAllBytes(path);

            response.setStatus(HttpStatus.OK.value());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(video.length);
            result = new ResponseEntity<byte[]>(video, headers, HttpStatus.OK);

        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return result;
    }

    @GetMapping("/getallfoto")
    public List<Foto> getAllPhoto(){
        return fileStorageService.getAllPhoto();
    }

    @GetMapping("/getallfotoinvideo")
    public List<Tuttifile> getAllPhotoinVideo(){
        return fileStorageService.getAllPhotoinVideo();
    }


    @GetMapping("/getallvideo")
    public List<Tuttifile> getAllVideo(){
        return fileStorageService.getAllVideo();
    }

    @GetMapping(value = "/videosrc/{fileName:.+}", produces = "video/mp4")
    @ResponseBody
    public FileSystemResource videoSource(@PathVariable String fileName) {
        String path = fileStorageService.getflepath(fileName);
        System.out.println(path);
        return new FileSystemResource(new File(path));
    }
}
