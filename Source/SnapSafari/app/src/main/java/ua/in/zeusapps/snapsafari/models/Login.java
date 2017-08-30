package ua.in.zeusapps.snapsafari.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("username")
    @Expose
    private String _username;
    @SerializedName("password")
    @Expose
    private String _password;
    @SerializedName("email")
    @Expose
    private String _email;

    public Login(String username, String password) {
        _username = username;
        _password = password;
    }

    public Login(String username, String password, String email) {
        _username = username;
        _password = password;
        _email = email;
    }

    public String getUsername() {
        return _username;
    }

    public String getPassword() {
        return _password;
    }

    public String getEmail() {
        return _email;
    }
}
