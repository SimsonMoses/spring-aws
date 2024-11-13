package com.aws.service.service.impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.aws.service.exception.CustomException;
import com.aws.service.response.CommonResponse;
import com.aws.service.response.UploadResponse;
import com.aws.service.service.FileService;
import com.aws.service.service.s3.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private AwsS3Service awsService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public CommonResponse uploadFile(MultipartFile file, String path) throws CustomException {
        CommonResponse response = new CommonResponse();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put("fileName", file.getOriginalFilename());
            metadata.setUserMetadata(userMetadata);
            // on appending the keyName to the path, the file will be uploaded to the specified path in s3 bucket
            UploadResponse uploadResponse = new UploadResponse();
            uploadResponse.setFileName(file.getOriginalFilename());
            uploadResponse.setFileType(file.getContentType());

            StringBuilder keyName = new StringBuilder(path);
            keyName.append(UUID.randomUUID().toString());
            uploadResponse.setFileKey(keyName.toString());
            awsService.uploadFile(bucketName, keyName.toString(), file.getInputStream(), metadata);

            response.setData(uploadResponse);
            response.setMessage("File uploaded successfully");
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
