package me.ByteCoder.Core.Command.Commands;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Command.CommandSender;
import me.ByteCoder.Core.Files.YAML.DefaultConfig;
import me.ByteCoder.Core.Utils.StringUtils;

public class CCore extends Command {

	@Override
	public void exec(CommandSender sender, String in, String[] args) {
		if(args.length == 1) {
			sender.sendMessage("");
			sender.sendMessage(Main.getPrefix() + " - Connection control");
			sender.sendMessage("");
			sender.sendMessage(" * Close socket server connection - core access (State)");
			sender.sendMessage(" * Ban socket ip - core ban (IP)");
			sender.sendMessage(" * Unban socket ip - core unban (IP)");
			sender.sendMessage(" * list of banned ips - core banlist");
			sender.sendMessage("");
		}else {
			if(args[1].equalsIgnoreCase("access")) {
				if(args.length == 2) {
					sender.sendMessage(Main.getPrefix() + " Close socket server connection - core access (State)");
				}else{
					if(args[2] != null) {
						if(StringUtils.isBoolean(args[2])) {
							Main.getSocketManager().setAccess(Boolean.valueOf(args[2]));
							sender.sendMessage(Main.getPrefix() + " connection: " + args[2]);
						}
					}
				}
			}else if(args[1].equalsIgnoreCase("ban")) {
				if(args.length == 2) {
					sender.sendMessage(Main.getPrefix() + " Ban socket ip - core ban (IP)");
				}else {
					if(args[2] != null) {
						Main.getSocketManager().getBannedSockets().add(args[2]);
						DefaultConfig.config.set("Core.BanList", Main.getSocketManager().getBannedSockets());
						sender.sendMessage(" IP: " + args[2] + " has been banned!");
					}
				}
			}else if(args[1].equalsIgnoreCase("unban")) {
				if(args.length == 2) {
					sender.sendMessage(Main.getPrefix() + " Unban socket ip - core unban (IP)");
				}else {
					if(args[2] != null) {
						Main.getSocketManager().getBannedSockets().remove(args[2]);	
						DefaultConfig.config.set("Core.BanList", Main.getSocketManager().getBannedSockets());
						sender.sendMessage(" IP: " + args[2] + " has been unbanned!");
					}
				}
			}else if(args[1].equalsIgnoreCase("banlist")) {
				if(args.length == 2) {
					sender.sendMessage("");
					sender.sendMessage(Main.getPrefix() + " List of banned sockets ip");
					sender.sendMessage("");
					sender.sendMessage(" ID | IP");
					sender.sendMessage("");
					int i = 0;
					for(String s : Main.getSocketManager().getBannedSockets()) {
						i++;
						sender.sendMessage(" * " + i + " | " + s);
					}
					sender.sendMessage("");
				}
			}
		}
	}

	@Override
	public String getCommand() {
		return "core";
	}

	@Override
	public String getDescription() {
		return "Command to managment core";
	}

}
