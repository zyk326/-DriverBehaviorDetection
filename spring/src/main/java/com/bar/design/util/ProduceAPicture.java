package com.bar.design.util;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * 处理图像工具类
 */
public class ProduceAPicture {

    static CascadeClassifier faceDetector;
    static CascadeClassifier f0;
    static CascadeClassifier f1;
    static CascadeClassifier f2;
    static CascadeClassifier f3;
    static CascadeClassifier f4;
    /**
     * 初始化人脸探测器
     */
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        faceDetector = new CascadeClassifier("F:\\course-design-grade1\\spring\\src\\main\\resources\\static\\k\\haarcascade_mcs_mouth.xml");
        f0 = new CascadeClassifier("F:\\course-design-grade1\\spring\\src\\main\\resources\\static\\k\\smoke.xml");
        f1 = new CascadeClassifier("F:\\course-design-grade1\\spring\\src\\main\\resources\\static\\k\\haarcascade_mcs_mouth.xml");
        f2 = new CascadeClassifier("F:\\course-design-grade1\\spring\\src\\main\\resources\\static\\k\\sleep.xml");
        f3 = new CascadeClassifier("F:\\course-design-grade1\\spring\\src\\main\\resources\\static\\k\\phone.xml");
        f4 = new CascadeClassifier("F:\\course-design-grade1\\spring\\src\\main\\resources\\static\\k\\haarcascade_frontalface_alt.xml");
    }

    /**
     * 处理Mat类型的图像
     * @param image 传来的Mat类型的图像
     * @return 处理过后的Mat类型图像
     */
    public static Mat getFace(Mat image, int catg){

        String cont = "admin";

        if(catg == 0){
            cont = "smoking";
            faceDetector = f0;
        }else if(catg == 1){
            cont = "yawnning";
            faceDetector = f1;
            MatOfRect face1 = new MatOfRect();
            faceDetector.detectMultiScale(image, face1, 1.1,5, 0,new Size(5, 5),new Size(1000, 1000));//目标识别
            Rect[] rec=face1.toArray();
            if(rec.length > 0){
                System.out.println("有嘴巴");
                return image;
            }else{
                faceDetector = f4;
//                return image;
            }
        }else if(catg == 2){
            cont = "sleeping";
            faceDetector = f2;
        }else if(catg == 3){
            cont = "phone";
            faceDetector = f3;
        }else if(catg == 4){
            cont = "admin";
            faceDetector = f4;
        }

        MatOfRect face = new MatOfRect();
        faceDetector.detectMultiScale(image, face, 1.1,5, 0,new Size(5, 5),new Size(1000, 1000));//目标识别
        Rect[] rects=face.toArray();

        System.out.println("匹配到 "+rects.length+" 个目标");

        if(rects != null && rects.length > 0) {
           for (int i = 0; i < rects.length; i++) {// 框出每张识别到的目标
               System.out.println(rects[i].height);
               Imgproc.rectangle(image, new Point(rects[i].x, rects[i].y), 
                        new Point(rects[i].x + rects[i].width, rects[i].y + rects[i].height), new Scalar(0, 255, 0));
               Imgproc.putText(image, cont, new Point(rects[i].x, rects[i].y), 
                        Imgproc.FONT_HERSHEY_SCRIPT_SIMPLEX, 1.0, new Scalar(0, 255, 0), 1, Imgproc.LINE_AA, false);
           }
        }
        return image;
    }
}
