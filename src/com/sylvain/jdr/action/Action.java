package com.sylvain.jdr.action;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;
@AllArgsConstructor
public abstract class Action {
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
}
