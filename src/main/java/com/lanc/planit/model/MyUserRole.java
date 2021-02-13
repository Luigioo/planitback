package com.lanc.planit.model;

public enum MyUserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String nameString;

    private MyUserRole(String _n){this.nameString = _n;}
    public String getName(){ return this.nameString; }

}
