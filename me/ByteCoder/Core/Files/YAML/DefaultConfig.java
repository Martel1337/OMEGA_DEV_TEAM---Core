package me.ByteCoder.Core.Files.YAML;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.StringUtils;

public class DefaultConfig {

private static File configFile;
public static Configuration config;
public static YamlConfiguration yaml;

public static void loadConfig() {
    configFile = new File("config.yml");
    yaml = (YamlConfiguration)ConfigurationProvider.getProvider(YamlConfiguration.class);
    if (configFile.exists()) {
        try {
            config = yaml.load(configFile);
        }
        catch (IOException e) {
            Logger.println("While loading config. Error: " + e.getMessage() , LoggType.ERROR);
        }
    }else {
        try {
        	configFile.createNewFile();
            config = yaml.load(configFile);
        	writeDefault();
        }
        catch (IOException ex) {
            Logger.println("While create and loading config. Error: " + ex.getMessage() , LoggType.ERROR);
        }
    }
}

public static void saveConfig() {
	try {
		yaml.save(config, configFile);
	} catch (IOException e) {
        Logger.println("While saving config. Error: " + e.getMessage() , LoggType.ERROR);
	}
}

public static void writeDefault() {
	config.set("MySQL.Enable", true);
	config.set("MySQL.User", "root");
	config.set("MySQL.Password", "");
	config.set("MySQL.Host", "localhost");
	config.set("MySQL.Base", "base");
	config.set("Core.Port", 8888);
	config.set("Core.Password", StringUtils.getSaltString(12));
	config.set("Core.Prefix", "[Core]");
	config.set("Core.BanList", Arrays.asList("127.0.0.1", "8.8.8.8"));
}

public static void loadDefaultBanList() {
	for(String s : config.getStringList("Core.BanList")) {
		Main.getSocketManager().getBannedSockets().add(s);
	}
}
}
