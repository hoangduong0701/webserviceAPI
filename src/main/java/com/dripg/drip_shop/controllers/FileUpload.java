package com.dripg.drip_shop.controllers;

import com.dripg.drip_shop.dto.CategoryDto;
import com.dripg.drip_shop.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.dripg.drip_shop.dto.FileUploadResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileUpload {


    @Autowired
    FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<?> fileUpload(@RequestParam(value = "file",required = true) MultipartFile file, @RequestParam(value = "fileName",required = true) String fileName){

        int statusCode = fileUploadService.uploadFile(file,fileName);
        return new ResponseEntity<>(statusCode == 200 ? HttpStatus.CREATED: HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
// 67919dc219111bee51dab54b2faf7188

// 181a9719e8ef1379d2f10e138dff5a28d50c3fd11881578f9bf9f55e8c2b553c
