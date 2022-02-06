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
	Long incomeBank;
	Long incomeInv;
	String channel;


	public final static String TABLE_NAME = "PLAYER";

	public final static String COLUMN_UID = "UID";
	public final static String COLUMN_CHANNEL = "CHANNEL";
	public final static String COLUMN_BANK = "BANK";
	public final static String COLUMN_INVENTORY = "INVENTORY";
	public final static String COLUMN_INCOME_BN = "INCOME_BNK";
	public final static String COLUMN_INCOME_INV = "INCOME_INV";

	public final static String COLUMN_UID_TYPE = "TEXT";
	public final static String COLUMN_CHANNEL_TYPE = "TEXT";
	public final static String COLUMN_BANK_TYPE = "BIGINT";
	public final static String COLUMN_INVENTORY_TYPE = "BIGINT";
	public final static String COLUMN_INCOME_BN_TYPE = "BIGINT";
	public final static String COLUMN_INCOME_INV_TYPE = "BIGINT";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Object get(String columnName) {
		Object val = super.get(columnName);
		if(val != null)
			return val;
		switch (columnName) {
		case COLUMN_UID:
			return uid;
		case COLUMN_CHANNEL:
			return channel;
		case COLUMN_BANK:
			return bank;
		case COLUMN_INVENTORY:
			return inventory;
		case COLUMN_INCOME_BN:
			return incomeBank;
		case COLUMN_INCOME_INV:
			return incomeInv;
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
		case COLUMN_CHANNEL:
			channel = (String) object;
			break;
		case COLUMN_BANK:
			bank = (Long) object;
			break;
		case COLUMN_INVENTORY:
			inventory = (Long) object;
			break;
		case COLUMN_INCOME_BN:
			incomeBank = (Long) object;
			break;
		case COLUMN_INCOME_INV:
			incomeInv = (Long) object;
			break;
		}
	}

	@Override
	public List<String> getColumnNames() {
		final List<String> columnNames = super.getColumnNames();
		columnNames.addAll(Arrays.asList(COLUMN_UID, COLUMN_CHANNEL, COLUMN_BANK, COLUMN_INVENTORY, COLUMN_INCOME_BN, COLUMN_INCOME_INV));
		return columnNames;
	}

	@Override
	public Map<String, String> getColumnTypes() {
		final Map<String, String> columnTypes = super.getColumnTypes();
		columnTypes.put(COLUMN_UID,COLUMN_UID_TYPE);
		columnTypes.put(COLUMN_CHANNEL, COLUMN_CHANNEL_TYPE);
		columnTypes.put(COLUMN_BANK,COLUMN_BANK_TYPE);
		columnTypes.put(COLUMN_INVENTORY,COLUMN_INVENTORY_TYPE);
		columnTypes.put(COLUMN_INCOME_BN,COLUMN_INCOME_BN_TYPE);
		columnTypes.put(COLUMN_INCOME_INV,COLUMN_INCOME_INV_TYPE);
		return columnTypes;
	}

	@Override
	public int compareTo(@NotNull DataObject o) {
		return uid.compareTo(((Player)o).getUid());
	}
}
