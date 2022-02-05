package com.sylvain.jdr.data.dto.impl;

import com.sylvain.jdr.data.dto.DataObject;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class Player extends DataObject {
	String uid;
	Long bank;
	Long inventory;
	Long income;


	public final static String COLUMN_UID = "UID";
	public final static String COLUMN_BANK = "BANK";
	public final static String COLUMN_INVENTORY = "INVENTORY";
	public final static String COLUMN_INCOME = "INCOME";
	public final static String COLUMN_UID_TYPE = "TEXT";
	public final static String COLUMN_BANK_TYPE = "BIGINT";
	public final static String COLUMN_INVENTORY_TYPE = "BIGINT";
	public final static String COLUMN_INCOME_TYPE = "BIGINT";

	@Override
	public String getTableName() {
		return "PLAYER";
	}

	@Override
	public Object get(String columnName) {
		Object val = super.get(columnName);
		if(val != null)
			return val;
		switch (columnName) {
		case COLUMN_UID:
			return uid;
		case COLUMN_BANK:
			return bank;
		case COLUMN_INVENTORY:
			return inventory;
		case COLUMN_INCOME:
			return income;
		default:
			return null;
		}
	}

	@Override
	public void set(Object object, String columnName) {
		super.set(object, columnName);
		switch (columnName) {
		case COLUMN_UID:
			uid = (String) object;
			break;
		case COLUMN_BANK:
			bank = (Long) object;
			break;
		case COLUMN_INVENTORY:
			inventory = (Long) object;
			break;
		case COLUMN_INCOME:
			income = (Long) object;
			break;
		}
	}

	@Override
	public List<String> getColumnNames() {
		final List<String> columnNames = super.getColumnNames();
		columnNames.addAll(Arrays.asList(COLUMN_UID, COLUMN_BANK, COLUMN_INVENTORY, COLUMN_INCOME));
		return columnNames;
	}

	@Override
	public Map<String, String> getColumnTypes() {
		final Map<String, String> columnTypes = super.getColumnTypes();
		columnTypes.put(COLUMN_UID,COLUMN_UID_TYPE);
		columnTypes.put(COLUMN_BANK,COLUMN_BANK_TYPE);
		columnTypes.put(COLUMN_INVENTORY,COLUMN_INVENTORY_TYPE);
		columnTypes.put(COLUMN_INCOME,COLUMN_INCOME_TYPE);
		return columnTypes;
	}

	@Override
	public int compareTo(@NotNull DataObject o) {
		return uid.compareTo(((Player)o).getUid());
	}
}
