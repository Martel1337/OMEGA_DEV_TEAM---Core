package me.ByteCoder.Core.Connections.Data.Client;

import java.util.ArrayList;
import java.util.Collection;

import me.ByteCoder.Core.Utils.DateUtils;

public class JavaClientData {

private String[] d;
private String dt;
private String ip;
private Collection<String> list;
private String lastConn;
private String firstConn;
private String bssid;
private String macid;

public JavaClientData(String data) {
	this.dt = data;
	this.bssid = "null";
	this.macid = "null";
	this.d = data.split(";");
	this.list = new ArrayList<String>();
	this.lastConn = DateUtils.getCurrentTime();
	this.firstConn = DateUtils.getCurrentTime();
}

public String toData() {
	return this.dt;
}

public void setIP(String i) {
	this.ip = i;
}

public String getIP() {
	return this.ip;
}

public String getName() {
	return this.d[0];
}

public String getOSName() {
	return this.d[1];
}

public String getOSVersion() {
	return this.d[2];
}

public String getJavaVersion() {
	return this.d[3];
}

public String getBSSID() {
	return this.bssid;
}

public void setBSSID(String str) {
	this.bssid = str;
}

public String getMacID() {
	return this.macid;
}

public void setMacID(String str) {
	this.macid = str;
}

public String getConnectorVersion() {
	return this.d[4];
}

public String getUPTIME() {
	return this.d[5];
}

public String getLastConnection() {
	return this.lastConn;
}

public void setLastConnection(String s) {
	this.lastConn = s;
}

public void setFirstConnection(String s) {
	this.firstConn = s;
}

public String getFirstConnection() {
	return this.firstConn;
}

public void setUPTIMS(String s) {
	this.d[5] = s;
}

public void setData(String[] dat) {
	this.d = dat;
}

public Collection<String> getTaskList(){
	return this.list;
}

public void setTaskList(ArrayList<String> l) {
	this.list.clear();
	this.list.addAll(l);
}

public String[] getData() {
	return this.d;
}
}
