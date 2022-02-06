package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class IncomeAction extends Action {
	private final static String MESSAGE_OK = "Vous avez set le revenu de %s à %d€/mois.";
	User destinataire = null;
	Comptes comptes = null;
	Long montant = null;

	Player source;

	@Builder
	public IncomeAction(GenericCommandInteractionEvent event, User destinataire, Comptes comptes, Long montant) {
		super(event);
		this.destinataire = destinataire;
		this.comptes = comptes;
		this.montant = montant;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getUser().getId());
		if(!validate())
			return;
		if(!validate())
			return;
		switch (comptes) {
		case BANQUE:
			source.setIncomeBank(montant);
			break;
		case INVENTAIRE:
			source.setIncomeInv(montant);
			break;
		}
		ReplyAction.builder()
				.event(event)
				.message(String.format(MESSAGE_OK, getName(destinataire), montant))
				.build()
				.apply();
	}

	@Override
	public boolean validate() {
		if (!adminCheck()) {
			return false;
		}
		return true;
	}
}
