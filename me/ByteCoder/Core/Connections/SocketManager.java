package me.ByteCoder.Core.Connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Client.JavaClient;
import me.ByteCoder.Core.Connections.Data.Client.JavaClientData;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class SocketManager {
	
    private HashMap<String, SocketClient> connectedSockets;
    private HashMap<String, JavaClient> connectedJavaClients;
    private ArrayList<String> bannedSockets;
    private ArrayList<SocketClient> connectedSocket;
    private ServerSocket serverSocket;
    public HashMap<String, JavaClientData> tempJData;
    private boolean enable;
    
	public SocketManager(){
    	bannedSockets = new ArrayList<String>();
    	connectedSockets = new HashMap<String, SocketClient>();
    	connectedJavaClients = new HashMap<String, JavaClient>();
    	connectedSocket = new ArrayList<SocketClient>();
    	tempJData = new HashMap<String, JavaClientData>();
    	Main.loadedManagers++;
    	Logger.println("Socket manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
    }

	public void init(int port){
    	try {
			this.serverSocket = new ServerSocket(port);
			this.enable = true;
			Logger.println("Socket server was started!", LoggType.INFO);
		} catch (IOException e) {
			Logger.println(e.getMessage(), LoggType.ERROR);
		}
    	
    	ExecutorService service = Executors.newCachedThreadPool();
    	service.submit(() -> {
            while (!this.serverSocket.isClosed()) {
                try {
                	Socket socket = this.serverSocket.accept();
                	if(!this.bannedSockets.contains(socket.getInetAddress().getHostAddress().toString())) {
                		new SocketsHandshake(socket, service);	
                	}else{
                		socket.close();
                		if(Main.getNotify() == true) {
                			Logger.println("Socket: " + socket.getInetAddress().getHostAddress().toString() + " tried to trouble Connect. Disconnected.", LoggType.WARNING);
                		}
                	}
                }catch (IOException e) {
                	Logger.println(e.getMessage(), LoggType.ERROR);
                }
            }
        	end("BLYADSKAYA ERROR!11!1!");
        	});
    }
    
    public void end(String cause) {
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
                Logger.println("Socket server was stopped! Reason: " + cause, LoggType.INFO);
            }
        }
        catch (IOException e) {
        	if(Main.getDebug() == true) {
        		Logger.println(e.getMessage(), LoggType.ERROR);
        	}
        }
    }
    
    public ArrayList<String> getBannedSockets(){
    	return this.bannedSockets;
    }
    
    public void setAccess(boolean b) {
    	this.enable = b;
    }
    
    public boolean getAccess() {
    	return this.enable;
    }
    
    public ServerSocket getSocketServer(){
    	return this.serverSocket;
    }
    
    public Collection<SocketClient> getClients(){
    	return this.connectedSockets.values();
    }
    
    public SocketClient getClient(String name){
    	return this.connectedSockets.get(name);
    }
    
    public void addClient(String name, SocketClient client){
    	this.connectedSocket.add(client);
    	this.connectedSockets.put(name, client);
    }
    
    public void removeClient(String name){
    	this.connectedSocket.remove(getClient(name));
    	this.connectedSockets.remove(name);
    }
    
    public Collection<JavaClient> getJavaClients(){
    	return this.connectedJavaClients.values();
    }
    
    public JavaClient getJavaClient(String n) {
    	return this.connectedJavaClients.get(n);
    }
    
    public void addJavaClient(String n, JavaClient c) {
    	if(!this.connectedJavaClients.containsKey(n)) {
    		this.connectedJavaClients.put(n, c);
    	}
    }
    
    public void removeJavaClient(String n) {
    	this.connectedJavaClients.remove(n);
    }
    
    public ServerType converToType(String s) {
    	ServerType localt;
    	ArrayList<String> list = new ArrayList<String>();
    	
    	for(ServerType str : ServerType.values()) {
    		list.add(str.toString());
    	}
    	
    	if(list.contains(s)) {
    		localt = ServerType.valueOf(s);
    	}else {
    		localt = null;
    	}
    	
    	return localt;
    }
    
    public static enum ServerType{
    	Proxy, Server, GroupManager, JavaClient, JavaClientFileHandshake, Handshake;
    }
}

