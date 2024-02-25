package com.brli.articlenet.controller;

import com.brli.articlenet.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            return Result.error("file is null");
        }
        // save file to local disk
        // ensure filename is unique
        String originalFilename = file.getOriginalFilename();
        // UUID + file type suffix (from last ".")
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String uploadDir = "/Users/brandon/Downloads/test/";
        Path path = Paths.get(uploadDir + filename);
        // have to use the overloaded method with transferTo(path) instead transferTo(file)
        file.transferTo(path);
        return Result.success("url of the file");
    }
}
