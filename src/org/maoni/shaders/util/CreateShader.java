package org.maoni.shaders.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import static android.opengl.GLES20.*;

public enum CreateShader {
	VERTEX(GL_VERTEX_SHADER),
	FRAGMENT(GL_FRAGMENT_SHADER)
	;
	
	final int shaderType;
	
	private CreateShader(final int shaderType) {
		this.shaderType = shaderType;
	}
	
	public int load(final String shaderLocation) {
		int shader = glCreateShader(shaderType);
		
		final String shaderSource = loadShaderFromFile(shaderLocation);
		
		glShaderSource(shader, shaderSource);
		glCompileShader(shader);
	
		if(GetLogInfo.INSTANCE.printLogInfo(this.name() + " Shader", shader)) {
			shader = 0;
		}
		return shader;
	}
	
	public int load(final InputStream shaderLocation) {
		int shader = glCreateShader(shaderType);
		
		final String shaderSource = loadShaderFromFile(shaderLocation);

		glShaderSource(shader, shaderSource);
		glCompileShader(shader);
	
		if(GetLogInfo.INSTANCE.printLogInfo(this.name() + " Shader", shader)) {
			shader = 0;
		}
		return shader;
	}

	private String loadShaderFromFile(final String shaderLocation) {
		final StringBuffer sb = new StringBuffer();
		BufferedReader buf;
		String line;
		try {
			buf = new BufferedReader(new FileReader(shaderLocation));
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
