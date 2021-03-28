package com.example.automatizaresera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SelectedPlants.db";
    private static final String TABLE_CONTACTS = "plants";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_MAXLIGHTLUX = "maxLightLux";
    private static final String KEY_MINLIGHTLUX = "minLightLux";
    private static final String KEY_MAXTEMP = "maxTemp";
    private static final String KEY_MINTEMP = "minTemp";
    private static final String KEY_MAXENVHUMID = "maxEnvHumid";
    private static final String KEY_MINENVHUMID = "minEnvHumid";
    private static final String KEY_MAXSOILMOIST = "maxSoilMoist";
    private static final String KEY_MINSOILMOIST = "minSoilMoist";
    private static final String KEY_MAXSOILEC = "maxSoilEc";
    private static final String KEY_MINSOILEC = "minSoilEc";
     ;

    public SQLiteDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT," + KEY_MAXLIGHTLUX + " INTEGER," + KEY_MINLIGHTLUX + " INTEGER," + KEY_MAXTEMP +
                " INTEGER," + KEY_MINTEMP + " INTEGER," + KEY_MAXENVHUMID + " INTEGER," + KEY_MINENVHUMID + " INTEGER,"
                + KEY_MAXSOILMOIST + " INTEGER," + KEY_MINSOILMOIST + " INTEGER," + KEY_MAXSOILEC + " INTEGER," + KEY_MINSOILEC +
                " INTEGER" +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new plant
    void addPlant(SelectedPlant plant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, plant.getName());
        values.put(KEY_IMAGE, plant.getImage());
        values.put(KEY_MAXLIGHTLUX, plant.getMaxLightLux());
        values.put(KEY_MINLIGHTLUX, plant.getMinLightLux());
        values.put(KEY_MAXTEMP, plant.getMaxTemp());
        values.put(KEY_MINTEMP, plant.getMinTemp());
        values.put(KEY_MAXENVHUMID, plant.getMaxEnvHumid());
        values.put(KEY_MINENVHUMID, plant.getMinEnvHumid());
        values.put(KEY_MAXSOILMOIST, plant.getMaxSoilMoist());
        values.put(KEY_MINSOILMOIST, plant.getMinSoilMoist());
        values.put(KEY_MAXSOILEC, plant.getMaxSoilEc());
        values.put(KEY_MINSOILEC, plant.getMinSoilEc());

        // Inserting Row
        long insertedRow = db.insert(TABLE_CONTACTS, null, values);
        System.out.println("Inserted row: ");
        System.out.println(insertedRow);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
//    SelectedPlant getPlant(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                        KEY_NAME, KEY_IMAGE, KEY_MAXLIGHTLUX, KEY_MINLIGHTLUX, KEY_MAXTEMP, KEY_MINTEMP, KEY_MAXENVHUMID
//                , KEY_MINENVHUMID, KEY_MAXSOILMOIST, KEY_MINSOILMOIST, KEY_MAXSOILEC, KEY_MINSOILEC}, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if( cursor != null && cursor.moveToFirst() ) {
//
//            SelectedPlant plant = new SelectedPlant(Integer.parseInt(cursor.getString(0)),
//                    cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
//                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)),
//                    Integer.parseInt(cursor.getString(8)), Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)),
//                    Integer.parseInt(cursor.getString(11)), Integer.parseInt(cursor.getString(12)));
//       // cursor.close();
//        return plant;}
//        else System.out.println("Cursor null");
//        return null;
//    }

    // code to get all contacts in a list view
    public SelectedPlant getPlant(int id) {
        List<SelectedPlant> PlantList = new ArrayList<SelectedPlant>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SelectedPlant p = new SelectedPlant();
                p.setID(Integer.parseInt(cursor.getString(0)));
                p.setName(cursor.getString(1));
                p.setImage(cursor.getString(2));
                p.setMaxLightLux(Integer.parseInt(cursor.getString(3)));
                p.setMinLightLux(Integer.parseInt(cursor.getString(4)));
                p.setMaxTemp(Integer.parseInt(cursor.getString(5)));
                p.setMinTemp(Integer.parseInt(cursor.getString(6)));
                p.setMaxEnvHumid(Integer.parseInt(cursor.getString(7)));
                p.setMinEnvHumid(Integer.parseInt(cursor.getString(8)));
                p.setMaxSoilMoist(Integer.parseInt(cursor.getString(9)));
                p.setMinSoilMoist(Integer.parseInt(cursor.getString(10)));
                p.setMaxSoilEc(Integer.parseInt(cursor.getString(11)));
                p.setMinSoilEc(Integer.parseInt(cursor.getString(12)));
                PlantList.add(p);
                System.out.println("Exista cursor");
            } while (cursor.moveToNext());}
        else System.out.println("Cursor null 2");
        return PlantList.get(id);
        }

        // code to update the single contact


    // Deleting single contact
    public void deletePlant(SelectedPlant plant) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(plant.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getPlantCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;

    }
    public boolean hasElement(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        return cursor.moveToFirst();
    }

}
