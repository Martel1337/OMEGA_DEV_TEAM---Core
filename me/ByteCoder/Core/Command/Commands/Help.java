package me.ByteCoder.Core.Command.Commands;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Command.CommandSender;

public class Help extends Command {

	@Override
	public void exec(CommandSender sender, String in, String[] args) {
		if(args.length == 1){
			sender.sendMessage("");
			sender.sendMessage(Main.getPrefix() + " Help page");
			sender.sendMessage("Commands count: " + Main.getCommandManager().getCommands().size());
			sender.sendMessage(" # | Command name | Command descrition");
			sender.sendMessage("");
			int i = 0;
			for(Command c : Main.getCommandManager().getCommands()){
				i++;
				sender.sendMessage(" " + i + " | " + c.getCommand() + " | " + c.getDescription());
			}
			sender.sendMessage("");
		}
	}

	@Override
	public String getCommand() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Main core command";
	}

}
