package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class StealAction extends Action {
	private final static String MESSAGE_OK = "%s à voler %s€ à %s";
	private final static String PRIVATE_MESSAGE_OK = "Vous avez réussi à voler à %s %d€";
	private final static String MESSAGE_MONTANT_NEG = "Indiquer un montant suppérieur à zero.";
	private final static String MESSAGE_ISSUFISANT_INV = "%s n'a pas suffisament de fond dans son inventaire.";
	User destinataire = null;
	User victime = null;
	Long montant = null;

	Player source;
	Player cible;

	@Builder
	public StealAction(GenericCommandInteractionEvent event, User destinataire, User victime, Long montant) {
		super(event);
		this.destinataire = destinataire;
		this.victime = victime;
		this.montant = montant;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(destinataire.getId());
		cible = playerQuery.getById(victime.getId());
		if(!validate())
			return;
		source.setInventory(source.getInventory() + montant );
		cible.setInventory(cible.getInventory() - montant );
		playerQuery.save(source);
		playerQuery.save(cible);
		MpAction.builder()
				.event(event)
				.destinataire(destinataire)
				.message(String.format(PRIVATE_MESSAGE_OK, getName(victime), montant))
				.build()
				.apply();

		ReplyAction.builder()
				.event(event)
				.message(String.format(MESSAGE_OK, getName(destinataire), montant, getName(victime)))
				.build()
				.apply();
	}

	@Override
	public boolean validate() {
		if (!adminCheck()) {
			return false;
		}
		if (montant <= 0) {
			ReplyAction.builder()
					.event(event)
					.message(MESSAGE_MONTANT_NEG)
					.build()
					.apply();
			return false;
		}
		if(source.getInventory() - montant < 0) {
			ReplyAction.builder().event(event).message(String.format(MESSAGE_ISSUFISANT_INV, getName(victime))).build().apply();
			return false;
		}
		return true;
	}
}
