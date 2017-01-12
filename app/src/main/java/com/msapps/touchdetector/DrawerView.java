package com.msapps.touchdetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawerView extends View {
    private static final String TAG = "DrawerView";

    private static final int MAX_TOUCH_COUNTS = 10;
    private static final List<MotionEvent.PointerCoords> CACHE_POINTER_COORDS = new ArrayList<>();

    static {
        for (int i = 0; i < MAX_TOUCH_COUNTS; i++) {
            CACHE_POINTER_COORDS.add(new MotionEvent.PointerCoords());
        }
    }

    private Paint mTouchPaint = new Paint();
    private List<MotionEvent.PointerCoords> mTouchPoints = new ArrayList<>();

    public DrawerView(Context context) {
        super(context);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTouchPaint.setColor(Color.RED);
        mTouchPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchPoints.clear();
        if (event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL) {
            invalidate();
            return false;
        }
        int count = event.getPointerCount();
        for (int i = 0; i < count; i++) {
            MotionEvent.PointerCoords coords = CACHE_POINTER_COORDS.get(i);
            event.getPointerCoords(i, coords);
            mTouchPoints.add(coords);
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (MotionEvent.PointerCoords coords : mTouchPoints) {
            canvas.drawCircle(coords.x, coords.y, 100f, mTouchPaint);
        }
    }
}
