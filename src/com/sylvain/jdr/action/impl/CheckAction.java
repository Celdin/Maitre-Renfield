package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class CheckAction extends Action {
	private final static String TITRE = "Fonds de %d";

	GenericCommandInteractionEvent event;

	Player source;

	@Builder
	public CheckAction(GenericCommandInteractionEvent event) {
		super(event);
	}

	@Override
	public void apply() {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getMember().getId());
		embedBuilder.setTitle(String.format(TITRE, event.getMember().getNickname()));
		final MessageEmbed.Field inventaireField = new MessageEmbed.Field(Comptes.INVENTAIRE.name(), source.getInventory() + "€", false);
		final MessageEmbed.Field banqueField = new MessageEmbed.Field(Comptes.BANQUE.name(), source.getBank() + "€", false);
		final MessageEmbed.Field incomeFIeld = new MessageEmbed.Field("Revenus", source.getIncome() + "€/mois", false);

		embedBuilder.addField(inventaireField);
		embedBuilder.addField(banqueField);
		embedBuilder.addField(incomeFIeld);


		event.replyEmbeds(embedBuilder.build()).queue();
	}

	@Override
	public boolean validate() {
		return true;
	}
}
