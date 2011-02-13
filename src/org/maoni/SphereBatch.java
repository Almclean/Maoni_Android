package org.maoni;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class SphereBatch implements Batch {
	
	private final List<Float> vertexData;
	private final List<Float> normalData;
	private final List<Float> textureData;
	private final List<Integer> indexData;
	
	private final FloatBuffer vData;
	private final FloatBuffer nData;
	private final FloatBuffer tData;
	private final IntBuffer iData;
	
	private final int vertexLength;
	private final int normalLength;
	private final int textureLength;
	private final int indexLength;
	
	
	public SphereBatch(final float radius, final int latitudeBands, final int longitudeBands) {
		// Big shout out to http://learningwebgl.com/blog/?p=1253 for this.
		// Giant constructor !
		final float PI = (float) Math.PI;
		
		vertexData = new ArrayList<Float>();
		normalData = new ArrayList<Float>();
		textureData = new ArrayList<Float>();
		
		indexData = new ArrayList<Integer>();
		
		for (int latNumber = 0; latNumber <= latitudeBands; latNumber++) {
			float theta = latNumber * PI / latitudeBands;
			float sinTheta = (float) Math.sin(theta);
			float cosTheta = (float) Math.cos(theta);
			for (int longNumber = 0; longNumber <= longitudeBands; longNumber++) {
				float phi = longNumber * 2 * PI / longitudeBands;
				float sinPhi = (float) Math.sin(phi);
				float cosPhi = (float) Math.cos(phi);
				
				float x = cosPhi * sinTheta;
				float y = cosTheta;
				float z = sinPhi * sinTheta;
				
				float u = 1 - (longNumber / longitudeBands);
				float v = 1 - (latNumber / latitudeBands);
				
				// Dae the normals
				this.addNormalCoord(x);
				this.addNormalCoord(y);
				this.addNormalCoord(z);
				// Dae the textures
				this.addTextureCoord(u);
				this.addTextureCoord(v);
				// Dae the vertices
				this.addVertexCoord(radius * x);
				this.addVertexCoord(radius * y);
				this.addVertexCoord(radius * z);
			}
		}
		// Now for the Indices info
		
		for (int latNumber = 0; latNumber < latitudeBands; latNumber++) {
			for (int longNumber = 0; longNumber < longitudeBands; longNumber++) {
				int first = (latNumber * (longitudeBands +1)) + longNumber;
				int second = first + longitudeBands + 1;
				
				this.addIndexValue(first);
				this.addIndexValue(second);
				this.addIndexValue(first + 1);
				
				this.addIndexValue(second);
				this.addIndexValue(second + 1);
				this.addIndexValue(first + 1);
			}
		}
		
		// Allocate the buffers up front 
		vData = ByteBuffer.allocateDirect(this.vertexData.size() << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (float f : vertexData) {
			vData.put(f);
		}
		vData.rewind();
		
		nData = ByteBuffer.allocateDirect(this.normalData.size() << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (float f : normalData) {
			nData.put(f);
		}
		nData.rewind();
		
		tData = ByteBuffer.allocateDirect(this.textureData.size() << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		for (float f : textureData) {
			tData.put(f);
		}
		tData.rewind();
		
		iData = ByteBuffer.allocateDirect(this.indexData.size() << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		for (int i : indexData) {
			iData.put(i);
		}
		iData.rewind();
		
		this.vertexLength = vertexData.size();
		this.textureLength = textureData.size();
		this.normalLength = normalData.size();
		this.indexLength = indexData.size();
	}
	
	private void addVertexCoord(final float value) {
		 this.vertexData.add(value);
	}

	private void addNormalCoord(final float value) {
		this.normalData.add(value);
	}

	private void addTextureCoord(final float value) {
		this.textureData.add(value);
	}

	private void addIndexValue(final int value) {
		this.indexData.add(value);
	}

	@Override
	public FloatBuffer getVertexCoordData() {
		return vData; 
	}

	@Override
	public FloatBuffer getNormalCoordData() {
		return nData; 
	}

	@Override
	public FloatBuffer getTexCoordData() {
		return tData;
	}

	@Override
	public IntBuffer getIndexData() {
		return iData; 
	}
	
	@Override
	public int getIndexLength() {
		return indexLength;
	}

	@Override
	public int getNormalLength() {
		return normalLength;
	}

	@Override
	public int getVertexLength() {
		return vertexLength;
	}

	@Override
	public int getTextureLength() {
		return textureLength;
	}

}
