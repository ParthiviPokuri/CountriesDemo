package com.example.praveenp.allianz.dao;

import java.util.List;

/**
 * Created by praveenpokuri on 01/10/18.
 */
public class Countries {
    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    List<Country> country;

}
