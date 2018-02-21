package me.ByteCoder.Core.Runnable;

import java.util.Collection;
import java.util.HashMap;

import me.ByteCoder.Core.Main;
import me.ByteCoder.Core.Logger.Logger;
import me.ByteCoder.Core.Logger.Logger.LoggType;

public class SchedulerManager {

protected static HashMap<String, Scheduler> schedulers;	

public SchedulerManager() {
	schedulers = new HashMap<String, Scheduler>();
	Main.loadedManagers++;
	Logger.println("Scheduler manager loaded. (" + Main.loadedManagers + "/" + Main.totalManagers + ")", LoggType.INFO);
}

public Scheduler createScheduler(String name, Runnable run) {
	Scheduler sched = new Scheduler(run);
	sched.setName(name);
	schedulers.put(name, sched);
	return sched;
}

public Collection<Scheduler> getSchedulers(){
	return schedulers.values();
}

public Scheduler getScheduler(String name) {
	return schedulers.get(name);
}

}
