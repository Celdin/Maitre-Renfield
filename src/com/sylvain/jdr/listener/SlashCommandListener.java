package com.sylvain.jdr.listener;

import com.sylvain.jdr.action.impl.*;
import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.SlashCommand;
import net.dv8tion.jda.api.entities.GuildMessageChannel;
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
		final OptionMapping channelOptionMapping = event.getOption("channel");
		Comptes compte = null;
		User destinataire = null;
		User cible = null;
		Long montant = null;
		String motif = null;
		GuildMessageChannel channel = null;
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
		if(channelOptionMapping != null)
			channel = channelOptionMapping.getAsMessageChannel();
		System.out.println(" ** " + SlashCommand.findByName(event.getName()) + " ** ");
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
					.cible(cible)
					.build()
					.apply();
			break;
		case PAY:
			PayAction.builder()
					.event(event)
					.compte(compte)
					.montant(montant)
					.motif(motif)
					.build()
					.apply();
			break;
		case ADD:
			AddAction.builder()
					.event(event)
					.compte(compte)
					.destinataire(destinataire)
					.montant(montant)
					.build()
					.apply();
			break;
		case SUB:
			SubAction.builder()
					.event(event)
					.compte(compte)
					.destinataire(destinataire)
					.montant(montant)
					.build()
					.apply();
			break;
		case STEAL:
			StealAction.builder()
					.event(event)
					.destinataire(destinataire)
					.victime(cible)
					.montant(montant)
					.build()
					.apply();
			break;
		case HACK:
			HackAction.builder()
					.event(event)
					.destinataire(destinataire)
					.victime(cible)
					.montant(montant)
					.build()
					.apply();
			break;
		case INCOME:
			IncomeAction.builder()
					.event(event)
					.comptes(compte)
					.destinataire(destinataire)
					.montant(montant)
					.build()
					.apply();
			break;
		case ADMINCHECK:
			AdminCheckAction.builder()
					.event(event)
					.build()
					.apply();
			break;
		case LINK:
			LinkAction.builder()
					.event(event)
					.destinataire(destinataire)
					.channel(channel)
					.build()
					.apply();
			break;
		case DEPOSIT:
			DepositAction.builder()
					.event(event)
					.montant(montant)
					.build()
					.apply();
			break;
		case WITHDRAW:
			WithdrawAction.builder()
					.event(event)
					.montant(montant)
					.build()
					.apply();
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
