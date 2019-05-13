package com.pubbli.pubbli.controller;

import com.pubbli.pubbli.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
public class FileProva {

    @Autowired
    FileStorageService fileStorageService;

    @GetMapping(value ="/BBB/{fileName:.+}", produces = "video/mp4")
    public ResponseEntity<StreamingResponseBody> BBB(@PathVariable String fileName) {



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

        byte[] buffer = new byte[1024*10];
        while (true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1)
                break;
            out.write(buffer, 0, bytesRead);
        }
    }

}
