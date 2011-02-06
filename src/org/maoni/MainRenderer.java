package org.maoni;

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
import android.provider.OpenableColumns;
import android.util.Log;

public class MainRenderer implements Renderer {
	
	private final String TAG = "MainRenderer";
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	public final Resources res;
	private int _PROGRAM;
	
	public MainRenderer(Resources resources) {
		this._PROGRAM = 0;
		this.res = resources;
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		glClearDepthf(1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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
		
		Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0, 0, 0, 0.0f, 1.0f, 0.0f);
	}

}
