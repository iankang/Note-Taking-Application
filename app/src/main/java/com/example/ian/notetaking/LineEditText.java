package com.example.ian.notetaking;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;


public class LineEditText extends AppCompatEditText {

    private Rect mRect;
    private Paint mpaint;


    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mpaint =  new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(2);
        mpaint.setColor(0xFFFFD966);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = ((View) this.getParent()).getHeight();

        int lineHeight = getLineHeight();

        int numberOfLines = height/lineHeight;

        Rect r = mRect;
        Paint p = mpaint;

        int baseline = getLineBounds(0, r);

        for (int i = 0; i<numberOfLines; i++){
            canvas.drawLine(r.left,baseline+1,r.right,baseline+1, p);

            baseline+=lineHeight;
        }

        super.onDraw(canvas);
    }
}
