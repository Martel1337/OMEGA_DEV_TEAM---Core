package me.ByteCoder.Core.Data.BCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.SystemUtils;

public class BCollection {

public static enum BCollectionType{YAML, JSON;}

private BCollectionType type;
private String name;
private Integer size;
private Map<String, BField> fields;

public BCollection(String n, int sz, BCollectionType t) {
	this.type = t;
	this.name = n;
	this.size = sz;
	this.fields = new HashMap<String, BField>();
	
	Main.getCollectionManager().add(this.name, this);
}

public String getName() {
	return this.name;
}

public BCollectionType getType() {
	return this.type;
}

public Collection<BField> getValues() {
	return this.fields.values();
}

public void clear() {
	if(!this.fields.isEmpty()) {
		this.fields.clear();
	}
}

public BField get(String n) {
	return this.fields.get(n);
}

public void add(BField field) {
	if(!this.fields.containsKey(field.getFieldName()) && this.fields.size() < this.size) {
		this.fields.put(field.getFieldName(), field);
	}
}

public void remove(String n) {
	if(this.fields.containsKey(n) && this.fields.size() > 0) {
		this.fields.remove(n);
	}
}

public void destroyMassive() {
	Main.getCollectionManager().remove(this.name);
	SystemUtils.optimizeCore(false);
}
}
