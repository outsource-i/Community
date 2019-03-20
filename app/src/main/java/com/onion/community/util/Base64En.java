package com.onion.community.util;

import android.util.Base64;

/**
 * Created by zhangqi on 2016/10/19.
 */

public class Base64En {
    public static byte[] base64Encode(String input) {
        return base64Encode(input.getBytes());
    }

    public static byte[] base64Encode(byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);

    }
    public static byte[] base64Decode(String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

}
