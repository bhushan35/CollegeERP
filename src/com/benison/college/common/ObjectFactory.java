package com.benison.college.common;

import com.benison.college.account.AccountLogin;
import com.benison.college.admin.AdminLogin;
import com.benison.college.library.LibraryLogin;


public class ObjectFactory {
    public static Login getLogin(String role){
        if(role.equalsIgnoreCase("ADMIN")){
            return new AdminLogin();
        }else if(role.equalsIgnoreCase("ACCOUNT")){
            return new AccountLogin();
        }else if(role.equalsIgnoreCase("LIBRARY")){
            return new LibraryLogin();
        }else{
           return null;
        }
    }
}
