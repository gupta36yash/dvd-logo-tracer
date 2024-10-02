package com.example.mygame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class TrailView extends View{
    private Paint paint;
    private List<Float[]> points;  // List to store x, y coordinates of each position

    public TrailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrailView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFFaaff00); // Set color (blue trail in this case)
        paint.setStrokeWidth(5f);  // Set the thickness of the trail

        points = new ArrayList<>();  // Initialize the points list
    }

    // Method to add the current position of the ImageView
    public void addPoint(float x, float y) {
        points.add(new Float[]{x, y});
        invalidate();  // Redraw the view with new points
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw a line between each consecutive points in the list
        for (int i = 1; i < points.size(); i++) {
            float startX = points.get(i - 1)[0];
            float startY = points.get(i - 1)[1];
            float endX = points.get(i)[0];
            float endY = points.get(i)[1];
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }
}
