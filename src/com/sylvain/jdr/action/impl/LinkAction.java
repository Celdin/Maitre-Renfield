package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.entities.GuildMessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class LinkAction extends Action {
	GuildMessageChannel channel;
	User destinataire;

	@Builder
	public LinkAction(GenericCommandInteractionEvent event, User destinataire, GuildMessageChannel channel) {
		super(event);
		this.destinataire = destinataire;
		this.channel = channel;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		final Player player = playerQuery.getById(destinataire.getId());
		player.setChannel(channel.getId());
		ReplyAction.builder()
				.event(event)
				.message(String.format("Joueur %s sur cannal %s", getName(destinataire), channel.getName()))
				.build()
				.apply();
	}

	@Override
	public boolean validate() {
		return true;
	}
}