package com.bar.design.service;

import com.alibaba.druid.util.StringUtils;
import com.bar.design.entity.AnsBase64;
import com.bar.design.util.CodeTrans;
import com.bar.design.util.ProduceAPicture;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class PictureServiceImpl implements PictureService{

    /**
     * 处理当前帧
     * @param Base64
     * @return
     */
    @Override
    public AnsBase64 produceImage(String Base64){
        Mat mat;
        AnsBase64 ansBase64 = new AnsBase64();
        String catg = Base64.substring(0,1);
        Base64 = Base64.substring(1);
        System.out.println(catg);

        try {
            mat = CodeTrans.base642Mat(Base64);
            String ans = CodeTrans.matToBase64(ProduceAPicture.getFace(mat,Integer.valueOf(catg)));
            ansBase64.setBase64(ans);
            ansBase64.setStatus(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ansBase64;
    }
}
