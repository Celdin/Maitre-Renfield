package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import lombok.Builder;
import lombok.Data;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

@Data
@Builder
public class TransferAction implements Action {
	GenericCommandInteractionEvent event;
	Comptes compte = null;
	User destinataire = null;
	Long montant = null;

	@Override
	public void apply() {

	}

	@Override
	public boolean validate() {
		return false;
	}
}
