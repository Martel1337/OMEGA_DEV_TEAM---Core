package me.ByteCoder.Core.Command.Commands;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Command.Command;
import me.ByteCoder.Core.Command.CommandSender;
import me.ByteCoder.Core.Files.YAML.DefaultConfig;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class Ending extends Command {

	@Override
	public void exec(CommandSender sender, String in, String[] args) {
		if(args.length == 1){
			Main.getModuleManager().disablePlugins();
	 		Logger.println("", LoggType.INFO);
	 		Logger.println("Bye, thx for using me. <3", LoggType.INFO);
	 		DefaultConfig.saveConfig();
			System.exit(1);
		}
	}

	@Override
	public String getCommand() {
		return "end";
	}

	@Override
	public String getDescription() {
		return "Stop core procces";
	}
}
