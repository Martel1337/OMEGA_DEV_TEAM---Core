package me.ByteCoder.Core.Connections.Packets.Bukkit;

import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Connections.Packets.Packet;
import me.ByteCoder.Core.Connections.SocketClient;

public class PacketBukkitBroadcast extends Packet {
	
	private SocketClient c;
	private String m;
	
	public PacketBukkitBroadcast(SocketClient client, String message) {
		this.c = client;
		this.m = message;
	}

	@Override
	public void execute() {
		this.c.write(DataType.SERVER.toString(), DataAction.SENDMESSAGE.toString(), this.m, "BROADCAST");
	}
}
