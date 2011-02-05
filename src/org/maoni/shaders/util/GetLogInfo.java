package org.maoni.shaders.util;

import static android.opengl.GLES20.*;

public enum GetLogInfo {
	INSTANCE,
	;
	
	public boolean printLogInfo(final String ObjectType, final int obj){
        final String errorLog = glGetProgramInfoLog(obj);
        
        if (errorLog.length() > 1) {
            // We have some info we need to output.
            System.out.println("Info log for " + ObjectType + " : \n " + errorLog);
            return true;
        } else {
        	return false;
        }
	}

}
