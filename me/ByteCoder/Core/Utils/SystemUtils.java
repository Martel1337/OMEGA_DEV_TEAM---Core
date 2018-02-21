package me.ByteCoder.Core.Utils;

import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class SystemUtils {

public static void optimizeCore(boolean notify) {
	if(notify == true) Logger.println("Starting 'GC' process...", LoggType.WARNING);
	System.gc();
	if(notify == true) Logger.println("Ending 'GC' process...", LoggType.WARNING);
}
	
}
