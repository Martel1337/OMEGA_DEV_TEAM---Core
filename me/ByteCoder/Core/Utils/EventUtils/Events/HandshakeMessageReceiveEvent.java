package me.ByteCoder.Core.Utils.EventUtils.Events;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Utils.EventUtils.Event;

public class HandshakeMessageReceiveEvent extends Event {

private String MessageAction;
private String MessageType;
private String Target;
private String data;
private Socket socket;
private DataInputStream in;
private DataOutputStream out;

public HandshakeMessageReceiveEvent(Socket s, String MessageType, String MessageAction, String m, String t, DataInputStream input, DataOutputStream output){
	this.socket = s;
	this.MessageAction = MessageAction;
	this.MessageType = MessageType;
	this.data = m;
	this.Target = t;
	this.out = output;
	this.in = input;
}

public Socket getSocket(){
	return this.socket;
}

public DataInputStream getInput(){
	return this.in;
}

public DataOutputStream getOutput(){
	return this.out;
}

public String getMessageType(){
	return this.MessageType;
}

public String getMessageAction(){
	return this.MessageAction;
}

public String getTarget(){
	return this.Target;
}

public String getData(){
	return this.data;
}

@Override
public Event run() {
    Main.getEventManager().callEvent(this);
    return this;
}
}
