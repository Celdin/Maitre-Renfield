package com.sylvain.jdr.action.impl;

import com.sylvain.jdr.action.Action;
import com.sylvain.jdr.data.dto.impl.Player;
import com.sylvain.jdr.query.impl.PlayerQuery;
import lombok.Builder;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class WithdrawAction extends Action {
	private final static String MESSAGE_OK = "Vous effectuez un retrait de %s€";
	private final static String MESSAGE_MONTANT_NEG = "Indiquer un montant suppérieur à zero.";
	Long montant = null;

	Player source;

	@Builder
	public WithdrawAction(GenericCommandInteractionEvent event, Long montant) {
		super(event);
		this.montant = montant;
	}

	@Override
	public void apply() {
		PlayerQuery playerQuery = new PlayerQuery();
		source = playerQuery.getById(event.getUser().getId());
		if(!validate())
			return;
		source.setBank(source.getBank() - montant);
		source.setInventory(source.getInventory() + montant);
		ReplyAction.builder()
				.event(event)
				.message(String.format(MESSAGE_OK, montant))
				.build()
				.apply();

	}

	@Override
	public boolean validate() {
		if (montant <= 0) {
			ReplyAction.builder()
					.event(event)
					.message(MESSAGE_MONTANT_NEG)
					.build()
					.apply();
			return false;
		}
		return true;
	}
}
