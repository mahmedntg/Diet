package ntweb.info.dietproj.Classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by nasser on 19/11/17.
 */

public class ShapeImageView extends AppCompatImageView implements View.OnTouchListener
{

    //
    private int p1_bust_x = 0;
    private int p1_bust_y = 0;
    private int p2_bust_x = 0;
    private int p2_bust_y = 0;

    private int p1_waist_x = 0;
    private int p1_waist_y = 0;
    private int p2_waist_x = 0;
    private int p2_waist_y = 0;

    private int p1_hip_x = 0;
    private int p1_hip_y = 0;
    private int p2_hip_x = 0;
    private int p2_hip_y = 0;

    private int p1_x = 0;
    private int p1_y = 0;
    private int p2_x = 0;
    private int p2_y = 0;

    private int p_move_x = 0;
    private int p_move_y = 0;
    public ShapeImageView(Context context)
    {

        super(context);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setupBust(Point p1, Point p2)
    {
        p1_bust_x = p1.x;
        p1_bust_y = p1.y;
        p2_bust_x = p2.x;
        p2_bust_y = p2.y;
        invalidate();

    }

    public void setupWaist(Point p1, Point p2)
    {
        p1_waist_x = p1.x;
        p1_waist_y = p1.y;
        p2_waist_x = p2.x;
        p2_waist_y = p2.y;
        invalidate();

    }

    public void setupHip(Point p1, Point p2)
    {
        p1_hip_x = p1.x;
        p1_hip_y = p1.y;
        p2_hip_x = p2.x;
        p2_hip_y = p2.y;
        invalidate();

    }
    public void drawP1(Point p)
    {
        resetPoint();
        p1_x = p.x;
        p1_y = p.y;


        invalidate();
    }
    public void drawP2(Point p)
    {
        p2_x = p.x;
        p2_y = p.y;
        invalidate();
    }


    public void drawMovePoint(Point p)
    {
        p_move_x = p.x;
        p_move_y = p.y;
        invalidate();
    }
    public void resetPoint()
    {
        p1_x = 0;
        p1_y = 0;
        p2_x = 0;
        p2_y = 0;
    }

    public void resetBrustline()
    {
        p1_bust_x = 0;
        p1_bust_y = 0;
        p2_bust_x = 0;
        p2_bust_y = 0;
    }

    public void resetWaistline()
    {
        p1_waist_x = 0;
        p1_waist_y = 0;
        p2_waist_x = 0;
        p2_waist_y = 0;
    }


    public void resetHipline()
    {
        p1_hip_x = 0;
        p1_hip_y = 0;
        p2_hip_x = 0;
        p2_hip_y = 0;
    }

    public void resetMovePoint()
    {
        p_move_x = 0;
        p_move_y = 0;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.w("TOUCH", "We Have This Action event: "+ motionEvent.getAction());
        return true;
    }

    public int getBustlength()
    {
        return (int) Math.sqrt(Math.pow(p2_bust_x - p1_bust_x, 2) - Math.pow(p2_bust_y - p1_bust_y, 2));
    }
    public int getWaistlength()
    {
        return (int) Math.sqrt(Math.pow(p2_waist_x - p1_waist_x, 2) - Math.pow(p2_waist_y - p1_waist_y, 2));
    }
    public int getHiplength()
    {
        return (int) Math.sqrt(Math.pow(p2_hip_x - p1_hip_x, 2) - Math.pow(p2_hip_y - p1_hip_y, 2));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(Color.RED);
        p.setStrokeWidth((float)3.5);;
        // Drawing bust line
        canvas.drawLine(p1_bust_x, p1_bust_y, p2_bust_x, p2_bust_y, p);

        // Drawing waist line
        canvas.drawLine(p1_waist_x, p1_waist_y, p2_waist_x, p2_waist_y, p);

        // Drawing hip line
        canvas.drawLine(p1_hip_x, p1_hip_y, p2_hip_x, p2_hip_y, p);

        canvas.drawCircle(p1_x,p1_y,5,p);
        canvas.drawCircle(p2_x,p2_y,5,p);
        canvas.drawCircle(p_move_x,p_move_y,10,p);

    }
}
