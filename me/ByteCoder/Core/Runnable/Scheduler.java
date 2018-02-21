package me.ByteCoder.Core.Runnable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

private Runnable run;
private String n;
private boolean pause;
private ScheduledExecutorService service;

protected Scheduler(Runnable r) {
	this.run = r;
	this.pause = false;
	this.service = Executors.newSingleThreadScheduledExecutor();
}

protected void setName(String name) {
	this.n = name;
}

public void runScheduler(long first, long second, TimeUnit time) {
	this.service.scheduleAtFixedRate(this.run, first, second, time);
}

public void endScheduler() {
	this.service.shutdown();
	SchedulerManager.schedulers.remove(this.n);
}

public boolean isEnded() {
	return this.service.isShutdown();
}

public void setPaused(boolean bol) {
	this.pause = bol;
}

public boolean isPaused() {
	return this.pause;
}

}
