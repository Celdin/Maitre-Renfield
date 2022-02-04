package com.sylvain.jdr;

import com.sylvain.jdr.data.Comptes;
import com.sylvain.jdr.data.SlashCommand;
import com.sylvain.jdr.driver.PostgreSQLDriver;
import com.sylvain.jdr.listener.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.EnumSet;

public class MaitreRenfield {

	public static void main(String[] args) throws LoginException, SQLException {
		JDA jda = JDABuilder.createLight(args[0], EnumSet.noneOf(GatewayIntent.class)) // slash commands don't need any intents
			.addEventListeners(new SlashCommandListener())
			.build();
		if(args.length > 1 ) {
			PostgreSQLDriver.initialise(args[1], args[2], args[3]);
			PostgreSQLDriver.ckeckDatabase();
		} else {
			PostgreSQLDriver.initialise(null, null, null);
			PostgreSQLDriver.ckeckDatabase();
		}
		jda.updateCommands()
				.addCommands(Commands.slash(SlashCommand.TRANSFER.getName(), "Transfère de l'argent à un autre joueur.")
						.addOptions(new OptionData(OptionType.STRING, "compte", "Compte à utiliser")
								.addChoice("Compte bancaire", Comptes.BANQUE.name())
								.addChoice("Inventaire", Comptes.INVENTAIRE.name())
								.setRequired(true))
						.addOptions(new OptionData(OptionType.USER, "destinataire", "Le destinataire")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true)))
				.addCommands(Commands.slash(SlashCommand.CHECK.getName(), "Consulter son propre compte en banque, son propre inventaire et son revenu mensuel"))
				.addCommands(Commands.slash(SlashCommand.PAY.getName(), "Dépenser son argent")
						.addOptions(new OptionData(OptionType.STRING, "compte", "Compte à utiliser")
								.addChoice("Compte bancaire", Comptes.BANQUE.name())
								.addChoice("Inventaire", Comptes.INVENTAIRE.name())
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.STRING, "motif", "Le motif du paiement")))
				.addCommands(Commands.slash(SlashCommand.ADD.getName(), "Ajouter de l'argent à un joueur.")
						.addOptions(new OptionData(OptionType.STRING, "compte", "Compte à utiliser")
								.addChoice("Compte bancaire", Comptes.BANQUE.name())
								.addChoice("Inventaire", Comptes.INVENTAIRE.name())
								.setRequired(true))
						.addOptions(new OptionData(OptionType.USER, "destinataire", "Le destinataire")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true)))
				.addCommands(Commands.slash(SlashCommand.SUB.getName(), "Retirer de l'argent à un joueur.")
						.addOptions(new OptionData(OptionType.STRING, "compte", "Compte à utiliser")
								.addChoice("Compte bancaire", Comptes.BANQUE.name())
								.addChoice("Inventaire", Comptes.INVENTAIRE.name())
								.setRequired(true))
						.addOptions(new OptionData(OptionType.USER, "destinataire", "Le destinataire")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true)))
				.addCommands(Commands.slash(SlashCommand.STEAL.getName(), "Voler de l'inventaire cible au destinataire")
						.addOptions(new OptionData(OptionType.USER, "cible", "La cible du vol")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.USER, "destinataire", "Le destinataire")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true)))
				.addCommands(Commands.slash(SlashCommand.HACK.getName(), "Voler du compte en banque cible au destinataire")
						.addOptions(new OptionData(OptionType.USER, "cible", "La cible du vol")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.USER, "destinataire", "Le destinataire")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true)))
				.addCommands(Commands.slash(SlashCommand.INCOME.getName(), "Programme la rentrée d'argent d'un joueur le 1er de chaque mois.")
						.addOptions(new OptionData(OptionType.STRING, "compte", "Compte à utiliser")
								.addChoice("Compte bancaire", Comptes.BANQUE.name())
								.addChoice("Inventaire", Comptes.INVENTAIRE.name())
								.setRequired(true))
						.addOptions(new OptionData(OptionType.USER, "destinataire", "Le destinataire")
								.setRequired(true))
						.addOptions(new OptionData(OptionType.INTEGER, "montant", "Le montant")
								.setRequired(true)))
				.addCommands(Commands.slash(SlashCommand.ADMINCHECK.getName(), "Afficher un récapitulatif de l'ensemble des comptes et revenu des joueurs."))
				.queue();
	}
}