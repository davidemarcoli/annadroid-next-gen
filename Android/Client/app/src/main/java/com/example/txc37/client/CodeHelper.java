package com.example.txc37.client;

import android.view.View;

/**
 * Created by annatina on 27/07/2016.
 */
public class CodeHelper {
    private static String code = null;

    public static String generateCode() {
        int randCode = (int) (Math.random() * 9000) + 1000;
        String codeString = String.valueOf(randCode);
        return codeString;

    }

    public static String getCode(){

        if (code == null){

            code = generateCode();

        }
        return code;
    }
}
