package com.moscue.utils;

import java.io.UnsupportedEncodingException;

public class CharsetUtils {
	public static String getString(byte[] bytes, String encoding) {
        try {
            return new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
	}
}
