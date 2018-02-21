package me.ByteCoder.Core.Connections.Data.Player;

import java.util.List;

public class PlayerData {

private String[] d;
private String name;
private String protcol;
private String ip;
private String group;
private String UUID;
private String Guild;
private int priority;
private int TimePlayed;
private int exp;
private double booster;
private int credits;
private int coins;
private List<String> friends;

public PlayerData(String data){
	this.d = data.split(";");
	this.name = this.d[0];
	this.UUID = this.d[1];
	this.ip = this.d[2];
	this.group = this.d[3];
	this.priority = Integer.valueOf(this.d[4]);
	this.Guild = this.d[5];
	this.exp = Integer.valueOf(this.d[6]);
	this.booster = Double.valueOf(this.d[7]);
	this.credits = Integer.valueOf(this.d[8]);
	this.coins = Integer.valueOf(this.d[9]);
}

public String[] getData(){
	return this.d;
}

public String getName(){
	return this.name;
}

public List<String> getFriends(){
	return this.friends;
}

public String getIP(){
	return this.ip;
}

public String getProtocol(){
	return this.protcol;
}

public String getGroup(){
	return this.group;
}

public void setGroup(String g){
	this.group = g;
}

public String getUUID(){
	return this.UUID;
}

public String getGuild(){
	return this.Guild;
}

public void setGuild(String g){
	this.Guild = g;
}

public int getPriority(){
	return this.priority;
}

public void setPriority(Integer p){
	this.priority = p;
}

public int getTimePlayed(){
	return this.TimePlayed;
}

public void setTimePlayed(Integer tp){
	this.TimePlayed = tp;
}

public int getExperience(){
	return this.exp;
}

public void setExperience(Integer e){
	this.exp = e;
}

public double getBooster(){
	return this.booster;
}

public void setBooster(double b){
	this.booster = b;
}

public Integer getCredits(){
	return this.credits;
}

public void setCredits(Integer c){
	this.credits = c;
}

public Integer getCoins(){
	return this.coins;
}

public void setCoins(Integer c){
	this.coins = c;
}

public void setProtocol(String a) {
	this.protcol = a;
}

public String toData(){
	return this.name + ";" + this.UUID + ";" + this.ip + ";" + this.group + ";" + this.priority + ";" + this.Guild + ";" + this.exp + ";" + this.booster + ";" + this.credits + ";" + this.coins;
}

public static PlayerData valueOf(String data){
	return new PlayerData(data);
}
}

