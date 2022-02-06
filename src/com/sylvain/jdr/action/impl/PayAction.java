package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class PayAction extends Action {
	private final static String MESSAGE_OK = "Vous payez %s€ [%s]";
	private final static String MESSAGE_NOTIF = "%s a payé %s€ [%s]";
	private final static String MESSAGE_MONTANT_NEG = "Indiquer un montant suppérieur à zero.";
	private final static String MESSAGE_ISSUFISANT_BANQUE = "Vous avez pas suffisament de fond sur votre compte en baque.";
	private final static String MESSAGE_ISSUFISANT_INV = "Vous avez pas suffisament de fond dans votre inventaire.";


	Comptes compte = null;
	Long montant = null;
	String motif = null;

	Player source;

	@Builder
	public PayAction(GenericCommandInteractionEvent event, Comptes compte, Long montant, String motif) {
		super(event);
		this.compte = compte;
		this.montant = montant;
		this.motif = motif;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getUser().getId());
		if(!validate())
			return;
		switch (compte) {
		case BANQUE:
			source.setBank(source.getBank() - montant );
			break;
		case INVENTAIRE:
			source.setInventory(source.getInventory() - montant );
			break;
		}
		playerQuery.save(source);
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle(String.format(MESSAGE_NOTIF, getName(event.getUser()), montant, compte));
		embedBuilder.setDescription(motif);
		embedBuilder.setThumbnail(getProfilePicture(source));
		TextChannel textChannelById = event.getJDA().getTextChannelById("938779944547389491");
		if (textChannelById != null) {
			textChannelById.sendMessageEmbeds(embedBuilder.build()).queue();
		}

		String format = String.format(MESSAGE_OK, montant, compte);
		format = motif!=null?format + " (" + motif + ").":format + ".";
		ReplyAction.builder()
				.event(event)
				.message(format)
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
