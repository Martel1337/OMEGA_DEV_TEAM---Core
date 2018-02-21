package me.ByteCoder.Core.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Files.YAML.DefaultConfig;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class ServerUtils {
	
public static ArrayList<String> timedOutIps = new ArrayList<String>();
public static HashMap<String, Integer> badIps = new HashMap<String, Integer>();	

public static int randomInt(Integer i){
	Random r = new Random();
	return r.nextInt(i);
}

public static void run(String exe){
	try {
		Runtime.getRuntime().exec(exe);
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static boolean isIpReachable(String ip){
    boolean state = false;

    try {
        state = InetAddress.getByName(ip).isReachable(5000);
    } catch (IOException e) {
        //Parse error here
    }

    return state;
}

public static void startTimedOutTimer(Socket s, DataInputStream in) {
	  ScheduledExecutorService sched = Executors.newSingleThreadScheduledExecutor();
	    sched.scheduleAtFixedRate(new Runnable() {
	    	
	    	int seconds = 0;
			@Override
			public void run() {
				 try {
					 if(seconds == 10) {
						 if(s.getInputStream().read() == -1) {
							 if(!s.isClosed()) {
								 if(timedOutIps.contains(s.getInetAddress().getHostAddress().toString())) {
									 if(!Main.getSocketManager().getBannedSockets().contains(s.getInetAddress().getHostAddress().toString())) {
										 Main.getSocketManager().getBannedSockets().add(s.getInetAddress().getHostAddress().toString());
								         DefaultConfig.config.set("Core.BanList", Main.getSocketManager().getBannedSockets());
									     s.close();
									     Logger.println("Socket " + s.getInetAddress().getHostAddress().toString() + " has been banned. Reason: TimeOut(x2).", LoggType.WARNING);
									 }
								 }
								 timedOutIps.add(s.getInetAddress().getHostAddress().toString());
								 Logger.println("Socket " + s.getInetAddress().getHostAddress().toString() + " has been disconnected. Reason: TimedOut", LoggType.WARNING);
								 s.close();
							 }
						 }else{
							 sched.shutdown();
						 }
				 }
				 } catch (IOException e) {}
				seconds++;
			}
	    	
	    }, 1, 1, TimeUnit.SECONDS);
}

public static void SocketChecker(Socket s) {
	if(badIps.get(s.getInetAddress().getHostAddress().toString()) != null) {
		if (badIps.get(s.getInetAddress().getHostAddress().toString()) > 3){
	    	Main.getSocketManager().getBannedSockets().add(s.getInetAddress().getHostAddress().toString());
	    	DefaultConfig.config.set("Core.BanList", Main.getSocketManager().getBannedSockets());
	    	Logger.println("Socket " + s.getInetAddress().getHostAddress().toString() + " has been banned. Reason: Bad login data.", LoggType.WARNING);
	    	try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			badIps.put(s.getInetAddress().getHostAddress().toString(), badIps.get(s.getInetAddress().getHostAddress().toString()) + 1);
		}
	}else {
		badIps.put(s.getInetAddress().getHostAddress().toString(), 0);
	}
}

}
