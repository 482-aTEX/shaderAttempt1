package com.example.kyle.shaderexample;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Kyle on 3/12/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mainRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(31);

        mainRenderer = new MyGLRenderer();
        setRenderer(mainRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
