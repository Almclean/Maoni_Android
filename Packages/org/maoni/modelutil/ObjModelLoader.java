package org.maoni.modelutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.maoni.batchutil.Batch;
import org.maoni.batchutil.OBJBatch;

public enum ObjModelLoader implements ModelLoader {

	INSTANCE;
	
	/**
	 * @param is an InputStream pointing to the filename to load in and parse.
	 */
	@Override
	public Batch createBatch(final InputStream is) {
		
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
				
		String line;
		final OBJModel o = new OBJModel();
		
		try {
			while ((line = buf.readLine()) != null) {
				// Parse vertices
				if (line.startsWith("v ")) {
					o.parseVertexData(line);
				} else if (line.startsWith("f")) {
					o.parseFaceVertexData(line);
				} else if (line.startsWith("vt")) {
					o.parseTextureData(line);
				} else if (line.startsWith("vn")) {
					o.parseNormalData(line);
				} 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new OBJBatch(o);
	}

}
