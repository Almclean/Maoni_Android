package org.maoni;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class Maoni extends Activity {
    private static final String TAG = "Main.java (Maoni)";
	/** Called when the activity is first created. */
    @Override			
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In onCreate.");
        GLSurfaceView glsv = new GLSurfaceView(this);
        glsv.setEGLContextClientVersion(2);
        Log.i(TAG, "Created GLSurfaceView.");
        glsv.setRenderer(new MainRenderer(this.getApplicationContext().getResources()));
        Log.i(TAG, "Created MainRenderer");
        setContentView(glsv);
    }
}