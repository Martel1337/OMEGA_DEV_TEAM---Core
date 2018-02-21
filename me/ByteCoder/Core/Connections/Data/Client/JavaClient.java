package me.ByteCoder.Core.Connections.Data.Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Data.SCryptor;
import me.ByteCoder.Core.Logger.Logger;

import me.ByteCoder.Core.Logger.Logger.LoggType;
import me.ByteCoder.Core.Utils.DateUtils;
import me.ByteCoder.Core.Utils.StringUtils;
import me.ByteCoder.Core.Utils.SystemUtils;
import me.ByteCoder.Core.Utils.EventUtils.Events.JavaClientFileReceiveEvent;
import me.ByteCoder.Core.Utils.EventUtils.Events.JavaClientMessageReceiveEvent;

public class JavaClient {

private Socket s;
private String n;
private JavaClientData d;
private ExecutorService srvc;
private DataInputStream in;
private DataOutputStream out;
private String ckey;
private boolean locked;
private int id;

public JavaClient(Socket socket, DataInputStream inputStream, DataOutputStream outputStream, String name, ExecutorService service) {
    SystemUtils.optimizeCore(false);
	try {
		this.s = socket;
		this.srvc = service;
		this.id = Main.getSocketManager().getJavaClients().size() + 1;
		this.n = "Client-" + this.id;
		this.in = inputStream;
		this.out = outputStream;
		this.setData(new JavaClientData(this.in.readUTF()));
		this.locked = false;
		if(!Main.getSocketManager().tempJData.containsKey(this.n)) {
			Main.getSocketManager().tempJData.put(this.n, this.d);
			Main.getSocketManager().tempJData.get(this.n).setIP(this.s.getInetAddress().getHostAddress().toLowerCase());
		}else {
			Main.getSocketManager().tempJData.get(this.n).setLastConnection(DateUtils.getCurrentTime());
		}
		this.ckey = StringUtils.getUpperRandomString(16);
		this.out.writeUTF(ckey);
		this.out.flush();
	} catch (IOException e) {
		if(Main.getDebug() == true) {
			Logger.println("While enabling JavaClient: " + this.n + ". Error: " + e.getMessage(), LoggType.ERROR);
		}
		end("CONNECTION REFUSED!");
	}
	Main.getSocketManager().addJavaClient(this.n, this);
	this.startReadPacket();
}

public String getName() {
	return this.n;
}

public int getID() {
	return this.id;
}

public ExecutorService getExecutorService() {
	return this.srvc;
}

public Socket getSocket() {
	return this.s;
}

public JavaClientData getData() {
	return this.d;
}

public DataInputStream getInput() {
	return this.in;
}

public DataOutputStream getOutput() {
	return this.out;
}

public void setData(JavaClientData data) {
	this.d = data;
}

public void end(String reason) {
    try {
    	this.s.close();
        Main.getSocketManager().removeJavaClient(this.n);
        if(Main.getNotify() == true) {
        	Logger.println("JavaClient " + this.n + " has disconnected. Reason: " + reason, LoggType.INFO);
        }
    }
    catch (IOException e) {
    	if(Main.getDebug() == true) {
    		Logger.println("While tried to disconnect " + this.n + ". Error: " + e.getMessage(), LoggType.ERROR);
    	}
    }
    SystemUtils.optimizeCore(false);
}

public void setLocked(boolean bol) {
	this.locked = bol;
}

public boolean getLocked() {
	return this.locked;
}

public void write(String type, String action, String obj, String target) {
	if(!this.locked) {
		try {
			String cType = SCryptor.aesEncryptString(type, this.ckey);
			String cAction = SCryptor.aesEncryptString(action, this.ckey);
			String cData = SCryptor.aesEncryptString(obj, this.ckey);
			String cTarget = SCryptor.aesEncryptString(target, this.ckey);
			this.out.writeUTF(cType);
			this.out.writeUTF(cAction);
			this.out.writeUTF(cData);
			this.out.writeUTF(cTarget);
			this.out.flush();
		} catch (IOException e) {
			if(Main.getDebug() == true) {
				Logger.println("While sending mess to: " + this.n + ". Error: " + e.getMessage(), LoggType.ERROR);
			}
			end("CONNECTION REFUSED!");
		}
	}else {
		if(Main.getDebug() == true) {
			Logger.println("Could not write message for client: " + this.n + ". Reason: Locked", LoggType.WARNING);
		}
	}
}

public String getCryptorKey() {
	return this.ckey;
}

public void startReadPacket() {
    	this.srvc.submit(() -> {
    	try {
            while (!this.s.isClosed()) {
            		boolean readMessage = this.in.readBoolean();
            		if(readMessage == true) {
		                String MessageType = this.in.readUTF();
		                String MessageAction = this.in.readUTF();
		                String Data = this.in.readUTF();
		                String Target = this.in.readUTF();
		                
		                String dMessageType = SCryptor.aesDecryptString(MessageType, this.ckey);
		                String dMessageAction = SCryptor.aesDecryptString(MessageAction, this.ckey);
		                String dData = SCryptor.aesDecryptString(Data, this.ckey);
		                String dTarget = SCryptor.aesDecryptString(Target, this.ckey);
		                
		                
		                if(Main.getNotify() == true) {
		                	Logger.println("Received new message: Type: " + dMessageType + ". Action: " + dMessageAction + ". Data: " + dData + ". Target: " + dTarget  + ".", LoggType.WARNING);
		                }
		                
		                if(dMessageType.equalsIgnoreCase("FILE")) {
		                	new JavaClientFileReceiveEvent(this, this.in).run();
		                }else {
		                	 new JavaClientMessageReceiveEvent(this, dMessageType, dMessageAction, dData, dTarget, this.in, this.out).run();
		                }
            		}
            }
        } catch (IOException e) {
        	if(Main.getDebug() == true) {
        		Logger.println("(IOException) While received message from: " + this.n + ". Error: " + e.getMessage(), LoggType.ERROR);
        	}
        	end("CONNECTION CLOSED");
        }
    	if(Main.getDebug() == true) {
    		Logger.println("Message Thread of " + this.n + " has ended!", LoggType.INFO);
    	}
    	});
    }

public enum JavaClientMessageType{
	DATA, FILE, VK, WEB, CMD;
}

public enum JavaClientMessageAction{
	SET, RUN, CREATE, DELETE, OPEN, SAVE, CHANGE;
}
}

