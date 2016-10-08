package com.edianjucai.eshop.model.entity;

import java.util.List;

public class InitModel extends BaseActModel {
    private String kf_phone;
    private String kf_email;
    private int about_info;
    private int version;
    private String site_domain;

    public String getSite_domain() {
        return site_domain;
    }

    public void setSite_domain(String site_domain) {
        this.site_domain = site_domain;
    }

    private List<AdvsModel> advs;

    private List<CateListModel> cate_list;

    public String getKf_phone() {
        return kf_phone;
    }

    public void setKf_phone(String kf_phone) {
        this.kf_phone = kf_phone;
    }

    public String getKf_email() {
        return kf_email;
    }

    public void setKf_email(String kf_email) {
        this.kf_email = kf_email;
    }

    public int getAbout_info() {
        return about_info;
    }

    public void setAbout_info(int about_info) {
        this.about_info = about_info;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<AdvsModel> getAdvs() {
        return advs;
    }

    public void setAdvs(List<AdvsModel> advs) {
        this.advs = advs;
    }

    public List<CateListModel> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<CateListModel> cate_list) {
        this.cate_list = cate_list;
    }

    public static class AdvsModel {
        private String id;
        private String title;
        private String url;
        private String img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public static class CateListModel {
        private String id;
        private String name;
        private String ico;
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

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }
    }
}
