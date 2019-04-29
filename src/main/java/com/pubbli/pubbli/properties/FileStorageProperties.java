package com.pubbli.pubbli.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {


    private String uploadDir;

    private String uploadDirVideo;

    private String tuttifile;

    public String getTuttifile() {
        return tuttifile;
    }

    public void setTuttifile(String tuttifile) {
        this.tuttifile = tuttifile;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public String getUploadDirVideo() {
        return uploadDirVideo;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public void setUploadDirVideo(String uploadDirVideo) {
        this.uploadDirVideo = uploadDirVideo;
    }
}
