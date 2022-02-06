package com.sylvain.jdr.scheduler;

import com.sylvain.jdr.data.dto.impl.Player;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class EventScheduler {

	private static ScheduledExecutorService executor;


	public static void update(List<Player> players) {
		if (executor != null) {
			executor.shutdown();
		}
		executor = Executors.newScheduledThreadPool(players.size() + 1);
		for(Player player : players) {
			schdule(player, executor);
		}
	}

	public static void schdule(Player player, ScheduledExecutorService executor) {
		Calendar nextMonth = Calendar.getInstance();
		nextMonth.add(Calendar.MONTH, 1);
		nextMonth.set(Calendar.DAY_OF_MONTH, 1);
		nextMonth.set(Calendar.HOUR_OF_DAY, 0);
		nextMonth.set(Calendar.MINUTE, 0);
		nextMonth.set(Calendar.SECOND, 0);
		nextMonth.set(Calendar.MILLISECOND, 0);
		long initialDelay = nextMonth.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		executor.schedule(new EventRunnable(player, executor), initialDelay, TimeUnit.MILLISECONDS);
	}
}
