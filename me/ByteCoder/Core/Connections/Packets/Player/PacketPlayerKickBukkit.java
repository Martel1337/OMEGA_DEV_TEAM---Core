package me.ByteCoder.Core.Connections.Packets.Player;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Player.Player;
import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Connections.Packets.Packet;
 
public class PacketPlayerKickBukkit extends Packet {

private Player p;
private String r;

	public PacketPlayerKickBukkit(Player player, String reason){
		this.p = player;
		this.r = reason;
	}

	@Override
	public void execute() {
		Main.getSocketManager().getClient(this.p.getServer().getName()).write(DataType.PLAYER.toString(), DataAction.KICK.toString(), this.r, this.p.getName());
	}
	
}
