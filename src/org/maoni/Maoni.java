package org.maoni;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class Maoni extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        GLSurfaceView glsv = new GLSurfaceView(this);
        glsv.setRenderer(new MainRenderer());
        setContentView(glsv);
    }
}