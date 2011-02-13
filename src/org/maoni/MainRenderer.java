package org.maoni;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import org.maoni.shaders.util.CreateProgram;
import org.maoni.shaders.util.CreateShader;
import org.maoni.shaders.util.GetLogInfo;

import static android.opengl.GLES20.*;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;
import android.util.Log;

public class MainRenderer implements Renderer {
	
	private final String TAG = "MainRenderer";
	
	private static final float vertexPositions[] = { 
		-1.0f, -0.5f, 0,
		1.0f, -0.5f, 0,
		0.0f,  1.0f, 0};
	
	private static final float colours[] = {
		 1.0f, 0.0f, 0.0f, 1.0f,
		 0.0f, 1.0f, 0.0f, 1.0f,
		 0.0f, 0.0f, 1.0f, 1.0f, };
	
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	private float[] mMMatrix = new float[16];
	
	public final Resources res;
	private int _PROGRAM;
	private FloatBuffer fb;
	private int matrixUniformLoc;
	private int vertexHandle;
	private FloatBuffer cb;
	private final SphereBatch sb;

	private int colorHandle;
	
	
	public MainRenderer(Resources resources) {
		this._PROGRAM = 0;
		this.res = resources;
		sb = new SphereBatch(2, 30, 30);
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(_PROGRAM);
		matrixUniformLoc = glGetUniformLocation(_PROGRAM, "mvpMatrix");
		
    	glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, false, 0, sb.getVertexCoordData());
		glEnableVertexAttribArray(vertexHandle);
        
		long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        
        Matrix.setIdentityM(mMMatrix, 0);
        Matrix.translateM(mMMatrix, 0, 0, 0, 4.0f);
        Matrix.rotateM(mMMatrix, 0, angle, 0.0f, 0.0f, 1.0f);
        Matrix.rotateM(mMMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(matrixUniformLoc, 0, false, mMVPMatrix, 0);
		glDrawElements(GL_POINTS, sb.getIndexLength() * 2, GL_UNSIGNED_SHORT, sb.getIndexData());
		glUseProgram(0);
	}

	@Override
	public void onSurfaceChanged(GL10 ignored, int width, int height) {
		glViewport(0, 0, width, height);
		glClearDepthf(1.0f);
		
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
	
		Log.e(TAG, "Vertex Length = " + sb.getVertexLength());
		Log.e(TAG, "Index Length = " + sb.getIndexLength());
		
    	Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5.0f, 0, 0, 0, 0.0f, 1.0f, 0.0f);
	}

}
