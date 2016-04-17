package net.binarysailor.shoppinglist.common;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseOperations {
	Object execute(SQLiteDatabase db);
}
