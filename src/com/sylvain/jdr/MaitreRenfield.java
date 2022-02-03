package com.sylvain.jdr;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import javax.security.auth.login.LoginException;

public class MaitreRenfield implements EventListener {
	public static void main(String[] args) throws LoginException
	{
		JDA jda = JDABuilder.createDefault(args[0]).build();
	}

	@Override
	public void onEvent(GenericEvent event)
	{
		if(event instanceof ReadyEvent)
			System.out.println("API is ready!");
	}
}
