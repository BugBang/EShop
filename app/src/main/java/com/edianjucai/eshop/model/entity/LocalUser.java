package com.edianjucai.eshop.model.entity;

import com.edianjucai.eshop.util.AESUtil;

import net.tsz.afinal.database.annotation.sqlite.Id;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by user on 2016-09-13.
 */
public class LocalUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int _id;

    private String id = null;
    private String userName = null;
    private String userPassword = null;
    private String userMoney = null;
    private String userMoneyFormat = null;
    private String mobile = null;

    private String paypassword = null;



    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getUserMoney()
    {
        return userMoney;
    }

    public void setUserMoney(String userMoney)
    {
        this.userMoney = userMoney;
    }

    public String getUserMoneyFormat()
    {
        return userMoneyFormat;
    }

    public void setUserMoneyFormat(String userMoneyFormat)
    {
        this.userMoneyFormat = userMoneyFormat;
    }

    public LocalUser deepClone()
    {
        try
        {
            // 将对象写到流里
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(this);
            // 从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (LocalUser) (oi.readObject());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void encryptModel()
    {
        if (this.id != null)
        {
            this.id = AESUtil.encrypt(this.id);
        }
        if (this.userMoney != null)
        {
            this.userMoney = AESUtil.encrypt(this.userMoney);
        }
        if (this.userMoneyFormat != null)
        {
            this.userMoneyFormat = AESUtil.encrypt(this.userMoneyFormat);
        }
        if (this.userName != null)
        {
            this.userName = AESUtil.encrypt(this.userName);
        }
        if (this.userPassword != null)
        {
            this.userPassword = AESUtil.encrypt(this.userPassword);
        }

    }

    public void decryptModel()
    {
        if (this.id != null)
        {
            this.id = AESUtil.decrypt(this.id);
        }
        if (this.userMoney != null)
        {
            this.userMoney = AESUtil.decrypt(this.userMoney);
        }
        if (this.userMoneyFormat != null)
        {
            this.userMoneyFormat = AESUtil.decrypt(this.userMoneyFormat);
        }
        if (this.userName != null)
        {
            this.userName = AESUtil.decrypt(this.userName);
        }
        if (this.userPassword != null)
        {
            this.userPassword = AESUtil.decrypt(this.userPassword);
        }

    }


    @Override
    public String toString() {
        return "LocalUser{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userMoney='" + userMoney + '\'' +
                ", userMoneyFormat='" + userMoneyFormat + '\'' +
                ", mobile='" + mobile + '\'' +
                ", paypassword='" + paypassword + '\'' +
                '}';
    }
}