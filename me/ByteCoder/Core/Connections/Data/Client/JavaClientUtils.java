package me.ByteCoder.Core.Connections.Data.Client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class JavaClientUtils {

public static void sendFile(JavaClient c, String directory, File f) {
	c.write("FILE", "UPLOAD", f.getName(), directory);
		try {
			DataOutputStream dos = c.getOutput();
		    FileInputStream fis = new FileInputStream(f);
		    byte[] buffer = new byte[16 * 1024];
	
		    c.setLocked(true);
		    dos.writeLong(f.length());
		    dos.flush();
		    while ((fis.read(buffer) > 0)) {
		        dos.write(buffer);
		    }
		    dos.flush();
		    
		    c.setLocked(false);
		    fis.close(); 
			} catch (IOException e) {
				if(Main.getDebug() == true) {
					Logger.println(e.getMessage(), LoggType.ERROR);
				}
			} 
}
}
