package com.aws.service.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.aws.service.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

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

    @Override
    public Object listFiles(String bucketName) throws AmazonClientException, CustomException {
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        if(objectListing == null) {
            throw new CustomException("No files found in the bucket", HttpStatus.NOT_FOUND);
        }
        List<S3ObjectSummary> objectSummary = objectListing.getObjectSummaries(); // NOTE: will get owner info too
        log.info("Files found in bucket({}): {}", bucketName, objectSummary);
        return objectSummary;
    }
}
