package com.edianjucai.eshop.model.entity;


import com.edianjucai.eshop.constant.Constant;

public class RequestModel
{
	private int mRequestDataType = Constant.RequestDataType.REQUEST;
	private int mResponseDataType = Constant.ResponseDataType.JSON;
	private Object mData = null;

	private boolean isNeedCache = true;

	public RequestModel()
	{
	}

	public RequestModel(Object data)
	{
		this.mData = data;
	}

	public Object getmData()
	{
		return mData;
	}

	public void setmData(Object mData)
	{
		this.mData = mData;
	}

	public int getmRequestDataType()
	{
		return mRequestDataType;
	}

	public void setmRequestDataType(int mRequestDataType)
	{
		this.mRequestDataType = mRequestDataType;
	}

	public int getmResponseDataType()
	{
		return mResponseDataType;
	}

	public void setmResponseDataType(int mResponseDataType)
	{
		this.mResponseDataType = mResponseDataType;
	}

	public boolean isNeedCache()
	{
		return isNeedCache;
	}

	public void setNeedCache(boolean isNeedCache)
	{
		this.isNeedCache = isNeedCache;
	}

}
