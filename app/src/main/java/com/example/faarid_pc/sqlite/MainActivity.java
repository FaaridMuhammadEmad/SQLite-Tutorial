package com.example.faarid_pc.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText editName, editSurname, editMarks;
    Button buttonAdd;
    Button buttonViewAll;
    Button buttonUpdate;
    EditText editTextId;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);

        editTextId = (EditText)findViewById(R.id.edit_id);
        editName = (EditText)findViewById(R.id.edit_name);
        editSurname = (EditText)findViewById(R.id.edit_surname);
        editMarks = (EditText)findViewById(R.id.edit_marks);
        buttonViewAll = (Button)findViewById(R.id.button_view);
        buttonAdd = (Button)findViewById(R.id.button_add);
        buttonUpdate = (Button)findViewById(R.id.button_update);
        buttonDelete = (Button)findViewById(R.id.button_delete);

        addData();
        viewAll();
        updateData();
        deleteData();
    }

    public void addData(){
        buttonAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          boolean isInserted = myDb.insertData(editName.getText().toString(),
                                          editSurname.getText().toString(),
                                          editMarks.getText().toString());

                          if(isInserted == true)
                          {
                              Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                          }
                          else
                          {
                              Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                          }
                    }
                }
        );
    }

    public void viewAll(){
        buttonViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            showMessage("Error", "No Data Found");
                            return;
                        }
                        else{
                            StringBuffer buffer = new StringBuffer();
                            while(res.moveToNext()){
                                buffer.append("ID : "+res.getString(0)+"\n");
                                buffer.append("NAME : "+res.getString(1)+"\n");
                                buffer.append("SURNAME : "+res.getString(2)+"\n");
                                buffer.append("MARKS : "+res.getString(3)+"\n\n");
                            }
                            showMessage("Data", buffer.toString());
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(editTextId.getText().toString(),editName.getText().toString(),
                                editSurname.getText().toString(),editMarks.getText().toString());
                        if(isUpdated == true){
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void deleteData(){
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0){
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
}
