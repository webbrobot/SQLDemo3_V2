package com.example.sqldemo3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
// This is the major functions that functions between the CustomerModel, database and user interface.

public class DataBaseHelper extends SQLiteOpenHelper {
     public final String cust_table = "CUSTOMER_TABLE";
     public static final String col_custName = "CUSTOMER_NAME";
     public static final String col_custAge = "CUSTOMER_AGE";
    public static final String col_actCust = "ACTIVE_CUSTOMER";
    public static final String col_ID = "ID";


        public DataBaseHelper(@Nullable Context context ) {
            super(context, "customer.db",  null, 1);
        }

        // this method is called the first time a database object is created.
        // add code to create a new database and initial populations of the table
        @Override
        public void onCreate(SQLiteDatabase db) {

            String createTableStatement = "CREATE TABLE " + cust_table + " (" + col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + col_custName  + " TEXT, " + col_custAge + " INT, " + col_actCust + " BOOL )";
            db.execSQL(createTableStatement);
        }

        // this is called if the database version number changes. It prevents previous users apps from breaking
        // when you change the database design.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public boolean addOne(CustomerModel customerModel){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(col_custName,  customerModel.getName());
            cv.put(col_custAge, customerModel.getAge());
            cv.put(col_actCust, customerModel.isActive());

        long insert = db.insert(cust_table,null ,cv);
            if (insert == -1 ){
                return false;
            }
                else{
                    return true;
            }
        }

        public boolean deleteOne(CustomerModel customerModel){
            // 1. Find customerModel in the database
            // 2. if found then delete it and return True
            // 3. else return False
            SQLiteDatabase db = this.getWritableDatabase();
            String queryString = "DELETE FROM " + cust_table + " WHERE " + col_ID +  " == " + customerModel.getId();
            Cursor cursor = db.rawQuery(queryString, null);
            if (cursor.moveToFirst()){
                return true;
            }
            else{
                return false;
            }
        }
        public List<CustomerModel>  getEveryone() {
           List<CustomerModel> returnList = new ArrayList<>();
                // get data from the database
            String queryString = "SELECT * FROM " + cust_table;
            SQLiteDatabase db = this.getReadableDatabase();
           Cursor cursor= db.rawQuery(queryString, null );
            if (cursor.moveToFirst() ) {
                // loop through the cursor (result set) and create new customer objects
                do {
                    int customerID = cursor.getInt(0);
                    String customerName = cursor.getString(1);
                    int customerAge = cursor.getInt(2);
                    // boolean truinat
                    boolean customerActive = cursor.getInt(3) == 1 ? true : false;
                    CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                    returnList.add(newCustomer);
                } while (cursor.moveToNext());
            }
            else {
                    //falure , don not add anything to the list

                }
            // close the cursor and the database
            cursor.close() ;
            db.close();
            return returnList;
            }
}

