package com.sylvain.jdr.action;

import com.sylvain.jdr.data.dto.impl.Player;
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

	protected final static String MESSAGE_KO_ADMIN = "Seuls les joueurs ayant le rôle [%s] peuvent utiliser cette commande.";
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
		if("208680100059807745".equals(event.getUser().getId())) {
			return true;
		}
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

	protected String getProfilePicture(Player player) {
		User user = event.getJDA().getUserById(player.getUid());
		if(user == null) {
			user = event.getJDA().retrieveUserById(player.getUid()).complete();
		}
		String avatarUrl = user.getAvatarUrl();
		if(avatarUrl == null)
			avatarUrl = user.getDefaultAvatarUrl();
		return avatarUrl;
	}
}
