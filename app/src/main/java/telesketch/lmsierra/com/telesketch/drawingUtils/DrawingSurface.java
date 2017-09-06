package telesketch.lmsierra.com.telesketch.drawingUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;

public class DrawingSurface extends View{

    private static final String TAG = "DrawingSurface";

    private WeakReference<Context> context;

    private int width;
    private int height;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint bitmapPaint;

    private UserLines userLines;

    private float x = 0;
    private float y = 0;

    public DrawingSurface(Context context) {
        super(context);
        initDrawingCanvasView(context);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawingCanvasView(context);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawingCanvasView(context);
    }

    private void initDrawingCanvasView(Context context){

        this.context = new WeakReference<>(context);

        userLines = new UserLines();
        userLines.add(new UserLine(Color.GRAY));

        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        path.reset();

        this.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        reset();

    }

    public void reset() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        x = canvas.getWidth()/2;
        y = canvas.getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);

        if(userLines.getCurrent() != null) {
            canvas.drawPath(path, userLines.getCurrent());
        }
    }

    public void drawUp(){
        path.moveTo(x, y);
        y -= 1;
        drawLine();
    }

    public void drawDown(){
        path.moveTo(x, y);
        y += 1;
        drawLine();

    }

    public void drawLeft(){
        path.moveTo(x, y);
        x -= 1;
        drawLine();
    }

    public void drawRight(){
        path.moveTo(x, y);
        x += 1;
        drawLine();
    }

    private void drawLine() {
        path.lineTo(x, y);
        canvas.drawPath(path, userLines.getCurrent());
        invalidate();
    }


    public Bitmap getBitmap(){
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public void changeColor(){
        userLines.getCurrent().setColor(Color.RED);
    }
}
