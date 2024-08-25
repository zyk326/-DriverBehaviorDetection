package com.bar.design.util;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 编码转换工具类
 */
public class CodeTrans {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    /**
     * Base64转Mat --》Base64转BufImage，BufImage转Base64
     * @param base64
     * @return
     * @throws IOException
     */
    public static Mat base642Mat(String base64) throws IOException {
//        System.out.println("IIIIIIIIIIIIIII- " + base64);
        return bufImg2Mat(base64ToBufferedImage(base64), BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
    }

    /**
     * base64 编码转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    public static BufferedImage base64ToBufferedImage(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BufferedImage转换成Mat
     *
     * @param original 要转换的BufferedImage
     * @param imgType  bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType  转换成mat的type 如 CvType.CV_8UC3
     */
    public static Mat bufImg2Mat(BufferedImage original, int imgType, int matType) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }
        // Don't convert if it already has correct type
        if (original.getType() != imgType) {
            // Create a buffered image
            BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), imgType);
            // Draw the image onto the new buffer
            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
                original = image;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                g.dispose();
            }
        }
        byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), matType);
        mat.put(0, 0, pixels);
        return mat;
    }

    /**
     * Mat转Base64 -->Mat转byte[],byte[]转Base64
     * @param correctMat 处理的Mat
     * @return 返回前端的Base64
     */
    public static String matToBase64(Mat correctMat) {
        return bufferToBase64(toByteArray(correctMat));
    }

    /**
     * Mat转二进制数组
     * @param matrix
     * @return
     */
    public static byte[] toByteArray(Mat matrix) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matrix, mob);
        return mob.toArray();
    }

    /**
     * 二进制数组转Base64
     * @param buffer
     * @return
     */
    public static String bufferToBase64(byte[] buffer) {
        return Base64Utils.encodeToString(buffer);
    }


}
