package com.example.kyle.shaderexample;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Kyle on 3/12/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private String vertexShaderCode;
    private String fragmentShaderCode;

    public MyGLSurfaceView(Context context) {
        super(context);


        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        vertexShaderCode = readShader(R.raw.test_vs);
        fragmentShaderCode = readShader(R.raw.test_fs);
        mRenderer.setShaders(vertexShaderCode, fragmentShaderCode);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    public String readShader(int fd) {
        String s = "";
        StringBuffer vs = new StringBuffer();

        try {
            InputStream inputStream = getContext().getResources().openRawResource(fd);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String read = in.readLine();
            while (read != null) {
                vs.append(read + "\n");
                read = in.readLine();
            }
            vs.deleteCharAt(vs.length() - 1);
            s = vs.toString();
        } catch (Exception e) {
            Log.d("ERROR-readingShader", "Could not read shader: " + e.getLocalizedMessage());
        }
        Log.d("Success","Read shader");
        return s;
    }
}
