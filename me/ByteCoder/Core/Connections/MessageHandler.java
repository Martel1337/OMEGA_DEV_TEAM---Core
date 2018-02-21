package me.ByteCoder.Core.Connections;

public class MessageHandler {

public enum DataType{
	SERVER, PLAYER, PROXY, GROUP;
}
	
public enum DataAction{
	SET, GET, GET_DATA, REGISTER, UNREGISTER, RECEIVE_DATA, SET_DATA, LOAD, UNLOAD, SAVE, UNSAVE, KICK, SENDMESSAGE, SEND, DO, SHUTDOWN, CONNECTED, DISCONNECTED;
}
}
