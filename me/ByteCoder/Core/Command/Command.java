package me.ByteCoder.Core.Command;

public abstract class Command {
	
	public Command(){
	}
	
	public abstract void exec(CommandSender sender, String in, String[] args);
	
	public abstract String getCommand();

	public abstract String getDescription();
	
}
