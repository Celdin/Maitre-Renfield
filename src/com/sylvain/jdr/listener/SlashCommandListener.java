package com.sylvain.jdr.listener;

import com.sylvain.jdr.action.impl.CheckAction;
import com.sylvain.jdr.action.impl.ReplyAction;
import com.sylvain.jdr.action.impl.TransferAction;
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

		switch (SlashCommand.findByName(event.getName())) {
		case TRANSFER:
			TransferAction.builder()
					.event(event)
					.compte(compte)
					.destinataire(destinataire)
					.montant(montant)
					.build()
					.apply();
			break;
		case CHECK:
			CheckAction.builder()
					.event(event)
					.build()
					.apply();
			break;
		case PAY:
			break;
		case ADD:
			break;
		case SUB:
			break;
		case STEAL:
			break;
		case HACK:
			break;
		case INCOME:
			break;
		case ADMINCHECK:
			break;
		default:
			ReplyAction.builder()
					.event(event)
					.message("Pas d'action")
					.build()
					.apply();
			break;
		}
	}
}
