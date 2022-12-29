package com.hexanome.splendor.retrofit;

import com.google.gson.JsonObject;

public class GSONObjectGenerator {
    public String username;
    public String password;
    public String preferredColor;
    public String role;

    public GSONObjectGenerator(String username, String password) {
        this.username = username;
        this.password = password;
        this.preferredColor="FF0000";
        this.role="ROLE_PLAYER";
    }


    public GSONObjectGenerator(String username, String password, String preferredColor, String role) {
        this.username = username;
        this.password = password;
        this.preferredColor = preferredColor;
        this.role = role;
    }
    public JsonObject createSessionGSON(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("creator", this.username);
        jsonObject.addProperty("game", "SplendorApi-Hexanome03");
        jsonObject.addProperty("savegame", "");
        return jsonObject;
    }
    public JsonObject createUserGSON(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.username);
        jsonObject.addProperty("password", this.password);
        jsonObject.addProperty("preferredColour", this.preferredColor);
        jsonObject.addProperty("role", this.role);
        return jsonObject;
    }
}
