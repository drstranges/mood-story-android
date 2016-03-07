package com.drprog.moodstory.core.restapi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

import static com.drprog.moodstory.core.LogHelper.LOGD;
import static com.drprog.moodstory.core.LogHelper.makeLogTag;

/**
 * Created by roman.donchenko on 09.10.15.
 */
public class LoggingInterceptor implements Interceptor {

    private static final String LOG_TAG = makeLogTag(LoggingInterceptor.class);
    private static final boolean SHOW_POST_BODY_ENABLED = true;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        String requestLog = String.format("Request %s %s%n%s",
                request.method(), request.url(), request.headers());
        if (request.method().compareToIgnoreCase("post") == 0) {
            // Be careful, this peace of code may produce OutOfMemoryError
            requestLog = requestLog + (SHOW_POST_BODY_ENABLED ? bodyToString(request)
                    : " Body hasn't been shown. Switch this in LoggingInterceptor");
        }
        LOGD(LOG_TAG, requestLog);

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        String responseLog = String.format("Response for %s in %.1fms with status = %s%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.code(), response.headers());

        String bodyString = response.body().string();

        LOGD(LOG_TAG, responseLog + bodyString.replace("\"", "\'"));

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
        //return response;
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8().replace("\"", "\'");
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
