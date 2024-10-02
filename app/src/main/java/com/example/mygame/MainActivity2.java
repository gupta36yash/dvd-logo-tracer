package com.example.mygame;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;

public class MainActivity2 extends AppCompatActivity {

    // Variables to store speed and direction
    private float xVelocity;
    private float yVelocity;

    private Handler handler = new Handler();
    private ImageView svgImageView;
    private TrailView trailView;
    private boolean isMoving = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        svgImageView = findViewById(R.id.svg_image);
        trailView = findViewById(R.id.trail_view);

        final View rootView = findViewById(android.R.id.content);

        // Create an OnGlobalLayoutListener
        ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener after the layout is done
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Get the dimensions of the screen and image
                float screenWidth = rootView.getWidth();
                float screenHeight = rootView.getHeight();
                float imageWidth= svgImageView.getWidth();
                float imageHeight= svgImageView.getHeight();

                xVelocity = 0.15f* screenWidth;
                yVelocity = 0.15f * screenHeight;


                // Runnable to move the ImageView diagonally
                Runnable moveImage = new Runnable() {
                    @Override
                    public void run() {
                        if (isMoving) {
                            // Get the current position
                            float currentX = svgImageView.getX();
                            float currentY = svgImageView.getY();

                            // Add the current position to the trail
                            trailView.addPoint(currentX + imageWidth / 2, currentY + imageHeight / 2); // Center of the image

                            // Update position by the velocity
                            float newX = currentX + xVelocity;
                            float newY = currentY + yVelocity;

                            // Check if the ImageView hits the right or left edge of the screen
                            if (newX < -imageWidth/2+1 || newX + imageWidth > screenWidth + imageWidth/2) {
                                xVelocity = -xVelocity; // Reverse X direction
                            }

                            // Check if the ImageView hits the top or bottom edge of the screen
                            if (newY < -imageHeight/2+1 || newY + imageHeight  > screenHeight + imageHeight/2) {
                                yVelocity = -yVelocity; // Reverse Y direction
                            }

                            // Set the new position
                            svgImageView.setX(newX);
                            svgImageView.setY(newY);
                        }
                        handler.postDelayed(this, 8);
                    }
                };

                // Start the movement
                handler.post(moveImage);
            }
        };

        // Add the listener to the root view's ViewTreeObserver
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isMoving = !isMoving;
                    return true;
                }
                return false;
            }
        });
    }
}
