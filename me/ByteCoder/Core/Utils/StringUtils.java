package me.ByteCoder.Core.Utils;

import java.io.File;
import java.util.Collection;
import java.util.Random;
import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Connections.Data.Client.JavaClient;

public class StringUtils {

private static final String[] charTable = new String[65536];
	
public static String getSaltString(int legth) {
	String SALTCHARS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz11223344556677889900";
	StringBuilder salt = new StringBuilder();
	Random rnd = new Random();
	while (salt.length() < legth) { // length of the random string.
		int index = (int) (rnd.nextFloat() * SALTCHARS.length());
		salt.append(SALTCHARS.charAt(index));
	}
	String saltStr = salt.toString();
	return saltStr;
}

public static String getUpperRandomString(int leght) {
	String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	StringBuilder salt = new StringBuilder();
	Random rnd = new Random();
	while (salt.length() < leght) { // length of the random string.
		int index = (int) (rnd.nextFloat() * SALTCHARS.length());
		salt.append(SALTCHARS.charAt(index));
	}
	String saltStr = salt.toString();
	return saltStr;
}

public static boolean isBoolean(String str) {
	boolean bol = false;
	if(str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")){
		bol = true;
	}else {
		bol = false;
	}
	return bol;
}

public static File getJarFile() {
	return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
}

public static boolean isInteger(String i) {
	try { 
        Integer.parseInt(i); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    return true;
}

public static boolean isDouble(String i) {
	try
	{
	  Double.parseDouble(i);
	}
	catch(NumberFormatException e)
	{
	  return false;
	}
	return true;
}
  
  static
  {
    charTable['А'] = "A";
    charTable['Б'] = "B";
    charTable['В'] = "V";
    charTable['Г'] = "G";
    charTable['Д'] = "D";
    charTable['Е'] = "E";
    charTable['Ё'] = "E";
    charTable['Ж'] = "ZH";
    charTable['З'] = "Z";
    charTable['И'] = "I";
    charTable['Й'] = "I";
    charTable['К'] = "K";
    charTable['Л'] = "L";
    charTable['М'] = "M";
    charTable['Н'] = "N";
    charTable['О'] = "O";
    charTable['П'] = "P";
    charTable['Р'] = "R";
    charTable['С'] = "S";
    charTable['Т'] = "T";
    charTable['У'] = "U";
    charTable['Ф'] = "F";
    charTable['Х'] = "H";
    charTable['Ц'] = "C";
    charTable['Ц'] = "CH";
    charTable['Щ'] = "SH";
    charTable['Ш'] = "SH";
    charTable['Ъ'] = "'";
    charTable['Ы'] = "Y";
    charTable['Ь'] = "'";
    charTable['Е'] = "E";
    charTable['У'] = "U";
    charTable['Я'] = "YA";
    for (int i = 0; i < charTable.length; i++)
    {
      char idx = (char)i;
      char lower = new String(new char[] { idx }).toLowerCase().charAt(0);
      if (charTable[i] != null) {
        charTable[lower] = charTable[i].toLowerCase();
      }
    }
  }
  
  public static String toTranslit(String text)
  {
    char[] charBuffer = text.toCharArray();
    StringBuilder sb = new StringBuilder(text.length());
    for (char symbol : charBuffer)
    {
      String replace = charTable[symbol];
      sb.append(replace == null ? Character.valueOf(symbol) : replace);
    }
    return sb.toString();
  }
  
  public static boolean isContainsIP(Collection<JavaClient> c, String o) {
	  boolean bol = false;
	  for (JavaClient client : c) {
		  if(client.getSocket().getInetAddress().getHostAddress().toString().equalsIgnoreCase(o)) {
			  bol = true;
		  }
	  }
	  return bol;
  }
  
public static String formatString(String str, String chr, int value) {
	String s = "";
	for(int i = 0; i<value; i++) {
		s += String.format("%-" + value + "" + chr + "= %s" , s, str.split(chr)[i]);
	}
	return s;
   }
}
	
	
