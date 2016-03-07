package com.drprog.moodstory.core.restapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.drprog.moodstory.core.LogHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;


/**
 * Helper class for using Retrofit
 * Created by roman.donchenko on 09.10.15.
 */
public class RestApiHelper {

    private static final String LOG_TAG = LogHelper.makeLogTag("RestApiHelper");

//    public static Map<String, RequestBody> prepareEditUserRequestBody(
//            Context _context, User user, File userAvatar) {
//
//        Map<String, RequestBody> map = new HashMap<>();
//        map.put("user", RUtils.toRequestBody((new Gson()).toJson(user)));
//
//        if (userAvatar != null && userAvatar.exists()) {
//            File fileToUpload;
//            try {
//                fileToUpload = getPerformedFileToUpload(_context, userAvatar);
//                RUtils.toRequestBodyMap(map, "avatar", fileToUpload, FileUtils.getMimeType(fileToUpload));
//            } catch (IOException e) {
//                e.printStackTrace();
//                LogHelper.LOGE(LOG_TAG, "User avatar performing error: " + e.getMessage());
//            }
//        }
//        return map;
//    }
//
//    private static File getPerformedFileToUpload(Context _context, File _file) throws IOException {
//        if (Config.IMAGE_TO_UPLOAD_PREPARE_ENABLED) {
//            File file = File.createTempFile("tmp", ".jpeg", _context.getCacheDir());
//            ImageUtils.performToUpload(file.getAbsolutePath(), _file.getAbsolutePath(),
//                    Config.IMAGE_TO_UPLOAD_REQUIRED_SIZE, Config.IMAGE_TO_UPLOAD_REQUIRED_SIZE,
//                    Config.IMAGE_TO_UPLOAD_FORMAT, Config.IMAGE_TO_UPLOAD_QUALITY, Config.IMAGE_TO_UPLOAD_CONFIG);
//            return file;
//        }
//        return _file;
//    }
//
//    private static File getVideoThumbnail(Context _context, File _postVideo) {
//        File file = null;
//        try {
//            file = File.createTempFile("tmb", ".jpeg", _context.getCacheDir());
//            Bitmap bmThumbnail = ThumbnailUtils.createVideoThumbnail(_postVideo.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND);
//            ImageUtils.saveToFile(file.getAbsolutePath(), bmThumbnail,
//                    Config.IMAGE_TO_UPLOAD_FORMAT, Config.IMAGE_TO_UPLOAD_QUALITY);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
}
