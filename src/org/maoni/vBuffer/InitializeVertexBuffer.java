package org.maoni.vBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static android.opengl.GLES20.*;

public enum InitializeVertexBuffer {
	INSTANCE,
	;
	
	public int createVertexBufferObject(float vertices[]) {
		IntBuffer positionBufferObject = IntBuffer.allocate(1);
		glGenBuffers(1, positionBufferObject);

		FloatBuffer fb = FloatBuffer.allocate(vertices.length);
		fb.put(vertices);
		fb.flip();
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject.);
		glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glVertexAttribPo
		glBindBuffer(GL_ARRAY_BUFFER, 0);
			
		return positionBufferObject;
	}
}
