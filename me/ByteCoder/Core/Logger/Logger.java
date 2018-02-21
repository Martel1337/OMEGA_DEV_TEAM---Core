package me.ByteCoder.Core.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Utils.DateUtils;

public class Logger {
    protected static String defaultLogFile = "Logs" + File.separatorChar + DateUtils.getCurrentTime2() + ".txt";
    
public enum LoggType
{
	WARNING, INFO, ERROR;
}
    
public static void println(String s, LoggType l) {
try {
	writeF(defaultLogFile, s, l);
	if(Main.getCommandManager() != null) {
		Main.getCommandManager().getReader().println("[" + DateUtils.getCurrentTime1() + " " + l + "]: " + s);
		Main.getCommandManager().getReader().flush();
	}else {
		System.out.println("[" + DateUtils.getCurrentTime1() + " " + l + "]: " + s);
	}
	
} catch (IOException e) {
	e.printStackTrace();
}
}

private static void writeF(String f, String s, LoggType l) throws IOException {
    FileWriter aWriter = new FileWriter(f, true);
    aWriter.write("[" + DateUtils.getCurrentTime1() + " " + l + "]: " + s + "\n");
    aWriter.flush();
    aWriter.close();
}

public static void createLoggFile(){
	File file = new File("Logs" + File.separatorChar);
	file.mkdir();
}
}