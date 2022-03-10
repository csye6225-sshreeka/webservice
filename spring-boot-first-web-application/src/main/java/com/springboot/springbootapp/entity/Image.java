package com.springboot.springbootapp.entity;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.Objects;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "image")
public class Image {


    @Id
    private int userId;
    //    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator"
//    )
    @NotEmpty @NotNull(message="File name cannot be missing or empty")
    @Column(name = "file_name")
    private String file_name;


    @NotEmpty @NotNull(message="URL cannot be missing or empty")
    @Column(name = "url")
    private String url;

    //2020-01-12
    private String upload_date;

//    @Column(name = "userId", nullable = false, updatable = false)
//    private int userId;


    public int getuserId() {
        return userId;
    }

//	public void setId(String id) {
//		this.id = id;
//	}

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public Image() {

    }

    public Image(int userId, String file_name, String url) {
        this.userId = userId;
        this.file_name = file_name;
        this.url = url;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.upload_date =formatter.format(new Date());
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Image))
            return false;
        Image employee = (Image) o;
        return Objects.equals(this.userId, employee.userId)
                && Objects.equals(this.file_name, employee.file_name)
                && Objects.equals(this.userId, employee.userId)
                && Objects.equals(this.url, employee.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.file_name, this.url);
    }
}