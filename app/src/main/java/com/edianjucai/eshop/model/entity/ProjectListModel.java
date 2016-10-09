package com.edianjucai.eshop.model.entity;

import java.util.List;

/**
 * Created by user on 2016-09-23.
 */
public class ProjectListModel {

    private int response_code;
    private List<AdvsBean> advs;
    private List<CateListBean> cate_list;

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<AdvsBean> getAdvs() {
        return advs;
    }

    public void setAdvs(List<AdvsBean> advs) {
        this.advs = advs;
    }

    public List<CateListBean> getCate_list() {
        return cate_list;
    }

    public void setCate_list(List<CateListBean> cate_list) {
        this.cate_list = cate_list;
    }

    public static class AdvsBean {
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

    public static class CateListBean {
        private String id;
        private String name;
        private String ico;

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
    }
}
