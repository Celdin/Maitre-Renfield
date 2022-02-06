package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class CheckAction extends Action {
	private final static String TITRE = "Fonds de %s";

	Player source;

	@Builder
	public CheckAction(GenericCommandInteractionEvent event) {
		super(event);
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getUser().getId());
		MessageEmbed embed = getEmbedBuilder(source);

		event.replyEmbeds(embed).queue();
	}

	@NotNull
	public MessageEmbed getEmbedBuilder(Player player) {
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setTitle(String.format(TITRE, getName(player.getUid())));
		embedBuilder.setThumbnail(getProfilePicture(player));
		final MessageEmbed.Field banqueField = new MessageEmbed.Field(Comptes.BANQUE.name(), player.getBank() + "€", false);
		final MessageEmbed.Field inventaireField = new MessageEmbed.Field(Comptes.INVENTAIRE.name(), player.getInventory() + "€", false);
		final MessageEmbed.Field incomeBnkFIeld = new MessageEmbed.Field("Revenus[" + Comptes.BANQUE.name() + "]", player.getIncomeBank() + "€/mois", false);
		final MessageEmbed.Field incomeInvFIeld = new MessageEmbed.Field("Revenus[" + Comptes.INVENTAIRE.name() + "]", player.getIncomeInv() + "€/mois", false);

		embedBuilder.addField(inventaireField);
		embedBuilder.addField(banqueField);
		embedBuilder.addField(incomeInvFIeld);
		embedBuilder.addField(incomeBnkFIeld);
		return embedBuilder.build();
	}

	private String getProfilePicture(Player player) {
		User user = event.getJDA().getUserById(player.getUid());
		if(user == null) {
			user = event.getJDA().retrieveUserById(player.getUid()).complete();
		}
		String avatarUrl = user.getAvatarUrl();
		if(avatarUrl == null)
			avatarUrl = user.getDefaultAvatarUrl();
		return avatarUrl;
	}

	@Override
	public boolean validate() {
		return true;
	}
}
