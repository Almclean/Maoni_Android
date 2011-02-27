package org.maoni;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.maoni.batchutil.Batch;
import org.maoni.batchutil.OBJBatch;
import org.maoni.batchutil.SphereBatch;
import org.maoni.modelutil.ObjModelLoader;
import org.maoni.shaderutil.CreateProgram;
import org.maoni.shaderutil.CreateShader;

import static android.opengl.GLES20.*;
import android.content.res.Resources;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;
import android.util.Log;

public class MainRenderer implements Renderer {
	
	private final String TAG = "MainRenderer";

	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	private float[] mMMatrix = new float[16];
	
	public final Resources res;
	private int _PROGRAM;
	private int matrixUniformLoc;
	private int vertexHandle;
	private final Batch ob;


	
	
	public MainRenderer(Resources resources) {
		this._PROGRAM = 0;
		this.res = resources;
		this.ob = ObjModelLoader.INSTANCE.createBatch(res.openRawResource(R.raw.bendychair));
	}
		
	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(_PROGRAM);
		matrixUniformLoc = glGetUniformLocation(_PROGRAM, "mvpMatrix");
		
    	glVertexAttribPointer(vertexHandle, 3, GL_FLOAT, false, 0, ob.getVertexCoordData());
		glEnableVertexAttribArray(vertexHandle);
        
		long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        
        Matrix.setIdentityM(mMMatrix, 0);
        Matrix.translateM(mMMatrix, 0, 0, 0, 2.0f);
        Matrix.rotateM(mMMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

		glUniformMatrix4fv(matrixUniformLoc, 0, false, mMVPMatrix, 0);
		glDrawElements(GL_TRIANGLES, ob.getIndexLength(), GL_UNSIGNED_INT, ob.getIndexData());
				
		glUseProgram(0);
	}

	@Override
	public void onSurfaceChanged(GL10 ignored, int width, int height) {
		glViewport(0, 0, width, height);
		glClearDepthf(1.0f);
		
		float ratio = (float) width / height;
		Matrix.frustumM(mProjMatrix, 0, ratio, -ratio, -1.0f, 1.0f, 1.0f, 1000.0f);
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
	
		Log.e(TAG, "Vertex Length = " + ob.getVertexLength());
		Log.e(TAG, "Index Length = " + ob.getIndexLength());
		
    	Matrix.setLookAtM(mVMatrix, 0, 0.0f, 0.0f, -75.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    	
	}

}
