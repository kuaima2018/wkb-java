package com.heima.web.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
public class ImageHelper {
    public static String encode(byte[] bytes){
        return new BASE64Encoder().encode(bytes);
    }

    public static byte[] decode(String encodeStr) throws IOException {
        byte[] bt = null;
        BASE64Decoder decoder = new BASE64Decoder();
        bt = decoder.decodeBuffer(encodeStr);
        return bt;
    }

    public static byte[] connectBytes(byte[] front, byte[] after){
        byte[] result = new byte[front.length + after.length];
        System.arraycopy(front, 0, result, 0, after.length);
        System.arraycopy(after, 0, result, front.length, after.length);
        return result;
    }

    public static String encodeImage(String imgUrl) throws IOException{
        FileInputStream fis = new FileInputStream(imgUrl);
        byte[] rs = new byte[fis.available()];
        fis.read(rs);
        fis.close();
        return encode(rs);
    }


    public static void main(String[] args) {
        String str;
        try {
            //--str = encodeImage("D:\\ESB\\Bears.jpg");
            //FileOutputStream fileOutputStream=new FileOutputStream("D:\\ESB\\Post\\123.jpg",false);
            //--System.out.println(str);
            //byte[] datas= decode(str);
            //fileOutputStream.write(datas);
            //fileOutputStream.close();
            str=URLDecoder.decode("%E6%91%81%E6%91%81%E3%80%82","UTF-8");
            System.out.println(str);
            /*str=MD5Utils.getMd5String("123456");
            System.out.println("123456-"+str);
            str=MD5Utils.getMd5String("1234567");
            System.out.println("1234567-"+str);
            str=MD5Utils.getMd5String("12345678");
            System.out.println("12345678-"+str);
            str=MD5Utils.getMd5String("123456789");
            System.out.println("123456789-"+str);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("sandboxapp.cloopen.com", "8883");
        restAPI.setAccount("8a48b5514bfd9130014c018e2fd80282", "1536c4a05a9f479aa6da04681fce4c9e");
        restAPI.setAppId("aaf98f894bfd8efd014c018e675d01eb");
        result = restAPI.sendTemplateSMS("15900742859","1" ,new String[]{"6532","5"});
        System.out.println(result.get("statusCode")); */
    }
}
