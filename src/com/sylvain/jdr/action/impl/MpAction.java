package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import lombok.Builder;
import net.dv8tion.jda.api.entities.PrivateChannel;
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
		final PrivateChannel privateChannel = destinataire.openPrivateChannel().complete();
		privateChannel.sendMessage(message).queue();
	}

	@Override
	public boolean validate() {
		return true;
	}
}
