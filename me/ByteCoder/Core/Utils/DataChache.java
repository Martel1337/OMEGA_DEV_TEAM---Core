package me.ByteCoder.Core.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import me.ByteCoder.Core.Connections.SocketClient;

public class DataChache {

	public static ArrayList<SocketClient> sclients;
	public static HashMap<String, SocketClient> sclient;
	
	public static void loadDataChache(){
		sclients = new ArrayList<SocketClient>();
		sclient = new HashMap<String, SocketClient>();
	}
}
