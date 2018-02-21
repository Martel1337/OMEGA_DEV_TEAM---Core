package me.ByteCoder.Core.Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Utils.EventUtils.Events.HandshakeMessageReceiveEvent;

public class DataHandshake {

private Socket s;
private DataInputStream in;
private DataOutputStream out;

public DataHandshake(Socket socket){
	this.s = socket;
	try {
		this.in = new DataInputStream(socket.getInputStream());
		this.out = new DataOutputStream(socket.getOutputStream());
	} catch (IOException e) {
		e.printStackTrace();
	}
	readPacket(this.in);
}

public Socket getSocket(){
	return this.s;
}

public void readPacket(DataInputStream input){
    try {
		String type = this.in.readUTF();
	    String action = this.in.readUTF();
	    String data = this.in.readUTF();
	    String target = this.in.readUTF();
	    
	    new HandshakeMessageReceiveEvent(this.s, type, action, data, target, this.in, this.out).run();  
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public void writeAndFlush(String obj){
	try {
		this.out.writeUTF(obj);
		this.out.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
