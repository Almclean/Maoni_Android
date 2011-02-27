package org.maoni.modelutil;

import java.io.InputStream;

import org.maoni.batchutil.Batch;

public interface ModelLoader {
	
	public Batch createBatch(final InputStream is);

}
