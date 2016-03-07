package com.drprog.moodstory.core.restapi;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Helper class for using Retrofit
 * Created by roman.donchenko on 09.10.15.
 */
public class RUtils {

    public static RequestBody toRequestBody (String _value) {
        if (_value == null) return null;
        return RequestBody.create(MediaType.parse("text/plain"), _value);
    }

    public static void toRequestBodyMap (Map<String, RequestBody> _dstMap, String _key, String _value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), _value);
        _dstMap.put(_key, body);
    }

    public static void toRequestBodyMap (Map<String, RequestBody> _dstMap, String _key, File _file, String _mimeType) {
        RequestBody body = RequestBody.create(
                MediaType.parse(_mimeType != null ? _mimeType : "application/octet-stream"),
                _file);
        _dstMap.put(_key + "\"; filename=\"" + _file.getName() + "\"", body);
    }
}
