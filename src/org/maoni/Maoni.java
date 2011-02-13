package org.maoni;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Maoni extends Activity implements OnTouchListener {
    private static final String TAG = "Main.java (Maoni)";
	/** Called when the activity is first created. */
    @Override			
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "In onCreate.");
        GLSurfaceView glsv = new ES2SurfaceView(this);
        glsv.setEGLContextClientVersion(2);
        Log.i(TAG, "Created GLSurfaceView.");
        glsv.setRenderer(new MainRenderer(this.getApplicationContext().getResources()));
        Log.i(TAG, "Created MainRenderer");
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        glsv.setOnTouchListener(this);
        setContentView(glsv);
    }
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ES2SurfaceView es2 = (ES2SurfaceView) v;
		dumpEvent(event);
		es2.talkToTheRenderer();
		return true;
	}
	
	private void dumpEvent(MotionEvent event) {
		   String names[] = { "DOWN" , "UP" , "MOVE" , "CANCEL" , "OUTSIDE" ,
		      "POINTER_DOWN" , "POINTER_UP" , "7?" , "8?" , "9?" };
		   StringBuilder sb = new StringBuilder();
		   int action = event.getAction();
		   int actionCode = action & MotionEvent.ACTION_MASK;
		   sb.append("event ACTION_" ).append(names[actionCode]);
		   if (actionCode == MotionEvent.ACTION_POINTER_DOWN
		         || actionCode == MotionEvent.ACTION_POINTER_UP) {
		      sb.append("(pid " ).append(
		      action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
		      sb.append(")" );
		   }
		   sb.append("[" );
		   for (int i = 0; i < event.getPointerCount(); i++) {
		      sb.append("#" ).append(i);
		      sb.append("(pid " ).append(event.getPointerId(i));
		      sb.append(")=" ).append((int) event.getX(i));
		      sb.append("," ).append((int) event.getY(i));
		      if (i + 1 < event.getPointerCount())
		         sb.append(";" );
		   }
		   sb.append("]" );
		   Log.d(TAG, sb.toString());
		}
}