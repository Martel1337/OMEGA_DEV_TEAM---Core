package me.ByteCoder.Core.Data;


public class Attributes {

private String[] d;
private String s;	

public Attributes(String data, String spliter) {
	this.s = spliter;
	this.d = data.split(spliter);
}

public Attributes(int length, String spliter) {
	this.s = spliter;
	for(int i = length; i > 1; i--){
		this.d[i] = "null";
    }
}

public String getData() {
	StringBuilder strList = new StringBuilder();

    for (String str : this.d) {
        if (this.d.length > 0) {
        	strList.append(this.s);
        }
        strList.append(str);
    }

    return strList.toString();
}

public void setData(String data) {
	this.d = data.split(this.s);
}

public void setField(int id, String data) {
	if(!data.contains(this.s)) {
		this.d[id] = data;
	}
}

public void setField(int id, Integer data) {
	this.d[id] = String.valueOf(data);
}

public void setField(int id, boolean data) {
	this.d[id] = String.valueOf(data);
}

public void setField(int id, double data) {
	this.d[id] = String.valueOf(data);
}

public boolean contains(String c) {
	return this.d.toString().contains(c);
}

public String getField(int id) {
	return this.d[id];
}

public int getLength() {
	return this.d.length;
}

}
