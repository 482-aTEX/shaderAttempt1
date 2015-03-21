package com.example.kyle.shaderexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.opengl.GLES31;
import android.opengl.GLES31Ext;
import java.lang.String;

import android.util.Log;
import java.io.*;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

public class ShaderExample extends Activity {

    private static final String TAG = "ShaderExample";
    StringBuffer vs;
    StringBuffer fs;
    private MyGLSurfaceView mainView;
    Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //readShader();
        //int program = createProgram(vs.toString(), fs.toString());

        //=====

        mainView = new MyGLSurfaceView(this);
        setContentView(mainView);

        //=====

        // Create a new GLSurfaceView - this holds the GL Renderer
        /*mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setEGLContextClientVersion(2);
        renderer = new Renderer(this);
        mGLSurfaceView.setRenderer(renderer);


        renderer.setShader(this.NORMALMAP_SHADER);*/

        //setContentView(R.layout.activity_shader_example);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shader_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES31.GL_VERTEX_SHADER, vertexSource);
        int pixelShader = loadShader(GLES31.GL_FRAGMENT_SHADER, fragmentSource);

        int program = GLES31.glCreateProgram();
        if (program != 0) {
            GLES31.glAttachShader(program, vertexShader);
            //checkGlError("glAttachShader");
            GLES31.glAttachShader(program, pixelShader);
            //checkGlError("glAttachShader");
            GLES31.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES31.glGetProgramiv(program, GLES31.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES31.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES31.glGetProgramInfoLog(program));
                GLES31.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES31.glCreateShader(shaderType);
        if (shader != 0) {
            GLES31.glShaderSource(shader, source);
            GLES31.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES31.glGetShaderiv(shader, GLES31.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES31.glGetShaderInfoLog(shader));
                GLES31.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private void readShader() {

        vs = new StringBuffer();
        fs = new StringBuffer();

        // read the files
        try {

            InputStream inputStream = getResources().openRawResource(R.raw.normalmap_vs);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            String read = in.readLine();
            while (read != null) {
                vs.append(read + "\n");
                read = in.readLine();
            }
            vs.deleteCharAt(vs.length() - 1);



            inputStream = getResources().openRawResource(R.raw.normalmap_ps);
            in = new BufferedReader(new InputStreamReader(inputStream));

            read = in.readLine();
            while (read != null) {
                fs.append(read + "\n");
                read = in.readLine();
            }
            fs.deleteCharAt(fs.length() - 1);

        } catch (Exception e) {
            Log.d("ERROR-readingShader", "Could not read shader: " + e.getLocalizedMessage());
        }

        return;
    }*/

}
