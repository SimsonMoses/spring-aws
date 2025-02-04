package com.aws.service.controller.s3;


import com.aws.service.exception.CustomException;
import com.aws.service.response.CommonResponse;
import com.aws.service.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("s3")
public class AwsS3Controller {

    @Autowired
    private FileService fileService;

    // file upload
    @PostMapping("file/upload")
    public ResponseEntity<CommonResponse> uploadTestFile(@RequestParam MultipartFile file) {
        CommonResponse response = new CommonResponse();
        try {
            if (file.isEmpty()) {
                response.setMessage("Please select a file to upload");
                return ResponseEntity.badRequest().body(response);
            }
            response = fileService.uploadFile(file, "test-path/"); //provide the test path according to the requirement
            response.setStatus(HttpStatus.CREATED);
            return ResponseEntity.ok(response);
        } catch (CustomException ce) {
            response.setMessage(ce.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.badRequest().body(response);
        }
    }

    // file list
    @GetMapping("file/list")
    public ResponseEntity<CommonResponse> listFiles() {
        CommonResponse response = new CommonResponse();
        try {
            response = fileService.listFiles();
        } catch (CustomException ce) {
            ce.printStackTrace();
            response.setMessage(ce.getMessage());
            response.setStatus(ce.getHttpStatus());
            return ResponseEntity.status(ce.getHttpStatus()).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // file download

    // file delete

    // file update

    // file metadata

    // file url presigned url


}
