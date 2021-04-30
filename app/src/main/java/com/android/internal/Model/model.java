package com.android.internal.Model;

public class model {
    String contactName;
    String phonenum;
    int pos;

    public model(String contactName, String phonenum,int pos) {
        this.contactName = contactName;
        this.phonenum = phonenum;
        this.pos=pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}
