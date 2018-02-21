package me.ByteCoder.Core.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Files.YAML.Configuration;
import me.ByteCoder.Core.Files.YAML.ConfigurationProvider;
import me.ByteCoder.Core.Files.YAML.YamlConfiguration;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.EventUtils.EventListener;

import java.io.File;

public class Module
{
    private ModuleDescription description;
    private File file;
    private File configFile;
    private Configuration config;
    private YamlConfiguration yaml;
    private HashMap<String, Command> commands = new HashMap<String, Command>();
    private HashSet<EventListener> events = new HashSet<EventListener>();
    
    public ModuleDescription getDescription() {
        return this.description;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public void onLoad() {
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    	Logger.println("Disabled module " + this.description.getName() + ".", LoggType.INFO);
    }
    
    public final File getDataFolder() {
        return new File("Modules/", this.getDescription().getName());
    }
    
    public final InputStream getResourceAsStream(final String name) {
        return this.getClass().getClassLoader().getResourceAsStream(name);
    }
    
    final void init(final ModuleDescription description) {
        this.description = description;
        this.file = description.getFile();
        this.loadConfig();
    }
    
    public Configuration getConfig() {
        return this.config;
    }
    
    public Collection<Command> getCommands(){
    	return this.commands.values();
    }
    
    public Collection<EventListener> getEvents(){
    	return this.events;
    }
    
    public void loadConfig() {
        this.configFile = new File(this.getDataFolder(), "config.yml");
        this.yaml = (YamlConfiguration)ConfigurationProvider.getProvider(YamlConfiguration.class);
        if (this.configFile.exists()) {
            try {
                this.config = this.yaml.load(this.configFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveConfig() {
        try {
            this.yaml.save(this.config, this.configFile);
        }
        catch (IOException ex) {
        	this.log(ex.getMessage(), LoggType.ERROR);
        }
    }
    
    public void saveDefaultConfig() {
        if (this.config != null) {
            return;
        }
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            if (!this.configFile.exists()) {
                final InputStream is = this.getResourceAsStream("config.yml");
                if (is == null) {
                    throw new IllegalStateException("config.yml not found in plugin jar file");
                }
                this.yaml.save(this.yaml.load(is), this.configFile);
                this.loadConfig();
                this.log("Config loaded!", LoggType.ERROR);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void registerEvents(EventListener e) {
    	Main.getEventManager().addEvent(e);
    	this.events.add(e);
    }
    
    public void unregisterEvents(EventListener e) {
    	Main.getEventManager().remEvent(e);
    	this.events.remove(e);
    }
    
    public void registerCommand(Command c) {
    	Main.getCommandManager().loadCommand(c);
    	this.commands.put(c.getCommand(), c);
    }
    
    public void unregisterCommand(Command c) {
    	Main.getCommandManager().unloadCommand(c);
    	this.commands.remove(c.getCommand());
    }
    
    public void log(String s, LoggType type){
    	Logger.println("[" + this.description.getName() + "] " + s, type);
    }
}
