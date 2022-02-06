package com.sylvain.jdr.query.impl;

import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.Query;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerQuery extends Query<Player> {

	public Player getById(String id) {
		final Player player = new Player();
		player.setBank(1000L);
		player.setInventory(100L);
		player.setIncomeBank(600L);
		player.setIncomeInv(550L);
		player.setUid(id);
		player.setChannel("939628364224929812");
		return player;
	}

	@Override
	public List<Player> getAll() throws SQLException {
		return Stream.of("112263806700224512", "129568273057447937", "581199373703184384", "532819372851462156").map(this::getById).collect(
				Collectors.toList());
	}
}
