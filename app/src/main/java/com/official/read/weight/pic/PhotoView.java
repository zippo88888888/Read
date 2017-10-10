package com.official.read.weight.pic;

import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.ImageView;

public class PhotoView extends ImageView implements IPhotoView {

    private final PhotoViewAttacher mAttacher;
    private ScaleType mPendingScaleType;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        super.setScaleType(ScaleType.MATRIX);
        mAttacher = new PhotoViewAttacher(this);
        if (null != mPendingScaleType) {
            setScaleType(mPendingScaleType);
            mPendingScaleType = null;
        }
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public boolean canZoom() {
        // TODO Auto-generated method stub
        return mAttacher.canZoom();
    }

    @Override
    public Matrix getDisplayMatrix() {
        // TODO Auto-generated method stub
        return mAttacher.getDrawMatrix();
    }

    @Override
    public RectF getDisplayRect() {
        // TODO Auto-generated method stub
        return mAttacher.getDisplayRect();
    }

    @Override
    public IPhotoView getIPhotoViewImplementation() {
        // TODO Auto-generated method stub
        return mAttacher;
    }

    @Override
    public float getMaxScale() {
        // TODO Auto-generated method stub
        return getMaximumScale();
    }

    @Override
    public float getMaximumScale() {
        // TODO Auto-generated method stub
        return mAttacher.getMaximumScale();
    }

    @Override
    public float getMediumScale() {
        // TODO Auto-generated method stub
        return mAttacher.getMediumScale();
    }

    @Override
    public float getMidScale() {
        // TODO Auto-generated method stub
        return getMediumScale();
    }

    @Override
    public float getMinScale() {
        // TODO Auto-generated method stub
        return getMinimumScale();
    }

    @Override
    public float getMinimumScale() {
        // TODO Auto-generated method stub
        return mAttacher.getMinimumScale();
    }

    @Override
    public OnPhotoTapListener getOnPhotoTapListener() {
        // TODO Auto-generated method stub
        return mAttacher.getOnPhotoTapListener();
    }

    @Override
    public OnViewTapListener getOnViewTapListener() {
        // TODO Auto-generated method stub
        return mAttacher.getOnViewTapListener();
    }

    @Override
    public float getScale() {
        // TODO Auto-generated method stub
        return mAttacher.getScale();
    }

    @Override
    public Bitmap getVisibleRectangleBitmap() {
        // TODO Auto-generated method stub
        return mAttacher.getVisibleRectangleBitmap();
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        // TODO Auto-generated method stub
        mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override
    public boolean setDisplayMatrix(Matrix finalMatrix) {
        // TODO Auto-generated method stub
        return mAttacher.setDisplayMatrix(finalMatrix);
    }

    @Override
    public void setMaxScale(float maxScale) {
        // TODO Auto-generated method stub
        mAttacher.setMaxScale(maxScale);
    }

    @Override
    public void setMaximumScale(float maximumScale) {
        // TODO Auto-generated method stub
        mAttacher.setMaximumScale(maximumScale);
    }

    @Override
    public void setMediumScale(float mediumScale) {
        // TODO Auto-generated method stub
        mAttacher.setMediumScale(mediumScale);
    }

    @Override
    public void setMidScale(float midScale) {
        // TODO Auto-generated method stub
        mAttacher.setMidScale(midScale);
    }

    @Override
    public void setMinScale(float minScale) {
        // TODO Auto-generated method stub
        mAttacher.setMinScale(minScale);
    }

    @Override
    public void setMinimumScale(float minimumScale) {
        // TODO Auto-generated method stub
        mAttacher.setMinimumScale(minimumScale);
    }

    @Override
    public void setImageResource(int resId) {
        // TODO Auto-generated method stub
        super.setImageResource(resId);
        if (null != mAttacher) {
            mAttacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        // TODO Auto-generated method stub
        super.setImageURI(uri);
        if (null != mAttacher) {
            mAttacher.update();
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        // TODO Auto-generated method stub
        super.setImageDrawable(drawable);
        if(null !=mAttacher){
            mAttacher.update();
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        // TODO Auto-generated method stub
        super.setImageBitmap(bm);
        if(null !=mAttacher){
            mAttacher.update();
        }
    }

    @Override
    public void setOnDoubleTapListener(
            OnDoubleTapListener newOnDoubleTapListener) {
        // TODO Auto-generated method stub
        mAttacher.setOnDoubleTapListener(newOnDoubleTapListener);
    }

    @Override
    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        // TODO Auto-generated method stub
        mAttacher.setOnMatrixChangeListener(listener);
    }

    @Override
    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        // TODO Auto-generated method stub
        mAttacher.setOnPhotoTapListener(listener);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener listener) {
        // TODO Auto-generated method stub
        mAttacher.setOnLongClickListener(listener);
    }

    @Override
    public void setOnViewTapListener(OnViewTapListener listener) {
        // TODO Auto-generated method stub
        mAttacher.setOnViewTapListener(listener);
    }

    @Override
    public void setPhotoViewRotation(float rotationDegrees) {
        // TODO Auto-generated method stub
        mAttacher.setRotationTo(rotationDegrees);
    }

    @Override
    public void setRotationBy(float rotationDegrees) {
        // TODO Auto-generated method stub
        mAttacher.setRotationBy(rotationDegrees);
    }

    @Override
    public void setRotationTo(float rotationDegrees) {
        // TODO Auto-generated method stub
        mAttacher.setRotationTo(rotationDegrees);
    }

    @Override
    public void setScale(float scale) {
        // TODO Auto-generated method stub
        mAttacher.setScale(scale);
    }

    @Override
    public void setScale(float scale, boolean animate) {
        // TODO Auto-generated method stub
        mAttacher.setScale(scale, animate);
    }

    @Override
    public void setScale(float scale, float focalX, float focalY,
            boolean animate) {
        // TODO Auto-generated method stub
        mAttacher.setScale(scale, focalX, focalY, animate);
    }

    @Override
    public void setZoomTransitionDuration(int milliseconds) {
        // TODO Auto-generated method stub
        mAttacher.setZoomTransitionDuration(milliseconds);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (null != mAttacher) {
            mAttacher.setScaleType(scaleType);
        } else {
            mPendingScaleType = scaleType;
        }
    }

    @Override
    public void setZoomable(boolean zoomable) {
        // TODO Auto-generated method stub
        mAttacher.setZoomable(zoomable);
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        mAttacher.cleanup();
        super.onDetachedFromWindow();
    }

}