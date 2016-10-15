package com.edianjucai.eshop.dao;


import com.edianjucai.eshop.common.DbManager;
import com.edianjucai.eshop.model.entity.LocalUser;

import java.util.List;

public class LocalUserModelDao
{

	private static LocalUserModelDao mDao = null;

	private LocalUserModelDao() {

	}

	public static synchronized LocalUserModelDao getInstance() {
		if (mDao == null) {
			mDao = new LocalUserModelDao();
		}
		return mDao;
	}

	public boolean saveModel(LocalUser model) {
		if (model != null) {
            LocalUser cloneModel = model.deepClone();
			if (cloneModel != null) {
				DbManager.getFinalDb().deleteAll(LocalUser.class);
				cloneModel.encryptModel();
				DbManager.getFinalDb().save(cloneModel);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public LocalUser getModel() {
        List<LocalUser> listModel = DbManager.getFinalDb().findAll(LocalUser.class);
		if (listModel != null && listModel.size() == 1) {
            LocalUser model = listModel.get(0);
			model.decryptModel();
			return model;
		} else {
			return null;
		}
	}

	public boolean deleteAllModel() {
		try {
			DbManager.getFinalDb().deleteAll(LocalUser.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
