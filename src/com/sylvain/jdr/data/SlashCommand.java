package com.sylvain.jdr.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public enum SlashCommand {
	TRANSFER ("transfer"),
	CHECK ("check"),
	PAY ("pay"),
	ADD ("add"),
	SUB ("sub"),
	STEAL ("steal"),
	HACK ("hack"),
	INCOME ("income"),
	ADMINCHECK ("admincheck"),
	LINK ("link");

	@Getter
	String name;

	public static SlashCommand findByName(String name) {
		final Optional<SlashCommand> first = Arrays.stream(SlashCommand.values()).filter(slashCommand -> slashCommand.getName().equals(name)).findFirst();
		return first.orElseGet(() -> SlashCommand.valueOf(name));
	}
}
