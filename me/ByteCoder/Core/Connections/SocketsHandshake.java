package me.ByteCoder.Core.Connections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.SocketManager.ServerType;
import me.ByteCoder.Core.Connections.Data.Client.JavaClient;
import me.ByteCoder.Core.Connections.Data.Client.JavaClientFileHandshake;
import me.ByteCoder.Core.Files.YAML.DefaultConfig;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.ServerUtils;
import me.ByteCoder.Core.Utils.StringUtils;

public class SocketsHandshake {

private Socket s;
private DataInputStream in;
private DataOutputStream out;
private String name;
private String pass;
private ServerType type;
private ExecutorService servic;

public SocketsHandshake(Socket socket, ExecutorService service){
	if(Main.getSocketManager().getAccess() == true) {
		this.servic = service;
		this.s = socket;
	try {
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
	    this.type = Main.getSocketManager().converToType(this.in.readUTF());
	    this.name = this.in.readUTF();
	    this.pass = this.in.readUTF();
	    
	    if(this.type.equals(null) || this.type == null || this.name.equalsIgnoreCase(null) || this.pass.equalsIgnoreCase(null)) {
        	Main.getSocketManager().getBannedSockets().add(this.s.getInetAddress().getHostAddress().toString());
        	DefaultConfig.config.set("Core.BanList", Main.getSocketManager().getBannedSockets());
	    	socket.close();
	    	Logger.println("Socket " + this.s.getInetAddress().getHostAddress().toString() + " has been banned. Reason: Bad login data.", LoggType.WARNING);
	    } else {
        if(!this.pass.equalsIgnoreCase(Main.getSocketPassword())){
        	Main.getSocketManager().getBannedSockets().add(this.s.getInetAddress().getHostAddress().toString());
        	DefaultConfig.config.set("Core.BanList", Main.getSocketManager().getBannedSockets());
        	this.s.close();
        	Logger.println("Socket " + this.s.getInetAddress().getHostAddress().toString() + " has been banned. Reason: Bad login data.", LoggType.WARNING);
        }else{
            if(Main.getNotify() == true) {
            	Logger.println(this.type + " " + this.name + " has connected.", LoggType.INFO);
            }
            this.handle();
        }
	    }
    } catch (IOException e) {
    	if(Main.getDebug() == true) {
    		Logger.println("While tried to enable: " + s.getInetAddress().getHostAddress().toString() + ". Error: " + e.getMessage(), LoggType.ERROR);
    		ServerUtils.SocketChecker(this.s);
    	}
    }
	}else {
		try {
			socket.close();
		} catch (IOException e) {
			if(Main.getDebug() == true) {
				Logger.println("While tryinig close socket: " + s.getInetAddress().getHostAddress().toString() + ". Error: " + e.getMessage(), LoggType.ERROR);
			}
		}
	}
}

private void handle(){
    	if(this.type.equals(ServerType.Server)){
    		this.servic.submit(() -> {
    			new SocketClient(this.s, this.name, this.type);
    		});
    	}else if(this.type.equals(ServerType.Handshake)){
    		new DataHandshake(this.s);
    	}else if(this.type.equals(ServerType.JavaClientFileHandshake)) {
    		new JavaClientFileHandshake(this.s, this.name);
    	}else if(this.type.equals(ServerType.JavaClient)) {
    		if(!StringUtils.isContainsIP(Main.getSocketManager().getJavaClients(), this.s.getInetAddress().getHostAddress().toString())) {
	    		this.servic.submit(() -> {
	    			new JavaClient(this.s, this.in, this.out, this.name, this.servic);
	    		});
    		}else {
    			if(Main.getNotify() == true) {
            		Logger.println("Socket " + this.s.getInetAddress().getHostAddress() + " has been disconnected. Reason: Already connected to Core", LoggType.INFO);
            	}
            	try {
					this.s.close();
				} catch (IOException e) {
					if(Main.getDebug() == true) {
						Logger.println("While tried to register: " + this.name + ". Error: " + e.getMessage(), LoggType.ERROR);
					}
				}
    		}
    	}
}
}
