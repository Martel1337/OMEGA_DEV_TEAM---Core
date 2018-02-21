package me.ByteCoder.Core.Files;

import java.util.Collection;
import java.util.HashMap;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Files.JSON.JConfiguration;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class FilesManager {

public HashMap<String, JConfiguration> JSONFiles;
	
public FilesManager(){
	this.JSONFiles = new HashMap<String, JConfiguration>();
	Main.loadedManagers++;
	Logger.println("Files manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
}

public Collection<JConfiguration> getJSONConfigurations(){
	return this.JSONFiles.values();
}

public JConfiguration getJSONConfiguration(String n){
	return this.JSONFiles.get(n);
}
}