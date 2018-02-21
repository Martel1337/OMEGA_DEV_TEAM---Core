package me.ByteCoder.Core.Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Server.Server;
import me.ByteCoder.Core.Connections.Packets.Packet;
import me.ByteCoder.Core.Connections.SocketManager.ServerType;
import me.ByteCoder.Core.Data.Attributes;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.EventUtils.Events.SocketMessageReceiveEvent;

public class SocketClient {
    private Socket socket;
    private Server server;
    private String name;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerType type;
    private ExecutorService readpacket;
    private Attributes data;
    private int r;
    private int s;
    
    public SocketClient(Socket socket, String n, ServerType t) {
        this.socket = socket;
        this.readpacket = Executors.newCachedThreadPool();
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = n;
            this.type = t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data = new Attributes("0;0", ";");
        this.server = new Server(this.name, this);
        //Death packet ruin Read packet work, turn off it or fix Death packet
        Main.getSocketManager().addClient(this.name, this);
        this.startReadPacket();
    }
    
    private void startReadPacket() {
    	readpacket.submit(() -> {
    	try {
            while (!this.socket.isClosed()) {
	                String MessageType = this.in.readUTF();
	                String MessageAction = this.in.readUTF();
	                String Data = this.in.readUTF();
	                String Target = this.in.readUTF();
	                new SocketMessageReceiveEvent(this, MessageType, MessageAction, Data, Target, new DataInputStream(this.socket.getInputStream()), new DataOutputStream(this.socket.getOutputStream())).run();
	            	r++;
	                this.data.setField(1, Integer.valueOf(r));
            }
        } catch (IOException e) {
        	Logger.println(e.getMessage(), LoggType.ERROR);
        	end("#3");
        }
    	});
    }
    
    public String getName(){
    	return this.name;
    }
    
    public Attributes getAttributes() {
    	return this.data;
    }
    
    public void end(String reason) {
        try {
        	this.socket.close();
            Main.getSocketManager().removeClient(this.name);
            Main.getServerManager().removeServer(this.name);
            this.readpacket.shutdown();
            if(Main.getNotify() == true) {
            	Logger.println(this.type + " " + this.name + " has disconnected. Reason: " + reason, LoggType.INFO);
            }
        }
        catch (IOException e) {
           Logger.println(e.getMessage(), LoggType.ERROR);
        }
    }
    
    public void write(String MessageType, String MessageAction, String DataToSend, String target){
	    	try {
	    		this.out.writeUTF(MessageType);
	    		this.out.writeUTF(MessageAction);
	    		this.out.writeUTF(DataToSend);
	    		this.out.writeUTF(target);
	    		this.out.flush();
			} catch (IOException e) {
				Logger.println(e.getMessage(), LoggType.ERROR);
			}
	    	s++;
	    	this.data.setField(0, Integer.valueOf(s));
    }
    
    public DataOutputStream getOut(){
    	return this.out;
    }
    
    public DataInputStream getIn(){
    	return this.in;
    }
    
    public ServerType getServerType(){
    	return this.type;
    }
    
    public Server getServer(){
    	return this.server;
    }
    
    public void sendPacket(Packet packet){
    	packet.execute();
    }
}

