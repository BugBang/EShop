package com.edianjucai.eshop.model.entity;

public class BaseActModel
{
	protected String act = null;
	protected String act_2 = null;
	protected int response_code = -999;
	protected String show_err = null;
	protected int user_login_status = 0;
	protected String app_url = null;
	protected int status = 0;
	protected String paypassword = null;
	protected String ips_money_format = null;
	protected String img = null;
	protected String conter = null;
	protected String title = null;
	protected String furl = null;
	

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getConter() {
		return conter;
	}

	public void setConter(String conter) {
		this.conter = conter;
	}

	public String getIps_money_format() {
		return ips_money_format;
	}

	public void setIps_money_format(String ips_money_format) {
		this.ips_money_format = ips_money_format;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public String getApp_url(){
		return app_url;
	}

	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int _status){
		this.status=_status;
	}

	public int getUser_login_status()
	{
		return user_login_status;
	}

	public void setUser_login_status(int user_login_status)
	{
		this.user_login_status = user_login_status;
	}

	public String getShow_err()
	{
		return show_err;
	}

	public void setShow_err(String show_err)
	{
		this.show_err = show_err;
	}

	public int getResponse_code()
	{
		return response_code;
	}

	public void setResponse_code(int response_code)
	{
		this.response_code = response_code;
	}

	public String getAct()
	{
		return act;
	}

	public void setAct(String act)
	{
		this.act = act;
	}

	public String getAct_2()
	{
		return act_2;
	}

	public void setAct_2(String act_2)
	{
		this.act_2 = act_2;
	}

}
