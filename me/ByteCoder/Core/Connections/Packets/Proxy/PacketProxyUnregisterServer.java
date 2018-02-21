package me.ByteCoder.Core.Connections.Packets.Proxy;

import me.ByteCoder.Core.Connections.SocketClient;
import me.ByteCoder.Core.Connections.MessageHandler.DataAction;
import me.ByteCoder.Core.Connections.MessageHandler.DataType;
import me.ByteCoder.Core.Connections.Packets.Packet;

public class PacketProxyUnregisterServer extends Packet {

	private SocketClient p;
	private SocketClient t;
	
	public PacketProxyUnregisterServer(SocketClient proxy, SocketClient target){
		this.p = proxy;
		this.t = target;
	}
	
	@Override
	public void execute() {
		this.p.write(DataType.PROXY.toString(), DataAction.UNREGISTER.toString(), "Unregister current server", this.t.getName());
	}

}
