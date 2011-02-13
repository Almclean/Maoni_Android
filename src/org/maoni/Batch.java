package org.maoni;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface Batch {
	public FloatBuffer getVertexCoordData();
	public FloatBuffer getNormalCoordData();
	public FloatBuffer getTexCoordData();
	public IntBuffer getIndexData();
	public int getTextureLength();
	public int getVertexLength();
	public int getNormalLength();
	public int getIndexLength();
}
