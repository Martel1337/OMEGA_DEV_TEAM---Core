package me.ByteCoder.Core;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.ByteCoder.Core.Command.CommandListener;
import me.ByteCoder.Core.Command.CommandManager;
import me.ByteCoder.Core.Connections.SocketManager;
import me.ByteCoder.Core.Connections.Data.PlayerManager;
import me.ByteCoder.Core.Connections.Data.ServerManager;
import me.ByteCoder.Core.Connections.MySQL.MySQLManager;
import me.ByteCoder.Core.Data.BCollection.BCollectionManager;
import me.ByteCoder.Core.Files.FilesManager;
import me.ByteCoder.Core.Files.YAML.DefaultConfig;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Module.ModuleManager;
import me.ByteCoder.Core.Runnable.SchedulerManager;
import me.ByteCoder.Core.Utils.ServerUtils;
import me.ByteCoder.Core.Utils.StringUtils;
import me.ByteCoder.Core.Utils.EventUtils.EventManager;

public class Main {

	private static CommandManager cm;
	private static ServerManager sm;
	private static PlayerManager pm;
	private static SocketManager socketm;
	private static MySQLManager msqm;
	private static FilesManager fm;
	private static ModuleManager mm;
	private static EventManager em;
	private static SchedulerManager smm;
	private static BCollectionManager bcm;
	private static ExecutorService es;
	public static int totalManagers = 9;
	public static int loadedManagers;
	private static Integer port = 8888;
	private static String prefix = "[Core]";
	private static String ClientPass;
	private static boolean isSQL;
	private static String mysqlUser;
	private static String mysqlBase;
	private static String mysqlPassword;
	private static String mysqlHost;
	private static boolean Notify; 
	private static boolean Debug;
	
public static void main(String[] args) throws IOException {
	logoLoad();
	cm = new CommandManager();
	cm.getReader().clearScreen();
	Logger.createLoggFile();
	Logger.println("Loading core...", LoggType.INFO);
	Logger.println("", LoggType.INFO);
	DefaultConfig.loadConfig();
	loadDefaultSettings();
	sm = new ServerManager();
	fm = new FilesManager();
	msqm = new MySQLManager();
	socketm = new SocketManager();
	smm = new SchedulerManager();
	mm = new ModuleManager();
	em = new EventManager();
	bcm = new BCollectionManager();
	es = Executors.newCachedThreadPool();
	Logger.println("", LoggType.INFO);
	Logger.println("Information:", LoggType.INFO);
	Logger.println(" * Developer: ByteCoder_", LoggType.INFO);
	Logger.println(" * Version: A-1", LoggType.INFO);
	Logger.println(" * Core port: " + port, LoggType.INFO);
	Logger.println(" * Socket password: " + ClientPass, LoggType.INFO);
	Logger.println("", LoggType.INFO);
	Debug = false;
	Notify = true;
	socketm.init(port);
	if(isSQL) {
		msqm.connect(mysqlHost, mysqlBase, mysqlUser, mysqlPassword);
	}
	DefaultConfig.loadDefaultBanList();
	Logger.println("Loading modules...", LoggType.INFO);
	Logger.println("", LoggType.INFO);
	mm.detectModules(new File("Modules" + File.separator));
	mm.loadPlugins();
	mm.enablePlugins();
	cm.loadCommands();
	CommandListener.startListener();
}

private static void loadDefaultSettings() {
	ClientPass = DefaultConfig.config.getString("Core.Password");
	port = DefaultConfig.config.getInt("Core.Port");
	mysqlBase = DefaultConfig.config.getString("MySQL.Base");
	mysqlUser = DefaultConfig.config.getString("MySQL.User");
	mysqlPassword = DefaultConfig.config.getString("MySQL.Password");
	mysqlHost = DefaultConfig.config.getString("MySQL.Host");
	isSQL = DefaultConfig.config.getBoolean("MySQL.Enable");
}

	public static CommandManager getCommandManager(){
		return cm;
	}
	
	public static ServerManager getServerManager(){
		return sm;
	}
	
	public static PlayerManager getPlayerManager(){
		return pm;
	}
	
	public static SocketManager getSocketManager(){
		return socketm;
	}
	
	public static MySQLManager getMySQLManager(){
		return msqm;
	}
	
	public static FilesManager getFilesManager(){
		return fm;
	}
	
	public static ModuleManager getModuleManager(){
		return mm;
	}
	
	public static EventManager getEventManager() {
		return em;
	}
	
	public static SchedulerManager getSchedulerManager() {
		return smm;
	}
	
	public static BCollectionManager getCollectionManager() {
		return bcm;
	}
	
	public static ExecutorService getExecutor(){
		return es;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static String getSocketPassword() {
		return ClientPass;
	}
	
	public static Integer getSocketPort() {
		return port;
	}
	
	public static void setDebug(boolean bol) {
		Debug = bol;
	}
	
	public static boolean getDebug() {
		return Debug;
	}
	
	public static void setNotify(boolean bol) {
		Notify = bol;
	}
	
	public static boolean getNotify() {
		return Notify;
	}
	
	public static String getKey() {
		return "LGD7PtYlJ9ANf8cS";
	}
	
	public static void logoLoad() {
		System.out.println("(===================================================)");
	    System.out.println("|                                                   |");
	    System.out.println("|                                                   |");
	    System.out.println("|                  Core - " + "A-1" +  "                       |");
	    System.out.println("|                                                   |");
	    System.out.println("|            Remote user control system             |");
	    System.out.println("|                                                   |");
	    System.out.println("|      Developers: ByteCoder_, OmegaDev Team        |");
	    System.out.println("|                                                   |");
	    System.out.println("|                                                   |");
	    System.out.println("(===================================================)");
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
