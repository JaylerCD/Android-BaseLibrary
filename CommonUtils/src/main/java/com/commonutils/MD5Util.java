package com.commonutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * @author LIANGYJ3
 */
public class MD5Util {

    public static String toMD5(String content) {
        return toHex(encode(content));
    }

    /**
     * MessageDigest不仅仅只为我们提供了"MD5"加密,还提供了"SHA-1"
     * 创建的方法只为: MessageDigest messageDigest =
     * MessageDigest.getInstance("SHA-1");
     * MD5与SHA-1的区别为:MD5是16位,SHA是20位（这是两种报文摘要的算法）
     *
     * @param content
     * @return
     */
    private static byte[] encode(String content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes());
            return messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为16进制字符
     *
     * @param buffer
     * @return
     */
    private static String toHex(byte buffer[]) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }

        return sb.toString();
    }

    public synchronized static String getMd5(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getWalletMd5(String src) {
        return getWalletMd5Str(src);
    }

    private static String getWalletMd5Str(String data) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = getWalletMd52HexString(md.digest(data.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    private static String getWalletMd52HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toLowerCase();
        }
        return ret;
    }

    public synchronized static String getMd5(File file) {
        String s = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(byteBuffer);
            s = bytesToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }

}
