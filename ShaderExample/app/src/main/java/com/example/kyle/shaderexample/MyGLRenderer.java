package com.example.kyle.shaderexample;

import android.opengl.GLSurfaceView;
import android.opengl.GLES31;
import java.nio.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kyle on 3/12/2015.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer{

    int program;
    private int PositionHandle;

    private FloatBuffer vertexBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {
            // in counterclockwise order:
            0.0f,  0.622008459f, 0.0f,   // top
            -0.5f, -0.311004243f, 0.0f,   // bottom left
            0.5f, -0.311004243f, 0.0f    // bottom right
    };
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 0.0f };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);


        program = GLES31.glCreateProgram();
        int vertexShader = GLES31.glCreateShader(GLES31.GL_VERTEX_SHADER);
        int fragmentShader = GLES31.glCreateShader(GLES31.GL_FRAGMENT_SHADER);

        StringBuilder vertexSource = new StringBuilder();
        StringBuilder fragmentSource = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("normalmap_vs.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                vertexSource.append(line).append('\n');
            }
            reader.close();

        } catch (IOException e) {}

        try {
            BufferedReader reader = new BufferedReader(new FileReader("normalmap_fs.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                fragmentSource.append(line).append('\n');
            }
            reader.close();

        } catch (IOException e) {}

        System.out.println(fragmentSource);

        GLES31.glShaderSource(vertexShader, vertexSource.toString());
        GLES31.glCompileShader(vertexShader);
        GLES31.glShaderSource(fragmentShader, fragmentSource.toString());
        GLES31.glCompileShader(fragmentShader);

        GLES31.glAttachShader(program, vertexShader);
        GLES31.glAttachShader(program, fragmentShader);

        GLES31.glLinkProgram(program);
        GLES31.glValidateProgram(program);
        GLES31.glUseProgram(program);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES31.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES31.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES31.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES31.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES31.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES31.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES31.glGetUniformLocation(mProgram, "uMVPMatrix");
        //MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        //GLES31.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        //MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES31.glDisableVertexAttribArray(mPositionHandle);

    }
}
