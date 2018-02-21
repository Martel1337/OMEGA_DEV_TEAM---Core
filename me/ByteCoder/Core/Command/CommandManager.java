package me.ByteCoder.Core.Command;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import jline.console.ConsoleReader;
import jline.console.completer.StringsCompleter;
import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Commands.CCore;
import me.ByteCoder.Core.Command.Commands.CModule;
import me.ByteCoder.Core.Command.Commands.Ending;
import me.ByteCoder.Core.Command.Commands.Help;
import me.ByteCoder.Core.Command.Commands.Restart;
import me.ByteCoder.Core.Command.Commands.Status;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class CommandManager {

	private ConsoleReader reader;
	private HashMap<String, Command> command;
	private CommandSender sender = new CommandSender();
	public boolean shouldRun = true;
	private boolean loaded;

	
	public CommandManager() {
		try {
			System.setProperty(jline.TerminalFactory.JLINE_TERMINAL, jline.UnsupportedTerminal.class.getName());
			command = new HashMap<String, Command>();
			sender = new CommandSender();
			reader = new jline.console.ConsoleReader();
			reader.setExpandEvents(true);
			reader.setBellEnabled(true);
			reader.setPrompt("\n>");
			this.loaded = true;
		} catch (IOException e) {
			Logger.println(e.getMessage(), LoggType.ERROR);
		}
		Main.loadedManagers++;
	}

	public void loadCommand(Command c) {
		command.put(c.getCommand(), c);
	}

	public void unloadCommand(Command c){
		command.remove(c.getCommand());
	}
	
	public ConsoleReader getReader(){
		return this.reader;
	}
	
	public void performCommand(String in, String[] args) {
		if(!shouldRun) return;
		boolean ran = false;
		for (Command c : command.values()) {
			if(c.equals(command.get(args[0]))){
				c.exec(sender, in, args);
				ran = true;
			}
		}
		if(!ran){
			Logger.println("Unknown command. Type 'help' for help.", LoggType.INFO);
		}
	}
	
	public Collection<Command> getCommands() {
		return command.values();
	}
	
	public Set<String> getCommandsString(){
		return this.command.keySet();
	}
	
	public Command getCommand(String s){
		return this.command.get(s);
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	public void loadCommands(){
		loadCommand(new CModule());
		loadCommand(new Ending());
		loadCommand(new Help());
		loadCommand(new Status());
		loadCommand(new Restart());
		loadCommand(new CCore());
	}
}
