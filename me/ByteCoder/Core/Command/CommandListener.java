package me.ByteCoder.Core.Command;

import java.io.IOException;

import jline.console.completer.StringsCompleter;
import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class CommandListener {

	public static void startListener(){
		Main.getCommandManager().loadCommands();
		Logger.println("", LoggType.INFO);
		Logger.println("For see help page type in cosole 'help'", LoggType.INFO);
		Logger.println("", LoggType.INFO);
		Main.getCommandManager().getReader().addCompleter(new StringsCompleter(Main.getCommandManager().getCommandsString()));
		startListenerAnOther();
	}
	
	public static void startListenerAnOther() {
        String line = null;
        try {
			while ((line=Main.getCommandManager().getReader().readLine("\n>")) != null) {
			    String[] input = line.split(" ");
			    if(!line.equalsIgnoreCase("")){
					Main.getCommandManager().performCommand(line, input);
			    }
			}
		} catch (IOException e) {
			startListenerAnOther();
		}
	}
}