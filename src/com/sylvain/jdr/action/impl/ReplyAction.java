package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import lombok.Builder;
import lombok.Data;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

@Data
@Builder
public class ReplyAction implements Action {
	GenericCommandInteractionEvent event;
	String message;

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
