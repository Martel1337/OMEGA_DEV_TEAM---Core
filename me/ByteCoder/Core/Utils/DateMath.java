package me.ByteCoder.Core.Utils;

import java.util.concurrent.TimeUnit;

public class DateMath {

	public long millis;
	public int Years;
	public int Days;
	public int Hours;
	public int Minutes;
	public int Seconds;
	
public DateMath(long millis){
	this.millis = millis;
    this.Days = (int) TimeUnit.MILLISECONDS.toDays(this.millis);
    this.millis -= TimeUnit.DAYS.toMillis(this.Days);
    this.Hours = (int) TimeUnit.MILLISECONDS.toHours(this.millis);
    this.millis -= TimeUnit.HOURS.toMillis(this.Hours);
    this.Minutes = (int) TimeUnit.MILLISECONDS.toMinutes(this.millis);
    this.millis -= TimeUnit.MINUTES.toMillis(this.Minutes);
    this.Seconds = (int) TimeUnit.MILLISECONDS.toSeconds(this.millis);
}

public int getSeconds(){
	return this.Seconds;
}

public int getMinuts(){
	return this.Minutes;
}

public int getHours(){
	return this.Hours;
}

public int getDays(){
	return this.Days;
}
}