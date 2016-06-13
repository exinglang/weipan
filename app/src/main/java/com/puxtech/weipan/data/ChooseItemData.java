package com.puxtech.weipan.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mac on 15/11/5.
 */
public class ChooseItemData implements Serializable {
        String code,name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
