package com.example.amanarora.dottedbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Aman's Laptop on 5/8/2016.
 */
class DottedBar extends View {

    private Paint drawPaint;
    private float animatedRadius;
    private float dotRadius;
    private int dotCount;
    private float expandedRadius;
    private float dotSpace;
    private float circleViewWidth;
    private ExpandAnimation expandAnimation;
    private int dotPosition = 0;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    int height;
    int width;

    public DottedBar(Context context) {
        super(context);
        init(context);

    }

    private void init(Context context) {
        setupPaint();
    }

    private void setupPaint() {

        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setColor(Color.BLACK);
        drawPaint.setDither(true);
        drawPaint.setStyle(Paint.Style.FILL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DottedBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeAttributes(attrs);
    }

    public DottedBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initializeAttributes(attrs);
    }

    public DottedBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initializeAttributes(attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeaureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeaureSpec);

        setMeasuredDimension(width, height);

        circleViewWidth = dotCount * dotRadius * 2 + (dotCount - 1) * dotSpace; // Calculating the width of all circles along with space in between = 30 //
    }

    private void initializeAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.DottedBar,
                    0, 0);

            try {
                dotCount = typedArray.getInteger(R.styleable.DottedBar_dotCount, 4);
                dotRadius = typedArray.getDimension(R.styleable.DottedBar_dotSize, 30);
                expandedRadius = typedArray.getDimension(R.styleable.DottedBar_expandedDotSize, (int) (dotRadius + 10));
                dotSpace = typedArray.getDimension(R.styleable.DottedBar_dotSpace, 30);

            } finally {
                typedArray.recycle();
            }

        } else {
            dotCount = 5;
            dotRadius = 30;
            expandedRadius = dotRadius + 10;
            dotSpace = 30;
        }
    }

    public void onDraw(Canvas canvas) {

        int halfHeight = getHeight() / 2;

        int deltaX = 0;
        for (int i = 0; i < dotCount; i++) {

            if (dotPosition == i) {
                canvas.drawCircle((getWidth() - circleViewWidth) / 2 + dotRadius + deltaX, halfHeight, dotRadius + animatedRadius, drawPaint);
            } else if ( (dotPosition - 1) == i || ( i == (dotCount - 1) && dotPosition == 0)) {

                canvas.drawCircle((getWidth() - circleViewWidth) / 2 + dotRadius + deltaX, halfHeight, expandedRadius - animatedRadius, drawPaint);
            } else {
                canvas.drawCircle((getWidth() - circleViewWidth) / 2 + dotRadius + deltaX, halfHeight, dotRadius, drawPaint);
            }
            deltaX += (dotRadius*2 + dotSpace);
        }
        Log.d("Draw", "Inside onDraw");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    public void stopAnimation() {
        this.clearAnimation();
        postInvalidate();

    }

    public void resetAnimation() {
        stopAnimation();
        dotPosition = 0;
        startAnimation();

    }

    private void startAnimation() {

        expandAnimation = new ExpandAnimation();
        expandAnimation.setDuration(200);
        expandAnimation.setRepeatCount(Animation.INFINITE);
        expandAnimation.setInterpolator(new LinearInterpolator());
        expandAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                dotPosition++;

                if (dotPosition == dotCount) {

                    dotPosition = 0;
                }
            }
        });
        startAnimation(expandAnimation);
    }

    private class ExpandAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            animatedRadius = (expandedRadius - dotRadius) * interpolatedTime;
            Log.d("Animation", "Inside Applytransformation");
            invalidate();

        }

    }
}
