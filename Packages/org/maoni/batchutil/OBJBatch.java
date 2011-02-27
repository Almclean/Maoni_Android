package org.maoni.batchutil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.maoni.modelutil.OBJModel;

public class OBJBatch implements Batch {
	
	private final FloatBuffer vData;
	private final FloatBuffer nData;
	private final FloatBuffer tData;
	private final IntBuffer iData;


	public OBJBatch(final OBJModel o) {
		
		vData = ByteBuffer.allocateDirect(o.getVertices().size() << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (Float f : o.getVertices()) {
			vData.put(f);
		}
		vData.rewind();
		
		nData = ByteBuffer.allocateDirect(o.getNormalCoords().size() << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (Float f : o.getNormalCoords()) {
			nData.put(f);
		}
		nData.rewind();
		
		tData = ByteBuffer.allocateDirect(o.getTextureCoords().size() << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (Float f : o.getTextureCoords()) {
			tData.put(f);
		}
		tData.rewind();
		
		iData = ByteBuffer.allocateDirect(o.getIndices().size() << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		for (Integer i : o.getIndices()) {
			iData.put(i);
		}
		iData.rewind();
		
	}
	
	
	@Override
	public FloatBuffer getVertexCoordData() {
		return this.vData;
	}

	@Override
	public FloatBuffer getNormalCoordData() {
		return this.nData;
	}

	@Override
	public FloatBuffer getTexCoordData() {
		return this.tData;
	}

	@Override
	public IntBuffer getIndexData() {
		return this.iData;
	}

	@Override
	public int getTextureLength() {
		return this.tData.capacity();
	}

	@Override
	public int getVertexLength() {
		return this.vData.capacity();
	}

	@Override
	public int getNormalLength() {
		return this.nData.capacity();
	}

	@Override
	public int getIndexLength() {
		return this.iData.capacity();
	}

}
