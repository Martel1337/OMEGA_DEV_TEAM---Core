package me.ByteCoder.Core.Connections.Data.Server;

public class ServerData {
	
private String data;

public ServerData(String srvdata){
	this.data = srvdata;
}

public String getName() {
	return this.data.split(";")[0];
}

public Integer getOnline(){
	return Integer.valueOf(this.data.split(";")[1]);
}

public Integer getSlots(){
	return Integer.valueOf(this.data.split(";")[2]);
}

public String getState(){
	return this.data.split(";")[3];
}

public String getMap(){
	return this.data.split(";")[4];
}

public String getGroup(){
	return this.data.split(";")[5];
}

public String getType(){
	return this.data.split(";")[6];
}

public Integer getID(){
	return Integer.valueOf(this.data.split(";")[7]);
}

public String getIP(){
	return this.data.split(";")[8];
}

public Integer getPort(){
	return Integer.valueOf(this.data.split(";")[9]);
}

public double getTPS() {
	return Double.valueOf(this.data.split(";")[10]);
}

public int getUptime() {
	return Integer.valueOf(this.data.split(";")[11]);
}

public String toString(){
	return this.data;
}
}