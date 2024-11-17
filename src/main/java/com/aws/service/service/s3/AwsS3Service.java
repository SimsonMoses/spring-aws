package com.aws.service.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.aws.service.exception.CustomException;

import java.io.InputStream;

public interface AwsS3Service {
    void uploadFile(final String bucketName,
                    final String keyName,
                    final InputStream file,
                    final ObjectMetadata metadata
    ) throws AmazonClientException;

    Object listFiles(final String bucketName) throws AmazonClientException, CustomException;
}
