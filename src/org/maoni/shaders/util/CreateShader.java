package org.maoni.shaders.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import static android.opengl.GLES20.*;

public enum CreateShader {
	VERTEX(GL_VERTEX_SHADER),
	FRAGMENT(GL_FRAGMENT_SHADER)
	;
	
	final int shaderType;
	final String TAG = "CreateShader";
	
	private CreateShader(final int shaderType) {
		this.shaderType = shaderType;
	}
	
	public int load(final InputStream shaderLocation) {
		int shader = glCreateShader(shaderType);
		Log.i(TAG, "Shader ID = " + shader);
		final String shaderSource = loadShaderFromFile(shaderLocation);

		glShaderSource(shader, shaderSource);
		glCompileShader(shader);
		
		int[] compiled = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] != GL_TRUE) {
            Log.e(TAG, "Could not compile shader " + this.name() + ":" + glGetShaderInfoLog(shader));
            glDeleteShader(shader);
            shader = 0;
        }
		return shader;
	}
	
	private String loadShaderFromFile(final InputStream shaderLocation) {
		final StringBuffer sb = new StringBuffer();
		BufferedReader buf;
		String line;
		try {
			buf = new BufferedReader(new InputStreamReader(shaderLocation));
			while ((line = buf.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}


}
