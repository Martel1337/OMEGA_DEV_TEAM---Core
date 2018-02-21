package me.ByteCoder.Core.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils
{
  private static final int[] seconds = { 1, 60, 3600, 86400, 2592000, 31536000 };
  private static final String[] names = { "second", "minute", "hour", "day", "month", "year" };
  
  public static String dateToString(Date date, boolean future) {
    long diff = Instant.now().getEpochSecond() - date.toInstant().getEpochSecond();
    if (future) {
      diff *= -1L;
    }
    for (int i = 1; i < seconds.length; i++) {
      if (diff < seconds[i])
      {
        long result = diff / seconds[(i - 1)];
        return result + " " + names[(i - 1)] + (result != 1L ? "s" : "");
      }
    }
    return "";
  }
  
  public static String durationToStringCount(Duration d)
  {
    String s = "";
    boolean started = false;
    if (d.toDays() >= 365L)
    {
      started = true;
      s = s + d.toDays() / 365L + " ���" + (d.toDays() / 365L != 1L ? "�" : "") + ", ";
      d = d.minus(Duration.ofDays(d.toDays() / 365L * 365L));
    }
    if ((started) || (d.toDays() >= 30L))
    {
      started = true;
      s = s + d.toDays() / 30L + " �����" + (d.toDays() / 30L != 1L ? "�" : "") + ", ";
      d = d.minus(Duration.ofDays(d.toDays() / 30L * 30L));
    }
    if ((started) || (d.toDays() >= 7L))
    {
      started = true;
      s = s + d.toDays() / 7L + " ������" + (d.toDays() / 7L != 1L ? "�" : "") + ", ";
      d = d.minus(Duration.ofDays(d.toDays() / 7L * 7L));
    }
    if ((started) || (d.toDays() > 0L))
    {
      started = true;
      s = s + d.toDays() + " ����" + (d.toDays() != 1L ? "��" : "") + ", ";
      d = d.minus(Duration.ofDays(d.toDays()));
    }
    if ((started) || (d.toHours() > 0L))
    {
      s = s + d.toHours() + " ���" + (d.toHours() != 1L ? "�" : "") + ", ";
      started = true;
      d = d.minus(Duration.ofHours(d.toHours()));
    }
    if ((started) || (d.toMinutes() > 0L))
    {
      s = s + d.toMinutes() + " �����" + (d.toMinutes() != 1L ? "�" : "") + " � ";
      started = true;
      d = d.minus(Duration.ofMinutes(d.toMinutes()));
    }
    if ((started) || (d.getSeconds() > 0L)) {
      s = s + d.getSeconds() + " ������" + (d.getSeconds() != 1L ? "�" : "");
    }
    return s;
  }
  
  public static String now()
  {
    Date date = new Date(System.currentTimeMillis());
    DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    return formatter.format(date);
  }
  
  public static String durationToString(Duration duration)
  {
    Date date = new Date(System.currentTimeMillis() + duration.getSeconds() * 1000L);
    DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    return formatter.format(date);
  }
  
  public static String convertTimeToString(long time)
  {
    Date date = new Date(time);
    DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd � HH:mm:ss");
    return formatter.format(date);
  }
  
  public static Duration parseDurationString(String time)
  {
    Pattern timePattern = Pattern.compile("[1-9][0-9]*(y|mo|w|h|d|m|s)");
    Matcher matcher = timePattern.matcher(time);
    int years = 0;
    int months = 0;
    int weeks = 0;
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    while (matcher.find())
    {
      String r = matcher.group();
      switch (r.charAt(r.length() - 1))
      {
      case 'y': 
        years = Integer.parseInt(r.replace("y", ""));
        break;
      case 'o': 
        months = Integer.parseInt(r.replace("mo", ""));
        break;
      case 'w': 
        weeks = Integer.parseInt(r.replace("w", ""));
        break;
      case 'd': 
        days = Integer.parseInt(r.replace("d", ""));
        break;
      case 'h': 
        hours = Integer.parseInt(r.replace("h", ""));
        break;
      case 'm': 
        minutes = Integer.parseInt(r.replace("m", ""));
        break;
      case 's': 
        seconds = Integer.parseInt(r.replace("s", ""));
      }
    }
    return Duration.ofDays(years * 365 + months * 30 + weeks * 7 + days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
  }
  
	@SuppressWarnings("deprecation")
	public static long convertTime(String firstDate, String secondDate) {
		
		String cf = firstDate.replaceAll("-", "/");
		cf = cf.replace("|", "");
		String cs = secondDate.replaceAll("-", "/");
		cs = cs.replace("|", "");
		
		return ChronoUnit.MILLIS.between(new Date(cf).toInstant(), new Date(cs).toInstant());
	}
}
