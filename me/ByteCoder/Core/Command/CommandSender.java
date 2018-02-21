package me.ByteCoder.Core.Command;

import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class CommandSender {

private boolean isPlayer;
	
public void sendMessage(String mess){
	Logger.println(mess, LoggType.INFO);
}

public boolean isPlayer(){
	return this.isPlayer;
}
}
