package com.edianjucai.eshop.common;

import android.database.sqlite.SQLiteDatabase;

import com.edianjucai.eshop.app.App;

import net.tsz.afinal.database.FinalDb;
import net.tsz.afinal.database.FinalDb.DbUpdateListener;

public class DbManager
{
	private static final String targetDirectory = null;
	private static final String dbName = "edian.db";
	private static final boolean isDebug = true;
	
	private static final int dbVersion = 1;

	private static FinalDb mFinalDb = null;

	private DbManager()
	{

	}

	public synchronized static FinalDb getFinalDb()
	{
		if (mFinalDb == null)
		{
			mFinalDb = FinalDb.create(App.getApplication(), targetDirectory, dbName, isDebug, dbVersion, new YPDbUpdateListener());
		}
		return mFinalDb;
	}

	private static class YPDbUpdateListener implements DbUpdateListener
	{

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{

		}
	}

}
