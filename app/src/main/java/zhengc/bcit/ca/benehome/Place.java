package zhengc.bcit.ca.benehome;

import android.net.Uri;
import java.io.Serializable;

public class Place implements Serializable {

    private String name;
    private String picUrl;
    private String desc;
    private String cate;
    private String hours;
    private String location;
    private String pc;
    private String email;
    private String phone;
    private String lon;
    private String lat;
    private String web;

    public Place(String name, String desc,
                 String cate, String hours, String location,
                 String pc, String email, String phone, String lon,
                 String lat, String web) {
        this.name = name;
        this.desc = desc;
        this.cate = cate;
        this.hours = hours;
        this.email = email;
        this.location = location;
        this.pc = pc;
        this.phone = phone;
        this.lon = lon;
        this.lat = lat;
        this.web = web;

    }

    public Place() {
    }

    public String getWeb(){
        return web;
    }
    public String getName() {

        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {

        return picUrl;
    }

    public String getDesc() {
        return desc;
    }

    public String getCate() {
        return cate;
    }

    public String getHours() {
        return hours;
    }

    public String getLocation() {
        return location;
    }

    public String getPc() {
        return pc;
    }

    public String getPhone() {
        return phone;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }
}
