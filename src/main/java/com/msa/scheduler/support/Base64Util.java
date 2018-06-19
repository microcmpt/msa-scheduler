package com.msa.scheduler.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * The type Base 64 util.
 *
 * @author sxp
 */
@Slf4j
public class Base64Util {
    public static final String BASE64_SUFFIX = "1q2w3e4r!@#$";

    /**
     * Instantiates a new Base 64 util.
     */
    private Base64Util() {
    }

    /**
     * Encode string.
     *
     * @param data the data
     * @return the string
     */
    public static String encode(String data) {
        String base64Data = data + BASE64_SUFFIX;
        try {
            return Base64.encodeBase64String(base64Data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("encode exception", e);
        }
        return "";
    }

    /**
     * Decode string.
     *
     * @param data the data
     * @return the string
     */
    public static String decode(String data) {
        try {
            String base64Data = new String(Base64.decodeBase64(data), "utf-8");
            Assert.isTrue(base64Data.endsWith(Base64Util.BASE64_SUFFIX), "scheduler.mail.password is not correct");
            return base64Data.substring(0, base64Data.length() - BASE64_SUFFIX.length());
        } catch (UnsupportedEncodingException e) {
            log.error("decode exception", e);
        }
        return "";
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("Please input your encode data:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("After your encode data is:" + encode(scanner.next()));
    }
}