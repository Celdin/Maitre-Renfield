package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

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
		embedBuilder.setColor(getRoleColor(player.getUid()));
		final MessageEmbed.Field banqueField = new MessageEmbed.Field(":credit_card: " + Comptes.BANQUE.name(), player.getBank() + "€", false);
		final MessageEmbed.Field inventaireField = new MessageEmbed.Field(":moneybag: " + Comptes.INVENTAIRE.name(), player.getInventory() + "€", false);
		final MessageEmbed.Field incomeBnkFIeld = new MessageEmbed.Field(":inbox_tray: Revenus [" + Comptes.BANQUE.name() + "]", player.getIncomeBank() + "€/mois", false);
		final MessageEmbed.Field incomeInvFIeld = new MessageEmbed.Field(":inbox_tray: Revenus [" + Comptes.INVENTAIRE.name() + "]", player.getIncomeInv() + "€/mois", false);

		embedBuilder.addField(inventaireField);
		embedBuilder.addField(banqueField);
		embedBuilder.addField(incomeInvFIeld);
		embedBuilder.addField(incomeBnkFIeld);
		return embedBuilder.build();
	}

	protected Color getRoleColor(String id) {
		User userById;
		userById = event.getJDA().getUserById(id);
		if(userById == null)
			userById = event.getJDA().retrieveUserById(id).complete();
		if(userById!= null) {
			final Guild guild = event.getGuild();
			if (guild!=null) {
				Member member;
				member = guild.getMember(userById);
				if(member == null)
					member = guild.retrieveMember(userById).complete();
				if(member != null) {
					final List<Role> roles = member.getRoles();
					if(!roles.isEmpty()) {
						return roles.get(0).getColor();
					}
				}
			}
		}
		return Color.DARK_GRAY;
	}

	@Override
	public boolean validate() {
		return true;
	}
}
