package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class TransferAction extends Action {
	private final static String MESSAGE_OK = "Vous transferer %s€ [%s] à %s";
	private final static String MESSAGE_MONTANT_NEG = "Indiquer un montant suppérieur à zero.";
	private final static String MESSAGE_ISSUFISANT_BANQUE = "Vous avez pas suffisament de fond sur votre compte en banque.";
	private final static String MESSAGE_ISSUFISANT_INV = "Vous avez pas suffisament de fond dans votre inventaire.";

	Comptes compte = null;
	User destinataire = null;
	Long montant = null;
	
	Player source;
	Player cible;



	@Builder
	public TransferAction(GenericCommandInteractionEvent event, Comptes compte, User destinataire, Long montant) {
		super(event);
		this.compte = compte;
		this.destinataire = destinataire;
		this.montant = montant;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getUser().getId());
		cible = playerQuery.getById(destinataire.getId());
		if(!validate())
			return;
		switch (compte) {
		case BANQUE:
			source.setBank(source.getBank() - montant );
			cible.setBank(cible.getBank() + montant );
			break;
		case INVENTAIRE:
			source.setInventory(source.getInventory() - montant );
			cible.setInventory(cible.getInventory() + montant );
			break;
		}
		playerQuery.save(source);
		playerQuery.save(cible);
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
		switch (compte) {
		case BANQUE:
			if(source.getBank() - montant < 0){
				ReplyAction.builder()
						.event(event)
						.message(MESSAGE_ISSUFISANT_BANQUE)
						.build()
						.apply();
				return false;
			}
			break;
		case INVENTAIRE:
			if(source.getInventory() - montant < 0){
				ReplyAction.builder()
						.event(event)
						.message(MESSAGE_ISSUFISANT_INV)
						.build()
						.apply();
				return false;
			}
			break;
		}
		return true;
	}
}
