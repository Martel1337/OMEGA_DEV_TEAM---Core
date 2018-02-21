package me.ByteCoder.Core.Connections.Data.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class JavaClientFileHandshake {

private Socket s;
private DataInputStream in;
private String fileName;
private String fileFormat;

public JavaClientFileHandshake(Socket socket, String n) {
	try {
		this.s = socket;
		this.in = new DataInputStream(this.s.getInputStream());
		new DataOutputStream(this.s.getOutputStream());
		this.fileName = this.in.readUTF();
		this.fileFormat = this.in.readUTF();
	} catch (IOException e) {
		if(Main.getDebug() == true) {
			Logger.println("While starting JavaClient handshake: " + n + ". Error: " + e.getMessage(), LoggType.ERROR);
		}
	}
	saveFile(this.fileName, this.fileFormat);
}

public void saveFile(String f, String format) { 
try {
	File dir = new File("Data" + File.separatorChar + this.s.getInetAddress().getHostAddress().toString() + File.separatorChar);
	dir.mkdirs();
	FileOutputStream fos = new FileOutputStream(dir.toPath().toString() + File.separatorChar + f);
	 
 byte[] buffer = new byte[16*1024];
 
 	int filesize = 99999999;
	int read = 0;
	int remaining = filesize;
	while((read = this.in.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
		remaining -= read;
		fos.write(buffer, 0, read);
	}
	fos.close();
	this.in.close();
	this.s.close();
	} catch (FileNotFoundException e) {
		if(Main.getDebug() == true) {
			Logger.println(e.getMessage(), LoggType.ERROR);
		}
	} catch (IOException e) {
		if(Main.getDebug() == true) {
			Logger.println(e.getMessage(), LoggType.ERROR);
		}
	}
}
}
