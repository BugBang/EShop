package com.edianjucai.eshop.model.entity;

import java.util.List;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyListModel {

    private List<CompanyListModelDeal> list;

    public List<CompanyListModelDeal> getList() {
        return list;
    }

    public void setList(List<CompanyListModelDeal> list) {
        this.list = list;
    }

    public static class CompanyListModelDeal {
        private String id;
        private String name;
        private String ico;
        private String address;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
