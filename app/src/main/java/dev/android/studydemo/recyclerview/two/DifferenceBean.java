package dev.android.studydemo.recyclerview.two;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/14.
 * user: Administrator
 * date: 2018/12/14
 * time; 14:03
 * name:
 */
public class DifferenceBean implements Serializable {
    public  int type;
    public  String name;

    public DifferenceBean(int type, String name) {
        this.type = type;
        this.name = name;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
