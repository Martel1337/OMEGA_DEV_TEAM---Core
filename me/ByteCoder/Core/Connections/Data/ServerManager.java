package me.ByteCoder.Core.Connections.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Server.Server;
import me.ByteCoder.Core.Connections.Data.Server.ServerGroup;
import me.ByteCoder.Core.Connections.SocketManager.ServerType;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class ServerManager {

private HashMap<String, Server> server;
private HashMap<String, ServerGroup> groups;

public ServerManager(){
	this.server = new HashMap<String, Server>();
	this.groups = new HashMap<String, ServerGroup>();
	Main.loadedManagers++;
	Logger.println("Server manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
}

public Collection<Server> getServers(){
	return this.server.values();
}

public Collection<Server> getProxys(){
	Collection<Server> proxys = new ArrayList<Server>();
	for(Server srv : this.server.values()) {
		if(srv.getSocketClient().getServerType().equals(ServerType.Proxy)) {
			proxys.add(srv);
		}
	}
	return proxys;
}

public Collection<ServerGroup> getServerGroups(){
	return this.groups.values();
}
public ServerGroup getServerGroup(String name){
	return this.groups.get(name);
}

public Server getServer(String name){
	return this.server.get(name);
}

public void addServerGroup(String name, ServerGroup group){
	this.groups.put(name, group);
}

public void removeServerGroup(String name){
	this.groups.remove(name);
}

public void addServer(String name, Server server){
	this.server.put(name, server);
}

public void removeServer(String name){
	this.server.remove(name);
}
}
