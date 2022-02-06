package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class MpAction extends Action {

	User destinataire;
	String message;

	@Builder
	public MpAction(GenericCommandInteractionEvent event, User destinataire, String message) {
		super(event);
		this.destinataire = destinataire;
		this.message = message;
	}

	@Override
	public void apply() {

		PlayerQuery playerQuery = new PlayerQuery();
		final Player player = playerQuery.getById(destinataire.getId());
		if(player.getChannel() != null) {
			final TextChannel textChannelById = event.getJDA().getTextChannelById(player.getChannel());
			if (textChannelById != null) textChannelById.sendMessage(message).queue();
		}
	}

	@Override
	public boolean validate() {
		return true;
	}
}
