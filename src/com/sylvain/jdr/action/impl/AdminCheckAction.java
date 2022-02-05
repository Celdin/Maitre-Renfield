package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

import java.sql.SQLException;
import java.util.List;

public class AdminCheckAction extends Action {
	private final static String TITRE = "Fonds de %s";

	@Builder
	public AdminCheckAction(GenericCommandInteractionEvent event) {
		super(event);
	}

	@Override
	public void apply() {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		PlayerQuery playerQuery = new PlayerQuery();
		try {
			final List<Player> all = playerQuery.getAll();
			embedBuilder.setTitle("Admin check");
			for(Player player : all) {
				final MessageEmbed.Field joueurField = new MessageEmbed.Field("Joueur", getName(player.getUid()), false);
				final MessageEmbed.Field inventaireField = new MessageEmbed.Field(Comptes.INVENTAIRE.name(), player.getInventory() + "€", true);
				final MessageEmbed.Field banqueField = new MessageEmbed.Field(Comptes.BANQUE.name(), player.getBank() + "€", true);
				final MessageEmbed.Field incomeFIeld = new MessageEmbed.Field("Revenus", player.getIncome() + "€/mois", true);

				embedBuilder.addField(joueurField);
				embedBuilder.addField(inventaireField);
				embedBuilder.addField(banqueField);
				embedBuilder.addField(incomeFIeld);
				embedBuilder.addBlankField(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		event.replyEmbeds(embedBuilder.build()).queue();
	}

	@Override
	public boolean validate() {
		return true;
	}

}
