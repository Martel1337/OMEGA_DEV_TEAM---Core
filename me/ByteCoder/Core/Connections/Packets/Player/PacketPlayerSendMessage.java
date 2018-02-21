package me.ByteCoder.Core.Connections.Packets.Player;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Connections.Data.Player.Player;
import me.ByteCoder.Core.Connections.Packets.Packet;

public class PacketPlayerSendMessage extends Packet {

private Player p;
private String m;
	
	public PacketPlayerSendMessage(Player player, String message){
		this.p = player;
		this.m = message;
	}

	@Override
	public void execute() {
		Main.getSocketManager().getClient(this.p.getServer().getName()).write(DataType.PLAYER.toString(), DataAction.SENDMESSAGE.toString(), this.m, this.p.getName());
	}
}
