package com.onion.community.api.Interceptor;

import android.util.Log;


import com.onion.community.constant.Constant;
import com.onion.community.util.logger.Logger;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by OnionMac on 17/9/22.
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    public static boolean DEBUG = true;

    @Override
    public void log(String message) {
        if (DEBUG){
            if (!message.startsWith("{")){
                Log.i(Constant.PROJECT_NAME, message);
            }
        }

        if(message.contains("info") && message.contains("code")){
            if (DEBUG && message.startsWith("{") && message.endsWith("}")){
                Logger.json(message);
            }
        }
    }
}