package me.ByteCoder.Core.Connections.Data.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Server.Server;
import me.ByteCoder.Core.Connections.Packets.Packet;
import me.ByteCoder.Core.Connections.Packets.Player.PacketPlayerKickBukkit;
import me.ByteCoder.Core.Connections.Packets.Player.PacketPlayerSendMessage;
import me.ByteCoder.Core.Data.Attributes;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.PlayerUtils;

public class Player {

private String n;
private Server s;
private boolean online;
private boolean dloaded;
private String ip;
private String uuid;
private PlayerData data;
private HashMap<String, Attributes> cdata;

public Player(String name, String data){
	this.n = name;
	this.cdata = new HashMap<String, Attributes>();
	this.dloaded = false;
	this.newPlayer(data);
	Main.getPlayerManager().addPlayer(this.n, this);
}

public boolean hasSqlProfile(){
	try
    {
      ResultSet rs = Main.getMySQLManager().getResult("SELECT * FROM PlayersData WHERE Name= '" + this.n + "'");
      if (rs.next()) {
        return rs.getString("Name") != null;
      }
      return false;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
}

public boolean isLoadedData(){
	return this.dloaded;
}

private void newPlayer(String tempdata){
	this.uuid = tempdata.split(";")[0];
	this.ip = tempdata.split(";")[1];
	if(!this.hasSqlProfile()){
		PlayerUtils.insterDataToSql(this);
		this.data = PlayerData.valueOf(this.n + ";" + this.uuid + ";" + this.ip + ";Default;1;null;0;1.0;0;0");
		this.dloaded = true;
	}else{
		loadData();
	}
}

public String getName(){
	return this.n;
}

public String getUUID(){
	return this.uuid;
}

public String getIP(){
	return this.ip;
}

public Server getServer(){
	return this.s;
}

public boolean isOnline(){
	return this.online;
}

public PlayerData getData(){
	return this.data;
}

public void loadData(){
	if(this.isLoadedData()) {
		ResultSet res = Main.getMySQLManager().getResult("SELECT * FROM PlayersData WHERE Name= '" + this.n + "'");
			try {
				if(res.next()){
					this.data = PlayerData.valueOf(res.getString("Data"));
				}
				this.dloaded = true;
			} catch (SQLException e) {
				e.printStackTrace();
				this.dloaded = false;
			}
		if(Main.getDebug() == true) {
			Logger.println("Data of player " + this.n + " has been loaded. Data: " + this.data.toData(), LoggType.INFO);
		}
	}
}

public void saveData(){
	Main.getMySQLManager().SendResult("UPDATE PlayersData SET Data='" + this.data.toData() + "' WHERE Name= '" + this.n + "'");
}

public void setOnline(boolean bol){
	this.online = bol;
}

public void setServer(Server server){
	this.s = server;
}

public void setAttributes(String name, Attributes data) {
	this.cdata.put(name, data);
}

public Attributes getAttributes(String dname) {
	return this.cdata.get(dname);
}

public void kickPlayer(String kickMess){
	Packet packet = new PacketPlayerKickBukkit(this, kickMess);
	if(this.online){
		packet.execute();
	}
}

public void sendMessage(String mess){
	Packet packet = new PacketPlayerSendMessage(this, mess);
	if(this.online){
		packet.execute();
	}
}

public void remove(){
	Main.getPlayerManager().removePlayer(this.n);
}
}
