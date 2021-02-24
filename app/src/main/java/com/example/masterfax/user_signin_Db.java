package com.example.masterfax;

import java.util.jar.Attributes;

public class user_signin_Db
{
    public String name;
    public String email;

    user_signin_Db(){}

    user_signin_Db(String name,String email)
    {
        this.name= name;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
