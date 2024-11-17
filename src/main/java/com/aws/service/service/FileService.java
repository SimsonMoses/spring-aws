package com.aws.service.service;

import com.aws.service.exception.CustomException;
import com.aws.service.response.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    CommonResponse uploadFile(MultipartFile file, String path) throws CustomException;

    CommonResponse listFiles() throws CustomException;
}
