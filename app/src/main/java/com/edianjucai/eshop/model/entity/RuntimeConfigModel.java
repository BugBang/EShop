package com.edianjucai.eshop.model.entity;


import com.edianjucai.eshop.ui.activity.WebViewActivity;

public class RuntimeConfigModel
{

	private boolean isMainActivityStarted = false;
	private WebViewActivity projectDetailWebviewActivity = null;

    public WebViewActivity getProjectDetailWebviewActivity() {
        return projectDetailWebviewActivity;
    }

    public void setProjectDetailWebviewActivity(WebViewActivity projectDetailWebviewActivity) {
        this.projectDetailWebviewActivity = projectDetailWebviewActivity;
    }

    public boolean isMainActivityStarted()
	{
		return isMainActivityStarted;
	}

	public void setMainActivityStarted(boolean isMainActivityStarted)
	{
		this.isMainActivityStarted = isMainActivityStarted;
	}
	
	
	
}
