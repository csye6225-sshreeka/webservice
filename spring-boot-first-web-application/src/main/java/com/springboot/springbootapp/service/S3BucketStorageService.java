package com.springboot.springbootapp.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.springboot.springbootapp.entity.Image;
import com.springboot.springbootapp.entity.User;
import com.springboot.springbootapp.repository.ImageRepository;
import com.springboot.springbootapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.timgroup.statsd.StatsDClient;

import java.io.IOException;


@Service
public class S3BucketStorageService {

    private Logger logger = LoggerFactory.getLogger(S3BucketStorageService.class);

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private ImageRepository repository;

//    @Autowired
//    private StatsDClient statsd;

    /**
     * Upload file into AWS S3
     *
     * @param keyName
     * @param file
     * @return String
     */
    public String uploadFile(String keyName, MultipartFile file) {
        long startTime = System.currentTimeMillis();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName, keyName, file.getInputStream(), metadata);
           // statsd.recordExecutionTime("S3 Response Time - Delete File", System.currentTimeMillis() - startTime);
            return bucketName;
        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException: "+ serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
     //   statsd.recordExecutionTime("S3 Response Time - Upload pic File", System.currentTimeMillis() - startTime);
        return "File not uploaded: " + keyName;
    }


    public String deleteFileFromS3Bucket(String fileUrl, int userId) {
        long startTime = System.currentTimeMillis();
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        System.out.println("fileName to delete from service: "+bucketName + "/"+fileName);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, userId+"/"+fileName));
       // statsd.recordExecutionTime("S3 Response Time - Delete pic File", System.currentTimeMillis() - startTime);
        return "Successfully deleted";
    }

//    public Boolean isIDpresent(int id) {
//        return repository.isIdPresent(id) > 0 ? true : false;
//    }
//
//    public Image getImage(int id) {
//        return repository.findByUserId(id);
//
//
//    }


}