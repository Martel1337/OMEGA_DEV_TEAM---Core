package me.ByteCoder.Core.Command.Commands;

import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Command.CommandSender;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.ServerUtils;
import me.ByteCoder.Core.Utils.StringUtils;

public class Restart extends Command {

	@Override
	public void exec(CommandSender sender, String in, String[] args) {
		if(args.length == 1){
			loadShutdownListener();
			Logger.println("Restarting...", LoggType.INFO);
			System.exit(0);
		}
	}

	@Override
	public String getCommand() {
		return "restart";
	}

	@Override
	public String getDescription() {
		return "Restart core system";
	}
	
	private static void loadShutdownListener() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				ServerUtils.run("cmd /C java -jar " + StringUtils.getJarFile().getName());
			}
		});
	}

}
