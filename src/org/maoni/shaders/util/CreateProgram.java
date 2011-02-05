package org.maoni.shaders.util;

import java.util.List;

import static android.opengl.GLES20.*;

public enum CreateProgram {
	INSTANCE,
	;
	
	public int create (final List<Integer> shaderList) {
		int program = glCreateProgram();

		for(int shader : shaderList) {
			glAttachShader(program, shader);
		}
		
		glLinkProgram(program);

		if(GetLogInfo.INSTANCE.printLogInfo(this.name() + " Shader Program", program)) {
			program = 0;
		}
		return program;
	}

}
