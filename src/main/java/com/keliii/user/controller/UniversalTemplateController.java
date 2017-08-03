package com.keliii.user.controller;

import com.keliii.user.annotation.Permission;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by keliii on 2017/6/27.
 */
@RestController
@RequestMapping("/tmp")
public class UniversalTemplateController {


    @RequestMapping("/file/upload")
    @Permission(isPublic = true)
    public String file_upload(MultipartFile file) {



        return "SUCCESS";
    }

}
