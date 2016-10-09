package com.edianjucai.eshop.model.entity;

import java.util.List;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyListModel {

    private List<CompanyListModelDeal> list;

    private List<CompanyListBannerModel> deal_cates;

    public List<CompanyListBannerModel> getDeal_cates() {
        return deal_cates;
    }

    public void setDeal_cates(List<CompanyListBannerModel> deal_cates) {
        this.deal_cates = deal_cates;
    }

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

    public static class CompanyListBannerModel {
        private String id;
        private String name;
        private String banner;

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

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }
}
