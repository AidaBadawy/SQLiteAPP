package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText editName, editSurname, editMarks, editId;
    Button buttonAdd, btnViewData, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.edit_name);
        editSurname = findViewById(R.id.edit_surname);
        editMarks = findViewById(R.id.edit_marks);
        editId = findViewById(R.id.edit_id);
        buttonAdd = findViewById(R.id.button_add);
        btnViewData = findViewById(R.id.button_data);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        addData();
        viewData();
        updateData();
        deleteData();
    }

    private void deleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteData(editId.getText().toString());

                if (deletedRows > 0)
                        Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();

                    else
                        Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void updateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isUpdated = myDb.updateData(
                        editId.getText().toString(),
                        editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString());

                if (isUpdated == true)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();

               else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();



            }
        });
    }

    public void addData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isInserted =  myDb.insertData(
                                        editName.getText().toString(),
                                        editSurname.getText().toString(),
                                        editMarks.getText().toString());

               //Check if inserted display toast
               if (isInserted == true)
                   Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();

               else
                   Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewData(){
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res =  myDb.getAllData();

                if (res.getCount() == 0){

                    //show message
                    showMessage("Error", "No Data found");
                    return;

                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id: " + res.getString(0) + "\n");
                    buffer.append("Name: " + res.getString(1) + "\n");
                    buffer.append("Surname: " + res.getString(2) + "\n");
                    buffer.append("Marks: " + res.getString(3) + "\n\n");

                }

                //show all data
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
