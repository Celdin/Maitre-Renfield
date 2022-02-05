package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import lombok.Builder;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class ReplyAction extends Action {
	String message;

	@Builder
	public ReplyAction(GenericCommandInteractionEvent event, String message) {
		super(event);
		this.message = message;
	}

	@Override
	public void apply() {
		if(validate())
			event.reply(message).queue();
	}

	@Override
	public boolean validate() {
		if(message == null) {
			ReplyAction.builder().event(event).message("Erreur, pas de réponses.").build().apply();
			return false;
		}
		return true;
	}
}
