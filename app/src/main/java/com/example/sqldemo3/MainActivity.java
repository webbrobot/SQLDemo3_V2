package com.example.sqldemo3;

import static java.lang.String.valueOf;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

//Todo: this is a test to see if it comes up in the todo list.
// Todo: Need to add a delete button
// Todo: Add some SQLite db quary capability like find a field or create a specific list
public class MainActivity extends AppCompatActivity {

    // reference to buttons and other controls
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -------------------- get control addresses-----------------------------------------
        btn_add = findViewById(R.id.btn_add);                        // add button  reference
        btn_viewAll = findViewById(R.id.btn_viewAll);                // viewall button  reference
        et_age = findViewById(R.id.et_Age);                          // age text field   reference
        et_name= findViewById(R.id.et_Name );                        // name text field  reference
        sw_activeCustomer = findViewById(R.id.sw_active );           // is customer acive switch  reference
        lv_customerList =findViewById(R.id.lv_customerList );        // list view that will show all the customers name, age, etc.
        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        showCustomersOnListView();



        //List<CustomerModel> everyone = dataBaseHelper.getEveryone();
     //   ArrayAdapter customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,everyone);

        // set listeners

        int duration = Toast.LENGTH_LONG;

        btn_add.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Context context = getApplicationContext();


                CustomerModel customerModel;
                try{
               //  String.valueof(xxx ) returns the value as a string, so an int of 5 would return "5"
                int theAgeInt = Integer.parseUnsignedInt(String.valueOf(et_age.getText()));
                // test to see if Integer.parseInt works like the statment above
                int theAgeInt2  = Integer.parseInt(String.valueOf(et_age.getText()));
                // theAgeInt2 = theAgeInt

                customerModel = new CustomerModel(1,  et_name.getText().toString().trim() , theAgeInt2, sw_activeCustomer.isChecked());
                CharSequence text = customerModel.toString();
                //  int enteredAge = Integer.parseInt(et_age.toString());
             //   String aString = "this is a string";
              //  String  stringAge = et_age.toString();

                Toast toast = Toast.makeText(context, customerModel.toString(), duration);
                toast.show();

            }
            catch(Exception e){
                    customerModel = new CustomerModel(-1, "error", 0, false);
                    Toast toast = Toast.makeText(context, "Error Creating Customer -- Error Info = " + e.toString() , duration);
                    toast.show();
                }

                //DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                boolean success =   dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, "Success = " + success, duration ).show();

                // update show all list
                showCustomersOnListView();

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new databaseHelper object
             //  DataBaseHelper dataBaseHelper = new DataBaseHelper( MainActivity.this);

               // get the list of all customers in the database
              //  List<CustomerModel> everyone = dataBaseHelper.getEveryone();

                // get the current context
                Context context = getApplicationContext();

                // create a new array adapter and fill it with the everyone list of customers from the database
                // The Adapter acts as a bridge between the UI Component and the Data Source.
                showCustomersOnListView();

                // ------- old code that is no longer used ---------
                    //  CharSequence text = "ViewAll Button";
                    // int duration = Toast.LENGTH_LONG;

                    //  Toast toast = Toast.makeText(context, everyone.toString(), duration);
                    //toast.show();
                // ------ end old code ----------------------------


            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                showCustomersOnListView();
                Toast.makeText(MainActivity.this, "Deleted " + clickedCustomer.toString(), duration).show();
            }
        });
    }

    private void showCustomersOnListView() {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,dataBaseHelper.getEveryone());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}