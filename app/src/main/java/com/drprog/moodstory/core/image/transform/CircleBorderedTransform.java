package com.drprog.moodstory.core.image.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by tamas.mondok on 27.10.15.
 */
public class CircleBorderedTransform implements Transformation<Bitmap> {

    private final int mBorderColor;
    private BitmapPool mBitmapPool;
    private PathEffect mPathEffect = new CornerPathEffect(10);

    public CircleBorderedTransform(final BitmapPool pool, final int borderColor) {
        mBitmapPool = pool;
        mBorderColor = borderColor;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int size = Math.min(source.getWidth(), source.getHeight());

        int width = (source.getWidth() - size) / 2;
        int height = (source.getHeight() - size) / 2;

        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        float stroke = getStrokeWidth(r);
        canvas.drawCircle(r, r, r, paint);

        preparePaintForCircleBorder(paint, stroke);

        canvas.drawCircle(r, r, r - stroke / 2f, paint);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    private void preparePaintForCircleBorder(final Paint _paint, final float _stroke) {
        _paint.setShader(null);
        _paint.setColor(mBorderColor);
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setStrokeJoin(Paint.Join.ROUND);
        _paint.setStrokeCap(Paint.Cap.ROUND);
        _paint.setPathEffect(mPathEffect);
        _paint.setStrokeWidth(_stroke);
    }

    /**
     * @param _radius - circle radius
     * @return - the stroke width based on the radius size
     */
    private float getStrokeWidth(final float _radius) {
        return _radius * 0.06f;
    }

    @Override
    public String getId() {
        return "CircleBorderedTransform()";
    }
}
