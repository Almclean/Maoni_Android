package org.maoni.modelutil;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.maoni.R;
import org.maoni.batchutil.Batch;

import android.content.res.Resources;

public class OBJModelTests {
	
	@Test
	public void testThatParserCanHandleVertexInfoSingleLine() {
		
		final String vertexdata = "v 1.0 2.0 3.0";
		
		OBJModel o = new OBJModel();
		
		o.parseVertexData(vertexdata);
		
		List<Float> result = o.getVertices();
		
		List<Float> expected = new ArrayList<Float>();
		expected.add(1.0f);
		expected.add(2.0f);
		expected.add(3.0f);
		
		assertArrayEquals(expected.toArray(), result.toArray());
	}
	
	@Test
	public void testThatParserCanHandleFaceData() {
		final String faceData = "f -33/-395/-18 -31/-393/-17 -34/-396/-18";
		
		OBJModel o = new OBJModel();
		
		o.parseFaceVertexData(faceData);
		List<Integer> result = o.getIndices();
		List<Integer> expected = new ArrayList<Integer>();
		
		expected.add(33);
		expected.add(31);
		expected.add(34);
		
		assertArrayEquals(expected.toArray(), result.toArray());
	}
	
	@Test
	public void testThatParserCanHandleTexData() {
		final String texData = "vt 270.729767 251.744568 0.000000";
		
		OBJModel o = new OBJModel();
		
		o.parseTextureData(texData);
		List<Float> result = o.getTextureCoords();
		List<Float> expected = new ArrayList<Float>();
		
		expected.add(270.729767f);
		expected.add(251.744568f);
				
		assertArrayEquals(expected.toArray(), result.toArray());
	}
	
	@Test
	public void testThatParserCanHandleNormalData() {
		final String normalData = "vn 0.000000 0.461007 -0.887397";
		
		OBJModel o = new OBJModel();
		
		o.parseNormalData(normalData);
		List<Float> result = o.getNormalCoords();
		List<Float> expected = new ArrayList<Float>();
		
		expected.add(0.000000f);
		expected.add(0.461007f);
		expected.add(-0.887397f);
				
		assertArrayEquals(expected.toArray(), result.toArray());
	}

}
