package com.sylvain.jdr.query.impl;

import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.Query;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerQuery extends Query<Player> {

	public Player getById(String id) {
		final Player player = new Player();
		player.setBank(1000L);
		player.setInventory(100L);
		player.setIncome(500L);
		player.setUid(id);
		return player;
	}

	@Override
	public List<Player> getAll() throws SQLException {
		return Arrays.asList("112263806700224512", "129568273057447937", "581199373703184384", "337275100120219650", "311140476256256000", "532819372851462156").stream().map(this::getById).collect(
				Collectors.toList());
	}
}
