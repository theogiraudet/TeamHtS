package fr.theogiraudet.HtS;

import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

	 HtS main;
	 int timer;
	 int increment = 0;
	 String timerCall;
	 int[] prevent;
	TimerOutput to;
	
	public Timer(HtS main, String timerCall, int timer, int... prevent) {
		this.prevent = prevent;
		this.main = main;
		this.timer = timer;
		this.timerCall = timerCall;
		this.to = new TimerOutput(main);
	}
	
	public Timer(HtS main, String timerCall, int timer) {
		this.main = main;
		this.timer = timer;
		this.timerCall = timerCall;
		this.to = new TimerOutput(main);
	}
	

	@Override
	public void run() {
		increment++;
		if(prevent != null) {
			if(prevent.length != 0) {
				int j = 0;
				for(int i : prevent) {
					if(i * 60 == increment) {
						to.Output(timerCall, j);
						j++;
					}
				}
			}
		}
		if(timer * 60 <= increment) {
			if(prevent != null) {
				to.Output(timerCall, prevent.length);
			} else {
				to.Output(timerCall, 0);
			}
			this.cancel();
		}
	}
	
}
