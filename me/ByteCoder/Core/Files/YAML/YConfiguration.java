package me.ByteCoder.Core.Files.YAML;

import java.io.File;
import java.io.IOException;

import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class YConfiguration {

private File f;
private Configuration config;
private YamlConfiguration yaml;

public YConfiguration(File file) {
	this.f = file;
	this.yaml = (YamlConfiguration)ConfigurationProvider.getProvider(YamlConfiguration.class);
    if (this.f.exists()) {
        try {
            this.config = this.yaml.load(this.f);
        }
        catch (IOException e) {
            Logger.println("While loading config. Error: " + e.getMessage() , LoggType.ERROR);
        }
    }else {
        try {
        	this.f.createNewFile();
            this.config = this.yaml.load(this.f);
        }
        catch (IOException ex) {
            Logger.println("While create and loading config. Error: " + ex.getMessage() , LoggType.ERROR);
        }
    }
}

public Configuration getConfiguration() {
	return this.config;
}

public void unload() {
	
}

public void save() {
	try {
		this.yaml.save(this.config, this.f);
	} catch (IOException e) {
        Logger.println("While saving config. Error: " + e.getMessage() , LoggType.ERROR);
	}
}

public File getFile() {
	return this.f;
}
}
