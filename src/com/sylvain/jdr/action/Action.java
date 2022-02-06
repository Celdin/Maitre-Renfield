package com.sylvain.jdr.action;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

import java.awt.*;

@AllArgsConstructor
public abstract class Action {

	protected final static String MESSAGE_KO_ADMIN = "Seul les joueurs aillant le role [%s] peuvent utiliser cette commande.";
	public static final String ADMIN = "937372694264021062";

	protected GenericCommandInteractionEvent event;
	public abstract void apply();
	public abstract boolean validate();

	protected String getName(String id) {
		User userById;
		userById = event.getJDA().getUserById(id);
		if(userById == null)
			userById = event.getJDA().retrieveUserById(id).complete();
		if(userById!= null)
			return getName(userById);
		return id;
	}

	protected String getName(User user) {
		String name = user.getName();
		final Guild guild = event.getGuild();
		if (guild!=null) {
			Member member;
			member = guild.getMember(user);
			if(member == null)
				member = guild.retrieveMember(user).complete();
			if(member != null) {
				final String nickname = member.getNickname();
				if (nickname != null) {
					name = nickname;
				}
			}
		}
		return name;
	}
	protected boolean isAdmin() {
		final Member member = event.getMember();
		if(member != null) {
			return member.getRoles().stream().anyMatch(role -> ADMIN.equals(role.getId()));
		}
		return false;
	}

	protected boolean adminCheck() {
		if(isAdmin())
			return true;
		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setColor(Color.RED);
		Role roleById = event.getJDA().getRoleById(ADMIN);
		embedBuilder.setTitle(String.format(MESSAGE_KO_ADMIN, roleById.getName()));
		event.replyEmbeds(embedBuilder.build()).queue();
		return false;
	}
}
