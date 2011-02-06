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
		
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject.get(0));
		glBufferData(GL_ARRAY_BUFFER, fb.remaining() << 2, fb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
			
		positionBufferObject.rewind();
		return positionBufferObject.get(0);
	}
}
