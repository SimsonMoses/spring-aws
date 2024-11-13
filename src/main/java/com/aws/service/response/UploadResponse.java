package com.aws.service.response;

import lombok.Data;

@Data
public class UploadResponse {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileKey;
}
