package org.maoni.shaderutil;

import java.util.List;

import android.util.Log;

import static android.opengl.GLES20.*;

public enum CreateProgram {
	INSTANCE,
	;
	
	private final String TAG = "CreateProgram";
	public int create (final List<Integer> shaderList) {
		int program = glCreateProgram();

		for(int shader : shaderList) {
			glAttachShader(program, shader);
		}
		
		glLinkProgram(program);

		int[] linkStatus = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GL_TRUE) {
            Log.e(TAG, "Could not link program: ");
            Log.e(TAG, glGetProgramInfoLog(program));
            glDeleteProgram(program);
            program = 0;
        }
		return program;
	}

}
