package com.pubbli.pubbli.model;


import javax.persistence.*;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="VIDEO_FOTO")
    @SequenceGenerator(name="VIDEO_FOTO", sequenceName="video_foto")
    private long idVideo;

    @Column(unique = true)
    private  String urlVideo;

    public long getIdVideo() {
        return idVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setIdVideo(long idVideo) {
        this.idVideo = idVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }
}
