package com.neo.service.convert.dcc;

import java.lang.reflect.Field;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class ProcessManager {

    static interface Kernel32 extends Library {

    public static Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

    public int GetProcessId(Long hProcess);
    }
    
    public static int getPid(Process p) {
    Field f;

    if (iSwindows()) {
        try {
            f = p.getClass().getDeclaredField("handle");
            f.setAccessible(true);
            int pid = Kernel32.INSTANCE.GetProcessId((Long) f.get(p));
            return pid;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } else {
        try {
            f = p.getClass().getDeclaredField("pid");
            f.setAccessible(true);
            int pid = Integer.valueOf(f.get(p).toString());
            return pid;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    return 0;
    }
    
    public static boolean iSwindows() {
    	String os = System.getenv("os");
    	if(os!=null&&os.toLowerCase().contains("win")) {
    		return true;
    	}
    	return false;
    }
    
    public static int killProcess(Integer pid) throws Exception {
    	String cmd = "";
    	if(pid==0) {
    		pid=null;
    	}
    	if(iSwindows()) {
    		cmd = "taskkill -f -pid "+pid;
    	}
    	else {
    		cmd = "kill -9 "+pid;
    	}
		Runtime rt =Runtime.getRuntime();
		System.out.println("killProcess :"+cmd);
		Process proc = rt.exec(cmd);
		return proc.waitFor();
    }
    
}
