package com.edianjucai.eshop.util;

import android.text.TextUtils;

/**
 * Created by user on 2016-09-20.
 */
public class DataUtil {

    /**
     *
     * @param str 为可变参数,传入要判断的字符串
     * @return -1:传入数据通过,可以执行下一步  其他具体数字代表第几个数据不通过
     */
    public static int checkData(String... str){
        for (int i = 0;i < str.length;i++){
            if (TextUtils.isEmpty(str[i])){
                return i;
            }
        }
        return -1;
    }



}
