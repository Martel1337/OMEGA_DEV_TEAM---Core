package me.ByteCoder.Core.Connections.Packets.Proxy;

import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Connections.Packets.Packet;
import me.ByteCoder.Core.Connections.SocketClient;

public class PacketProxyRegisterServer extends Packet {

	private String data;
	private String target;
	private SocketClient p;
	
	public PacketProxyRegisterServer(SocketClient proxy, SocketClient target) {
		this.data = target.getServer().getServerData().getID() + ":" + target.getServer().getServerData().getPort();
		this.target = target.getName();
		this.p = proxy;
	}
	
	public void execute(){
		this.p.write(DataType.PROXY.toString(), DataAction.REGISTER.toString(), this.data, this.target);
	}
}
