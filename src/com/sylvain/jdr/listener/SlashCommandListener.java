package com.sylvain.jdr.listener;

import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.SlashCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class SlashCommandListener extends ListenerAdapter {

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		final OptionMapping compteOptionMapping = event.getOption("compte");
		final OptionMapping cibleOptionMapping = event.getOption("cible");
		final OptionMapping destinataireOptionMapping = event.getOption("destinataire");
		final OptionMapping montantOptionMapping = event.getOption("montant");
		final OptionMapping motifOptionMapping = event.getOption("motif");
		Comptes compte = null;
		User destinataire = null;
		User cible = null;
		Long montant = null;
		String motif = null;
		if(compteOptionMapping != null)
			compte = Comptes.valueOf(compteOptionMapping.getAsString());
		if(cibleOptionMapping != null)
			cible = cibleOptionMapping.getAsUser();
		if(destinataireOptionMapping != null)
			destinataire = destinataireOptionMapping.getAsUser();
		if(montantOptionMapping != null)
			montant = montantOptionMapping.getAsLong();
		if(motifOptionMapping != null)
			motif = motifOptionMapping.getAsString();

		if (SlashCommand.TRANSFER.getName().equals(event.getName())) {
			say(event, String.format("Donner %d de %s a %s", montant, compte, destinataire.getName()));
		} else {
			say(event, "Vous avez fait la commande " + event.getName());
		}
	}

	public void say (SlashCommandInteractionEvent event, String content)
	{
		event.reply(content).queue(); // This requires no permissions!
	}
}
