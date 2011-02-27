package org.maoni;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

public class ES2SurfaceView extends GLSurfaceView {
	
	private final String TAG = "ES2SurfaceView";

	public ES2SurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ES2SurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void talkToTheRenderer() {
		Log.i(TAG, "Hello, im talking to the renderer : ");
	}

}
