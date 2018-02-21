package me.ByteCoder.Core.Utils.EventUtils.Events;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Client.JavaClient;
import me.ByteCoder.Core.Utils.EventUtils.Event;

public class JavaClientMessageReceiveEvent extends Event {

private String MessageAction;
private String MessageType;
private String Target;
private String data;
private JavaClient sc;
private DataOutputStream out;
private DataInputStream in;

public JavaClientMessageReceiveEvent(JavaClient client, String MessageType, String MessageAction, String m, String t, DataInputStream input, DataOutputStream output){
	this.sc = client;
	this.MessageAction = MessageAction;
	this.MessageType = MessageType;
	this.data = m;
	this.Target = t;
	this.out = output;
	this.in = input;
}

public JavaClient getJavaClient(){
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
