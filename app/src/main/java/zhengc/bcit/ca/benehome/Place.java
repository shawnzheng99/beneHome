package zhengc.bcit.ca.benehome;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A place refers to a subsidized house, which has name, description contact info and so on.
 */
public class Place implements Serializable {

    private String Name;
    private HashMap<String,String> Url ;
    private String Description;
    private String Category;
    private String Hours;
    private String Location;
    private String PC;
    private String Email;
    private String Phone;
    private String Y;
    private String X;
    private String Apply;
    private String TotalUnit;
    private String TypeUnits;
    private String Eligible;
    private String Pets;
    private String Boundaries;
    private String Elementary;
    private String Middle;
    private String Secondary;

    public Place() {

    }
    public Place(String name, HashMap<String,String> url, String description, String category, String hours, String location, String PC, String email, String phone, String y, String x, String apply, String totalUnit, String typeUnits, String eligible, String pets, String boundaries, String elementary, String middle, String secondary) {
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
        Apply = apply;
        TotalUnit = totalUnit;
        TypeUnits = typeUnits;
        Eligible = eligible;
        Pets = pets;
        Boundaries = boundaries;
        Elementary = elementary;
        Middle = middle;
        Secondary = secondary;
    }

    public void setApply(String apply) {
        Apply = apply;
    }

    public void setTotalUnit(String totalUnit) {
        TotalUnit = totalUnit;
    }

    public void setTypeUnits(String typeUnits) {
        TypeUnits = typeUnits;
    }

    public void setEligible(String eligible) {
        Eligible = eligible;
    }

    public void setPets(String pets) {
        Pets = pets;
    }

    public void setBoundaries(String boundaries) {
        Boundaries = boundaries;
    }

    public void setElementary(String elementary) {
        Elementary = elementary;
    }

    public void setMiddle(String middle) {
        Middle = middle;
    }

    public void setSecondary(String secondary) {
        Secondary = secondary;
    }

    public String getApply() {
        return Apply;
    }

    public String getTotalUnit() {
        return TotalUnit;
    }

    public String getTypeUnits() {
        return TypeUnits;
    }

    public String getEligible() {
        return Eligible;
    }

    public String getPets() {
        return Pets;
    }

    public String getBoundaries() {
        return Boundaries;
    }

    public String getElementary() {
        return Elementary;
    }

    public String getMiddle() {
        return Middle;
    }

    public String getSecondary() {
        return Secondary;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUrl(HashMap<String,String> url) {
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


    public String getName() {
        return Name;
    }

    public HashMap<String,String> getUrl() {
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



}
