package com.sylvain.jdr.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

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
	ADMINCHECK ("admincheck");

	@Getter
	String name;
}
