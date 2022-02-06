package com.sylvain.jdr.scheduler;

import com.sylvain.jdr.data.dto.impl.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class EventRunnable implements Runnable {
	Player player;
	ScheduledExecutorService executor;

	public EventRunnable(Player player, ScheduledExecutorService executor) {
		this.player = player;
		this.executor = executor;
	}

	@Override
	public void run() {
		player.setInventory(player.getInventory() + player.getIncomeInv());
		player.setBank(player.getBank() + player.getIncomeBank());
		EventScheduler.schdule(player, executor);
	}
}
