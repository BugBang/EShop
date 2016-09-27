package com.edianjucai.eshop.model.entity;

import net.tsz.afinal.database.annotation.sqlite.Id;

/**
 * Created by user on 2016-09-26.
 */
public class InitDBModel {
    @Id
    private int id;
    private String initContent;

    public String getInitContent() {
        return initContent;
    }

    public void setInitContent(String initContent) {
        this.initContent = initContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
