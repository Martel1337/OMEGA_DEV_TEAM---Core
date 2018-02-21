package me.ByteCoder.Core.Utils.EventUtils.Events;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Connections.SocketClient;
import me.ByteCoder.Core.Utils.EventUtils.Event;

public class SocketMessageReceiveEvent extends Event {

private String MessageAction;
private String MessageType;
private String Target;
private String data;
private SocketClient sc;
private DataOutputStream out;
private DataInputStream in;

public SocketMessageReceiveEvent(SocketClient sclient, String MessageType, String MessageAction, String m, String t, DataInputStream input, DataOutputStream output){
	this.sc = sclient;
	this.MessageAction = MessageAction;
	this.MessageType = MessageType;
	this.data = m;
	this.Target = t;
	this.out = output;
	this.in = input;
}

public SocketClient getSocketClient(){
	return this.sc;
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
