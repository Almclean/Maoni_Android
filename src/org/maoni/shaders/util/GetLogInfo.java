package org.maoni.shaders.util;

import static android.opengl.GLES20.*;
import android.util.Log;

public enum GetLogInfo {
	INSTANCE,
	;
	
	public boolean printLogInfo(final String ObjectType, final int obj){
        final String errorLog = glGetProgramInfoLog(obj);
        
        if (errorLog.length() > 1) {
            // We have some info we need to output.
            Log.e("ShaderInfo", "Info log for " + ObjectType + " : \n " + errorLog);
            return true;
        } else {
        	Log.i("ShaderInfo", "Loaded " + ObjectType + " successfully");
        	return false;
        }
	}

}
