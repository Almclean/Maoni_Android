package org.maoni;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.maoni.shaders.util.CreateProgram;
import org.maoni.shaders.util.CreateShader;

import static android.opengl.GLES20.*;
import android.content.res.Resources;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;
import android.util.Log;

public class MainRenderer implements Renderer {
	
	private final String TAG = "MainRenderer";
	private final float[] vertexPositions = {
            -1.0f, -0.5f, 0, -0.5f, 0.0f,
            1.0f, -0.5f, 0, 1.5f, -0.0f,
            0.0f,  1.11803399f, 0, 0.5f,  1.61803399f };
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	private float[] mMMatrix = new float[16];
	
	public final Resources res;
	private int _PROGRAM;
	private FloatBuffer fb;
	private int matrixUniformLoc;
	private int vertexHandle;
	
	
	
	public MainRenderer(Resources resources) {
		this._PROGRAM = 0;
		this.res = resources;
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepthf(1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(_PROGRAM);
		matrixUniformLoc = glGetUniformLocation(_PROGRAM, "mvpMatrix");
		fb.position(0);

		glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, false, 5 << 2, fb);
		glEnableVertexAttribArray(vertexHandle);
        
		long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        
        Matrix.setIdentityM(mMMatrix, 0);
        Matrix.translateM(mMMatrix, 0, 0, 0, -1.0f);
        Matrix.rotateM(mMMatrix, 0, angle, 0, 0, 1.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(matrixUniformLoc, 0, false, mMVPMatrix, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glUseProgram(0);
	}

	@Override
	public void onSurfaceChanged(GL10 ignored, int width, int height) {
		glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		Matrix.frustumM(mProjMatrix, 0, ratio, -ratio, -1.0f, 1.0f, 1.0f, 100.0f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// setup the shaders and link
		Log.i(TAG, "In onSurfaceCreate.");
		final List<Integer> shaderList = new ArrayList<Integer>();
		shaderList.add(CreateShader.VERTEX.load(res.openRawResource(R.raw.vertshader)));
		shaderList.add(CreateShader.FRAGMENT.load(res.openRawResource(R.raw.fragshader)));
		_PROGRAM = CreateProgram.INSTANCE.create(shaderList);
		
		vertexHandle = glGetAttribLocation(_PROGRAM, "vVector");
		
		this.fb = ByteBuffer.allocateDirect(vertexPositions.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		fb.put(vertexPositions);
		fb.flip();

		Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0, 0, 0, 0.0f, 1.0f, 0.0f);
	}

}
