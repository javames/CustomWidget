package com.example.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * 下拉回弹效果，上拉回弹效果
 */

public class BounceableRelativeLayout extends RelativeLayout {


    private static Scroller mScroller;
    private GestureDetector mGestureDetector;
    public BounceableRelativeLayout(Context context) {
        this(context, null);
    }
    public BounceableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                reset(0,0);
                break;
                default:
                    return mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    public BounceableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        setLongClickable(true);
        mScroller=new Scroller(context);
        mGestureDetector=new GestureDetector(context,new CustomGestureDetector());
    }

    private class CustomGestureDetector implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int disY= (int) ((distanceY-0.5)/2);
            beginScroll(0,disY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void reset(int x,int y){
        int dx=x-mScroller.getFinalX();
        int dy=y-mScroller.getFinalY();
        beginScroll(dx,dy);
    }
    private void beginScroll(int dx,int dy){
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(),dx,dy);
        invalidate();
    }
}
