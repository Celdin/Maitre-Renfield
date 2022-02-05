package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class AddAction extends Action {
	private final static String MESSAGE_OK = "Vous donnez %s€[%s] à %s.";
	private final static String MESSAGE_MONTANT_NEG = "Indiquer un montant suppérieur à zero.";

	Comptes compte = null;
	User destinataire = null;
	Long montant = null;

	Player source;

	@Builder
	public AddAction(GenericCommandInteractionEvent event, Comptes compte, User destinataire, Long montant, String motif) {
		super(event);
		this.compte = compte;
		this.destinataire = destinataire;
		this.montant = montant;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getUser().getId());
		if(!validate())
			return;
		switch (compte) {
		case BANQUE:
			source.setBank(source.getBank() + montant );
			break;
		case INVENTAIRE:
			source.setInventory(source.getInventory() + montant );
			break;
		}
		ReplyAction.builder()
				.event(event)
				.message(String.format(MESSAGE_OK, montant, compte, getName(destinataire)))
				.build()
				.apply();

	}

	@Override
	public boolean validate() {
		if (montant <= 0) {
			ReplyAction.builder()
					.event(event)
					.message(MESSAGE_MONTANT_NEG)
					.build()
					.apply();
			return false;
		}
		return true;
	}
}
