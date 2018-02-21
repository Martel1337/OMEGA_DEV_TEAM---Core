package me.ByteCoder.Core.Command.Commands;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Command.CommandSender;
import me.ByteCoder.Core.Utils.DateMath;

public class Status extends Command {

	@Override
	public void exec(CommandSender sender, String in, String[] args) {
		if(args.length == 1){
			RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
			DateMath date = new DateMath(rb.getUptime());
			Runtime r = Runtime.getRuntime();
			long memUsed = (r.totalMemory() - r.freeMemory()) / 1048576; 
			long memFree = r.freeMemory() / 1048576;
			long memMax = r.maxMemory() / 1048576;
			sender.sendMessage("");
			sender.sendMessage(Main.getPrefix() + " Current system information");
			sender.sendMessage("");
			sender.sendMessage(" * Uptime: " + date.getDays() + " Days. " + date.getHours() + " Hours. " + date.getMinuts() + " Minutes. " + date.getSeconds() + " Seconds.");
			sender.sendMessage(" * Memory(Used/Free/Max): " + memUsed + "/" + memFree + "/" + memMax);
			sender.sendMessage(" * Banned sockets: " + Main.getSocketManager().getBannedSockets().size());
			sender.sendMessage(" * Connected servers: " + Main.getSocketManager().getClients().size());
			sender.sendMessage(" * Connected java clients: " + Main.getSocketManager().getJavaClients().size());
			sender.sendMessage(" * Socket server closed?: " + Main.getSocketManager().getSocketServer().isClosed());
			sender.sendMessage(" * BCollections count: " + Main.getCollectionManager().getCollections().size());
			sender.sendMessage("");
		}
	}

	@Override
	public String getCommand() {
		return "status";
	}

	@Override
	public String getDescription() {
		return "Get current information above System";
	}

}
