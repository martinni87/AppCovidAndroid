package com.example.primera_aplicacion.models;

import android.util.Log;

public class User {
    private String name;
    private String lastname;
    private String city;
    private String province;
    private int temperature;

    public User(String name, String lastname, String location, String province, int temperature){
        this.name = name;
        this.lastname = lastname;
        this.city = location;
        this.province = province;
        this.temperature = temperature;
    }

    public String getName(){
        return name;
    }
    public String getLastname(){
        return lastname;
    }
    public String getCity(){
        return city;
    }
    public String getProvince(){
        return province;
    }
    public int getTemperature(){
        return temperature;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setProvince(String province){
        this.province = province;
    }
    public void setTemperature(int temperature){
        this.temperature = temperature;
    }

    public void printData(){
        Log.d("Object User", "Data: " +
                getName() + " - " + getLastname() + " - " +
                getCity() + " - " + getProvince() + " - " +
                getTemperature());
    }

    public String returnData(){
        return  getName() + " " + getLastname() + "\t" + getTemperature() + "\n" +
                getCity() + " " + getProvince() + " ";
    }
}
