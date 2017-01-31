package com.msapps.touchdetector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawerView extends View {

    private static final String TAG = "DrawerView";

    private static final int MAX_TOUCH_COUNTS = 10;
    private static final List<MotionEvent.PointerCoords> CACHE_POINTER_COORDS = new ArrayList<>();
    public static final int[] COLORS = new int[]{
            Color.parseColor("#f44242"),
            Color.parseColor("#f48642"),
            Color.parseColor("#f4ee42"),
            Color.parseColor("#aaf442"),
            Color.parseColor("#42f4a7"),
            Color.parseColor("#428ff4"),
            Color.parseColor("#8042f4"),
            Color.parseColor("#dc42f4"),
            Color.parseColor("#f44283"),
            Color.parseColor("#081907")
    };

    static {
        for (int i = 0; i < MAX_TOUCH_COUNTS; i++) {
            CACHE_POINTER_COORDS.add(new MotionEvent.PointerCoords());
        }
    }

    private Paint mTouchPaint = new Paint();
    private PointersListener mPointersListener;
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
            if (mPointersListener != null) {
                mPointersListener.onTouch(mTouchPoints);
            }
            invalidate();
            return false;
        }
        int count = event.getPointerCount();
        for (int i = 0; i < count; i++) {
            MotionEvent.PointerCoords coords = CACHE_POINTER_COORDS.get(i);
            event.getPointerCoords(i, coords);
            mTouchPoints.add(coords);
        }
        if (mPointersListener != null) {
            mPointersListener.onTouch(mTouchPoints);
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = 0;
        for (MotionEvent.PointerCoords coords : mTouchPoints) {
            mTouchPaint.setColor(COLORS[i]);
            i++;
            canvas.drawCircle(coords.x, coords.y, 100f, mTouchPaint);
        }
    }

    public void setPointerListener(PointersListener l) {
        mPointersListener = l;
    }

    public interface PointersListener {
        void onTouch(List<MotionEvent.PointerCoords> pointers);
    }
}
