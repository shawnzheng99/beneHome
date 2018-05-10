package zhengc.bcit.ca.benehome;

import android.net.Uri;
import java.io.Serializable;

public class Place implements Serializable {

    private String Name;
    private String Url;
    private String Description;
    private String Category;
    private String Hours;
    private String Location;
    private String PC;
    private String Email;
    private String Phone;
    private String Y;
    private String X;
    private String Website;

    public Place(String name, String url, String description, String category, String hours, String location, String PC, String email, String phone, String y, String x, String website) {
        Name = name;
        Url = url;
        Description = description;
        Category = category;
        Hours = hours;
        Location = location;
        this.PC = PC;
        Email = email;
        Phone = phone;
        Y = y;
        X = x;
        Website = website;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setHours(String hours) {
        Hours = hours;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setY(String y) {
        Y = y;
    }

    public void setX(String x) {
        X = x;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getName() {
        return Name;
    }

    public String getUrl() {
        return Url;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategory() {
        return Category;
    }

    public String getHours() {
        return Hours;
    }

    public String getLocation() {
        return Location;
    }

    public String getPC() {
        return PC;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getY() {
        return Y;
    }

    public String getX() {
        return X;
    }

    public String getWebsite() {
        return Website;
    }

    public Place() {

    }
}
