package com.bar.design.controller;

import com.bar.design.entity.AnsBase64;
import com.bar.design.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


@RestController
@RequestMapping("picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("produce")
    @ResponseBody
    public String produceImage(@RequestBody String Base64, HttpServletResponse response){
        AnsBase64 ansBase64 = pictureService.produceImage(Base64);
        return ansBase64.getBase64();
    }
}
