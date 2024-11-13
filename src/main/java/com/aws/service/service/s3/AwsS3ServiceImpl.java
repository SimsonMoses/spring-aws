package com.aws.service.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class AwsS3ServiceImpl implements AwsS3Service{

    private static final Logger log = LoggerFactory.getLogger(AwsS3ServiceImpl.class);

    @Autowired
    private AmazonS3 s3Client;

    @Override
    public void uploadFile(String bucketName, String keyName, InputStream value, ObjectMetadata metadata) throws AmazonClientException {
        s3Client.putObject(bucketName, keyName, value, metadata);
        log.info("File uploaded successfully");
    }
}
