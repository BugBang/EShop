package com.edianjucai.eshop.dao;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.common.DbManager;
import com.edianjucai.eshop.model.entity.InitDBModel;
import com.edianjucai.eshop.model.entity.InitModel;

import java.util.List;

/**
 * Created by user on 2016-09-26.
 */
public class InitModelDao {
    public static void saveInitModel(String content) {
        InitDBModel dbinit = new InitDBModel();
        // String encryptStr = AESUtil.encrypt(content,
        // ApkConstant.DEFAULT_AES_KEY);
        // dbinit.setInitActContent(encryptStr);
        dbinit.setInitContent(content);
        DbManager.getFinalDb().deleteAll(InitDBModel.class);
        DbManager.getFinalDb().save(dbinit);
    }

    public static synchronized InitModel readInitDB() {
        List<InitDBModel> list = DbManager.getFinalDb().findAll(InitDBModel.class);
        if (list.size() > 0) {
            String dBcontent = list.get(0).getInitContent();

            return JSON.parseObject(dBcontent, InitModel.class);
        }
        return null;
    }

    public static synchronized String getSite_domain() {
        List<InitDBModel> list = DbManager.getFinalDb().findAll(InitDBModel.class);
        if (list.size() > 0) {
            String dBcontent = list.get(0).getInitContent();
            return JSON.parseObject(dBcontent, InitModel.class).getSite_domain();
        }
        return null;
    }
}
