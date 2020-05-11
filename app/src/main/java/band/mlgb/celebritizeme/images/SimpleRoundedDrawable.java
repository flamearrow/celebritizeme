package band.mlgb.celebritizeme.images;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

public class SimpleRoundedDrawable extends Drawable {

    private final Paint mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG), mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBitmap;
    private int mSide;
    private float mRadius;

    public SimpleRoundedDrawable() {
        this(null);
    }

    public SimpleRoundedDrawable(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    public SimpleRoundedDrawable(Bitmap bitmap, float width, @ColorInt int color) {
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBitmap = bitmap;
        mSide = mBitmap == null ? 0 : Math.min(bitmap.getWidth(), bitmap.getHeight());
        mBorderPaint.setStrokeWidth(width);
        mBorderPaint.setColor(color);
    }

    public SimpleRoundedDrawable setBitmap(final Bitmap bitmap) {
        mBitmap = bitmap;
        mSide = Math.min(bitmap.getWidth(), bitmap.getHeight());
        invalidateSelf();
        return this;
    }

    public SimpleRoundedDrawable setBorder(float width, @ColorInt int color) {
        mBorderPaint.setStrokeWidth(width);
        mBorderPaint.setColor(color);
        invalidateSelf();
        return this;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        if (mBitmap == null)
            return;
        Matrix matrix = new Matrix();
        RectF src = new RectF(0, 0, mSide, mSide);
        src.offset((mBitmap.getWidth() - mSide) / 2f, (mBitmap.getHeight() - mSide) / 2f);
        RectF dst = new RectF(bounds);
        final float strokeWidth = mBorderPaint.getStrokeWidth();
        if (strokeWidth > 0)
            dst.inset(strokeWidth, strokeWidth);
        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        Shader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        mMaskPaint.setShader(shader);
        matrix.mapRect(src);
        mRadius = src.width() / 2f;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect b = getBounds();
        if (mBitmap != null)
            canvas.drawCircle(b.exactCenterX(), b.exactCenterY(), mRadius, mMaskPaint);
        final float strokeWidth = mBorderPaint.getStrokeWidth();
        if (strokeWidth > 0)
            canvas.drawCircle(b.exactCenterX(), b.exactCenterY(), mRadius + strokeWidth / 2, mBorderPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mMaskPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mMaskPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}