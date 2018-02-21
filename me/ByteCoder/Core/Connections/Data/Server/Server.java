package me.ByteCoder.Core.Connections.Data.Server;

import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;

import java.util.ArrayList;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.SocketClient;
import me.ByteCoder.Core.Connections.Data.Player.Player;

public class Server {

private String n;
private ServerData sd;
private SocketClient sc;
private ArrayList<Player> players;

	public Server(String name, SocketClient sclient){
		this.n = name;
		this.sc = sclient;
		this.players = new ArrayList<Player>();
		Main.getServerManager().addServer(this.n, this);
	}
	
	public String getName(){
		return this.n;
	}
	
	public ServerData getServerData(){
		return this.sd;
	}
	
	public void setServerData(String data){
		this.sd = new ServerData(data);
	}
	
	public SocketClient getSocketClient(){
		return this.sc;
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	public void sendMessage(DataType type, DataAction action, String data, String target){
		this.sc.write(type.toString(), action.toString(), data, target);
	}
}
