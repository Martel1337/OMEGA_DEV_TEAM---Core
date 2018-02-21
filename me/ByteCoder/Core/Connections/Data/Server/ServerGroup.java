package me.ByteCoder.Core.Connections.Data.Server;

import java.util.ArrayList;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.SocketClient;

public class ServerGroup {

private String n;
private ArrayList<Server> servers;
private SocketClient c;

public ServerGroup(String name, SocketClient client){
	this.n = name;
	this.servers = new ArrayList<Server>();
	this.c = client;
	Main.getServerManager().addServerGroup(this.n, this);
}

public String getName(){
	return this.n;
}

public SocketClient getSocketClient(){
	return this.c;
}

public ArrayList<Server> getServers(){
	return this.servers;
}

public void addServer(Server server){
	this.servers.add(server);
}

public void removeServer(Server server){
	this.servers.remove(server);
}

public void remove(){
	Main.getServerManager().removeServerGroup(this.n);
}
}
