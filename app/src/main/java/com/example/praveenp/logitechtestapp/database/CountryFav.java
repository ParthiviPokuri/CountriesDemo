package com.example.praveenp.logitechtestapp.database;

/**
 * Created by praveenpokuri on 02/10/18.
 */
public class CountryFav {

    public static final String TABLE_NAME = "favourites";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COUNTRY = "country_name";
    public static final String COLUMN_INFO = "country_info";

    private int id;
    private String note;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_COUNTRY + " TEXT,"
                    + COLUMN_INFO + " TEXT"
                    + ")";

    public CountryFav() {
    }

    public CountryFav(int id, String note, String timestamp) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
