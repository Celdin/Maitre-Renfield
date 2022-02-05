package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCheckAction extends Action {

	private final static String TITRE = "Fonds de %s";

	@Builder
	public AdminCheckAction(GenericCommandInteractionEvent event) {
		super(event);
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		try {
			final List<Player> all = playerQuery.getAll();
			event.replyEmbeds(all.stream().map(player -> CheckAction.builder().event(event).build().getEmbedBuilder(player)).collect(Collectors.toList())).queue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean validate() {
		return true;
	}

}
