package org.maoni;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.maoni.shaders.util.CreateProgram;
import org.maoni.shaders.util.CreateShader;

import static android.opengl.GLES20.*;

import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class MainRenderer implements Renderer {
	
	private static final String _VERTEX_SHADER_LOCATION = "org/maoni/shaders/normalProt.vert.shader";
	private static final String _FRAGMENT_SHADER_LOCATION = "org/maoni/shaders/normalProt.frag.shader";
	
	private float[] mProjMatrix = new float[16];
	private int _PROGRAM = 0;

	@Override
	public void onDrawFrame(GL10 glUnused) {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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
		final List<Integer> shaderList = new ArrayList<Integer>();
		shaderList.add(CreateShader.VERTEX.load(ClassLoader.getSystemResourceAsStream(_VERTEX_SHADER_LOCATION)));
		shaderList.add(CreateShader.FRAGMENT.load(ClassLoader.getSystemResourceAsStream(_FRAGMENT_SHADER_LOCATION)));
		_PROGRAM = CreateProgram.INSTANCE.create(shaderList);

	}

}
