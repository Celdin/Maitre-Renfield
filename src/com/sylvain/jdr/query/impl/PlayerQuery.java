package com.sylvain.jdr.query.impl;

import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.driver.PostgreSQLDriver;
import com.sylvain.jdr.query.Query;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class PlayerQuery extends Query<Player> {

	public PlayerQuery() {
		super();
		setSupplier(() -> new Player());
	}

	public Player getById(String id) {
		Player player = null;
		try {
			String query = "SELECT * FROM " + Player.TABLE_NAME + " WHERE " + Player.COLUMN_UID + " = '" + id + "';";
			Connection connection = PostgreSQLDriver.getConnection();
			Statement statement = connection.createStatement();
			System.out.println(query);
			ResultSet result = statement.executeQuery(query);
			if(result.next())
				player = result.getObject(0, Player.class);
			statement.close();
			result.close();
		}catch (SQLException e) {
			log.error("Can't retrieve Player " + id + ": ", e);
		}
		if(player != null)
			return player;
		player = new Player();
		player.setBank(0L);
		player.setInventory(0L);
		player.setIncomeBank(0L);
		player.setIncomeInv(0L);
		player.setUid(id);
		return player;
	}
}
