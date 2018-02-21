package me.ByteCoder.Core.Connections.Data;

import java.util.ArrayList;
import java.util.HashMap;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Player.Player;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class PlayerManager {

private HashMap<String, Player> player;
	
public PlayerManager(){
	this.player = new HashMap<String, Player>();
	Main.loadedManagers++;
	Logger.println("Player manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
}

public ArrayList<Player> getOnlinePlayers(){
	ArrayList<Player> players = new ArrayList<Player>();
	for(Player p : this.player.values()){
		if(p.isOnline()){
			players.add(p);
		}
	}
	return players;
}

public ArrayList<Player> getOfflinePlayers(){
	ArrayList<Player> players = new ArrayList<Player>();
	for(Player p : this.player.values()){
		if(!p.isOnline()){
			players.add(p);
		}
	}
	return players;
}

public Player getPlayer(String name){
	return this.player.get(name);
}

public void addPlayer(String name, Player player){
	if(!this.player.containsKey(name)){
		this.player.put(name, player);
	}
}

public Player createPlayer(String name, String data) {
	return new Player(name, data);
}

public void removePlayer(String name){
	if(this.player.containsKey(name)) {
		this.player.remove(name);
	}
}
}
