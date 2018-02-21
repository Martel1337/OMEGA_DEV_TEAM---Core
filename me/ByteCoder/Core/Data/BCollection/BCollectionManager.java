package me.ByteCoder.Core.Data.BCollection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class BCollectionManager {

private Map<String, BCollection> collections;
	
public BCollectionManager() {
	this.collections = new HashMap<String, BCollection>();
	Main.loadedManagers++;
	Logger.println("Collection manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
}

public Collection<BCollection> getCollections(){
	return this.collections.values();
}

public Set<String> getKeys(){
	return this.collections.keySet();
}

public void add(String name, BCollection collection) {
	if(!this.collections.containsKey(name)) {
		this.collections.put(name, collection);
	}
}

public void remove(String name) {
	if(this.collections.containsKey(name)) {
		this.collections.remove(name);
	}
}

public BCollection get(String name) {
	return this.collections.get(name);
}
}
