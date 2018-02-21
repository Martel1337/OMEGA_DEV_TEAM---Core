package me.ByteCoder.Core.Utils.EventUtils.Events;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Client.JavaClient;
import me.ByteCoder.Core.Utils.EventUtils.Event;

public class JavaClientFileReceiveEvent extends Event {

private JavaClient sc;
private DataInputStream in;
private FileOutputStream fos;

public JavaClientFileReceiveEvent(JavaClient client, DataInputStream  input){
	this.sc = client;
	this.in = input;
}

public JavaClient getJavaClient(){
	return this.sc;
}

public DataInputStream getInput(){
	return this.in;
}

public void save(String name, String format) {
	 try {
		this.fos = new FileOutputStream(name + "." + format);
	 
	 byte[] buffer = new byte[4096];
	 
	 int filesize = 15123; // Send file size in separate msg
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while((read = this.in.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
}

@Override
public Event run() {
    Main.getEventManager().callEvent(this);
    return this;
}
}
