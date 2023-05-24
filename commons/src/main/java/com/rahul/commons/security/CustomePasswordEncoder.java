package com.rahul.commons.security;

import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomePasswordEncoder implements PasswordEncoder {

    private String digest(CharSequence rawPassword) {
        if (rawPassword == null) {
            rawPassword = "";
        }
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        m.update(rawPassword.toString().getBytes(), 0, rawPassword.toString().length());
        return new BigInteger(1, m.digest()).toString(16);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return digest(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MessageDigest.isEqual(bytesUtf8(encodedPassword.toString()), bytesUtf8(digest(rawPassword)));
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

    private static byte[] bytesUtf8(String s) {
        // need to check if Utf8.encode() runs in constant time (probably not).
        // This may leak length of string.
        return (s != null) ? Utf8.encode(s) : null;
    }
}
