package me.ByteCoder.Core.Data.BCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BField {

private String name;
private Map<String, Object> subData;	

public BField(String n) {
	this.name = n;
	this.subData = new HashMap<String, Object>();
}

public String getFieldName() {
	return this.name;
}

public void setFieldName(String n) {
	this.name = n;
}

public Object getData(String field) {
	return this.subData.get(field);
}

public void setData(String field, Object o) {
	this.subData.put(field, o);
}

public void removeData(String field) {
	if(this.subData.containsKey(field)) {
		this.subData.remove(field);
	}
}

public Set<String> getKeys(){
	return this.subData.keySet();
}

public Collection<Object> getValues(){
	return this.subData.values();
}
}
