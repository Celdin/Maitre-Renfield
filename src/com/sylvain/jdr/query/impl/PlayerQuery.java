package com.sylvain.jdr.query.impl;

import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.Query;

public class PlayerQuery extends Query<Player> {

	public Player getById(String id) {
		final Player player = new Player();
		player.setBank(1000L);
		player.setInventory(100L);
		player.setIncome(500L);
		player.setUid(id);
		return player;
	}

}
