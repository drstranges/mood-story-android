package com.drprog.moodstory.core;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.drprog.moodstory.core.permission.PermissionHelper;
import com.drprog.moodstory.core.permission.PermissionSecurityException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class for pick-up images and prepare them for uploading
 * Created by roman.donchenko on 18.01.2016
 */

public class ImageUtils {

    public static boolean performToUpload(String _outFile, String _rawImageFile, int _reqWidth, int _reqHeight, Bitmap.CompressFormat compressFormat, int _quolity, Bitmap.Config bitmapConfig) throws IllegalArgumentException, IOException {
        if (TextUtils.isEmpty(_outFile)) {
            throw new IllegalArgumentException("_outFile not may be null");
        } else if (TextUtils.isEmpty(_rawImageFile)) {
            throw new IllegalArgumentException("_rawImageFile not may be null");
        } else if (_reqWidth <= 0 || _reqHeight <= 0) {
            throw new IllegalArgumentException("_reqWidth and _reqHeight has to be greater than 0 (w, h): " + _reqWidth + ", " + _reqHeight);
        }

        final Bitmap bmp = getResizedAndRotatedBitmap(_rawImageFile, _reqWidth, _reqHeight, bitmapConfig);
        return saveToFile(_outFile, bmp, compressFormat, _quolity);
    }

    public static boolean saveToFile(String _outFile, Bitmap bmp, Bitmap.CompressFormat _compressFormat, int _quolity) {
        boolean resultOk = false;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(_outFile);
            bmp.compress(_compressFormat, _quolity, out);
            resultOk = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultOk;
    }

    public static Bitmap getResizedAndRotatedBitmap(String _pathToFile, int _reqWidth, int _reqHeight, Bitmap.Config _bitmapConfig) throws IOException {
        ExifInterface exif = new ExifInterface(_pathToFile);
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotationInDegrees = exifToDegrees(rotation);

        Matrix matrix = new Matrix();
        if (rotation != 0f) {
            matrix.preRotate(rotationInDegrees);
        }

        final Bitmap sourceBitmap = decodeSampledBitmapFromFile(_pathToFile, _reqWidth, _reqHeight, _bitmapConfig);
        final Bitmap adjustedBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
        return adjustedBitmap;
    }

    private static int exifToDegrees(int _rotation) {
        if (_rotation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (_rotation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (_rotation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static Bitmap decodeSampledBitmapFromFile(String _pathToFile, int _reqWidth, int _reqHeight, Bitmap.Config _bitmapConfig) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(_pathToFile, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, _reqWidth, _reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = _bitmapConfig;
        return BitmapFactory.decodeFile(_pathToFile, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options _options, int _reqWidth, int _reqHeight) {
        // Raw height and width of image
        final int height = _options.outHeight;
        final int width = _options.outWidth;
        int inSampleSize = 1;

        if (height > _reqHeight || width > _reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > _reqHeight
                    || (halfWidth / inSampleSize) > _reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static class MediaChooseHelper {
        private static final String LOG_TAG = LogHelper.makeLogTag(MediaChooseHelper.class);

        private final static String APP_FOLDER = "/MyDrive";

        private static final String BUNDLE_URI_IMG = "img";
        private static final String BUNDLE_URI_VIDEO = "video";

        public static final String DIALOG_TITLE_CHOOSE_PHOTO = "Choose photo";
        private static final String DIALOG_TITLE_CHOOSE_VIDEO = "Choose video";
        final CharSequence[] mDialogItems = {"Camera", "Choose from Gallery"};
        public static final String[] REQUIRED_PERMISSIONS;

        static {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                REQUIRED_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            } else {
                REQUIRED_PERMISSIONS = new String[]{};
            }
        }

        private WeakReference<Activity> mActivityReference = new WeakReference<>(null);
        private WeakReference<Fragment> mFragmentReference = new WeakReference<>(null);

        private final int mImageCameraRequestCode;
        private final int mVideoCameraRequestCode;
        private final int mExternalImageRequestCode;
        private final int mExternalVideoRequestCode;

        private Uri mImageUri;
        private Uri mVideoUri;

        private DialogInterface.OnClickListener mOnClickListenerForPickImage;
        private DialogInterface.OnClickListener mOnClickListenerForPickVideo;

        private long mMaxVideoSize = 0;
        private long mMaxVideoLength = 0;

        private MediaChooseHelper(int _imageCameraRequestCode, int _externalImageRequestCode,
                                  int _videoCameraRequestCode, int _externalVideoRequestCode,
                                  long _maxVideoSize, long _maxVideoLength, Bundle _savedInstanceState) {
            mImageCameraRequestCode = _imageCameraRequestCode;
            mVideoCameraRequestCode = _videoCameraRequestCode;
            mExternalImageRequestCode = _externalImageRequestCode;
            mExternalVideoRequestCode = _externalVideoRequestCode;
            mMaxVideoSize = _maxVideoSize;
            mMaxVideoLength = _maxVideoLength;
            if (_savedInstanceState != null) {
                mImageUri = _savedInstanceState.getParcelable(BUNDLE_URI_IMG);
                mVideoUri = _savedInstanceState.getParcelable(BUNDLE_URI_VIDEO);
            }
        }

        public static MediaChooseHelper newInstance(int _imageCameraRequestCode, int _externalImageRequestCode,
                                                    int _videoCameraRequestCode, int _externalVideoRequestCode,
                                                    long _maxVideoSize, long _maxVideoLength, Bundle _savedInstanceState) {
            return new MediaChooseHelper(_imageCameraRequestCode, _externalImageRequestCode,
                    _videoCameraRequestCode, _externalVideoRequestCode, _maxVideoSize, _maxVideoLength, _savedInstanceState);
        }

        public void showImageChooser(Activity _activity) throws PermissionSecurityException {
            if (!isAlive(_activity)) return;
            mActivityReference = new WeakReference<>(_activity);
            mFragmentReference.clear();
            showImageChooserByContext(_activity);
        }

        public void showImageChooser(Fragment _fragment) throws PermissionSecurityException {
            if (!isAlive(_fragment)) return;
            mActivityReference.clear();
            mFragmentReference = new WeakReference<>(_fragment);
            showImageChooserByContext(_fragment.getContext());
        }

        public void showVideoChooser(Activity _activity) throws PermissionSecurityException {
            if (!isAlive(_activity)) return;
            mActivityReference = new WeakReference<>(_activity);
            mFragmentReference.clear();
            showVideoChooserByContext(_activity);
        }

        public void showVideoChooser(Fragment _fragment) throws PermissionSecurityException {
            if (!isAlive(_fragment)) return;
            mActivityReference.clear();
            mFragmentReference = new WeakReference<>(_fragment);
            showVideoChooserByContext(_fragment.getContext());
        }


        private void showImageChooserByContext(Context _context) throws PermissionSecurityException {
            PermissionHelper.assertPermissions(_context, REQUIRED_PERMISSIONS);
            mImageUri = null;
            final AlertDialog.Builder builder = new AlertDialog.Builder(_context);
            builder.setTitle(DIALOG_TITLE_CHOOSE_PHOTO);
            builder.setItems(mDialogItems, getOnClickListenerForPickImage());
            builder.show();
        }

        private void showVideoChooserByContext(Context _context) throws PermissionSecurityException {
            PermissionHelper.assertPermissions(_context, REQUIRED_PERMISSIONS);
            mVideoUri = null;
            final AlertDialog.Builder builder = new AlertDialog.Builder(_context);
            builder.setTitle(DIALOG_TITLE_CHOOSE_VIDEO);
            builder.setItems(mDialogItems, getOnClickListenerForPickVideo());
            builder.show();
        }

        private DialogInterface.OnClickListener getOnClickListenerForPickImage() {
            if (mOnClickListenerForPickImage == null) {
                mOnClickListenerForPickImage = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Context context = getContext();
                        if (context == null) return;
                        if (mDialogItems[item].equals(mDialogItems[0])) {
                            try {
                                mImageUri = getMediaDir("photo", ".jpg");
                            } catch (IOException _e) {
                                _e.printStackTrace();
                                Toast.makeText(context, "Sorry. External storage hasn't been found.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                            startActivityForResult(intent, mImageCameraRequestCode);
                        } else if (mDialogItems[item].equals(mDialogItems[1])) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), mExternalImageRequestCode);
                        }
                    }
                };
            }
            return mOnClickListenerForPickImage;
        }

        private DialogInterface.OnClickListener getOnClickListenerForPickVideo() {
            if (mOnClickListenerForPickVideo == null) {
                mOnClickListenerForPickVideo = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Context context = getContext();
                        if (context == null) return;
                        if (mDialogItems[item].equals(mDialogItems[0])) {
                            try {
                                mVideoUri = getMediaDir("video", ".3gp");
                            } catch (IOException _e) {
                                _e.printStackTrace();
                                Toast.makeText(context, "Sorry. External storage hasn't been found.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                            if (mMaxVideoSize != 0) {
                                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, mMaxVideoSize);
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, mMaxVideoLength);
                            }
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mVideoUri);
                            startActivityForResult(intent, mVideoCameraRequestCode);
                        } else if (mDialogItems[item].equals(mDialogItems[1])) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("video/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), mExternalVideoRequestCode);
                        }
                    }
                };
            }
            return mOnClickListenerForPickVideo;
        }

        private Uri getMediaDir(String _prefix, String _extension) throws IOException {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = _prefix + timeStamp;
            File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).getAbsolutePath() + APP_FOLDER);
            storageDir.mkdirs();
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    _extension,         /* suffix */
                    storageDir      /* directory */
            );

            return Uri.parse("file://" + image.getAbsolutePath());
        }

        public boolean onActivityResult(int _requestCode, int _resultCode, Intent _data) {
            if (_resultCode == Activity.RESULT_OK) {
                if (_requestCode == mImageCameraRequestCode) {
                    //do nothing
                    return true;
                } else if (_requestCode == mExternalImageRequestCode) {
                    mImageUri = _data.getData();
                    return true;
                } else if (_requestCode == mVideoCameraRequestCode) {
                    //do nothing
                    return true;
                } else if (_requestCode == mExternalVideoRequestCode) {
                    mVideoUri = _data.getData();
                    return true;
                }
            }
            return false;
        }

        public Uri getPickedImageUri() {
            return mImageUri;
        }

        public Uri getPickedVideoUri() {
            return mVideoUri;
        }

        public void onSaveInstanceState(Bundle outState) {
            outState.putParcelable(BUNDLE_URI_IMG, mImageUri);
            outState.putParcelable(BUNDLE_URI_VIDEO, mVideoUri);
        }


        private boolean isAlive(Activity _activity) {
            return _activity != null && !_activity.isFinishing();
        }

        private boolean isAlive(Fragment _fragment) {
            return _fragment != null && _fragment.isAdded();
        }

        private Context getContext() {
            Activity activity = mActivityReference.get();
            Fragment fragment = mFragmentReference.get();
            if (isAlive(activity)) return activity;
            if (isAlive(fragment)) return fragment.getContext();
            return null;
        }

        private void startActivityForResult(Intent _intent, int _imageCameraRequestCode) {
            Activity activity = mActivityReference.get();
            Fragment fragment = mFragmentReference.get();
            if (isAlive(activity))
                activity.startActivityForResult(_intent, _imageCameraRequestCode);
            if (isAlive(fragment))
                fragment.startActivityForResult(_intent, _imageCameraRequestCode);
        }

        public void clearVideoUri() {
            mVideoUri = null;
        }
    }
}
