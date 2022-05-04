package com.example.signup;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInf implements Parcelable {//Kayıt bilgileri için tutulacaktı fakat yetişmedi.
    private String mail;
    private String name;
    private String surname;
    private String tel;
    private String date;
    private String username;
    private String password;

    public UserInf(String mail, String name, String surname, String tel, String username, String password,String date) {
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
        this.date = date;
        this.username = username;
        this.password = password;
    }

    public UserInf(String mail, String name, String surname, String tel, String username, String password) {
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
        this.username = username;
        this.password = password;
        this.date= null;

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.name);
        parcel.writeString(this.surname);
        parcel.writeString(this.mail);
        parcel.writeString(this.tel);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.date);
    }

    protected UserInf(Parcel in) {
        this.name = in.readString();
        this.surname = in.readString();
        this.mail = in.readString();
    }
    static final Parcelable.Creator<UserInf>CREATOR= new Parcelable.Creator<UserInf>() {
        @Override
        public UserInf createFromParcel(Parcel parcel) {
            return new UserInf(parcel);
        }

        @Override
        public UserInf[] newArray(int size) {
            return new UserInf[size];
        }
    };
}
