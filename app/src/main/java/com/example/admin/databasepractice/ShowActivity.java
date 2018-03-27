package com.example.admin.databasepractice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtFname, txtLname, txtEmail, txtGender, txtCity, txtPass;

    SQLiteDatabase database;

    Cursor cursor;

    String firstName, lastName, personEmail, personGender, personCity, personPass;

    Button btnDelete, btnUpdate;

    RadioGroup showGenRadio;

    RadioButton showGenMale, showGenFemale;

    Spinner showSpin;

    String[] listCity = new String[]{
            "Ahmedabad", "Baroda", "Surat", "Bharuch"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        txtFname = findViewById(R.id.txtFname);
        txtLname = findViewById(R.id.txtLname);
        txtEmail = findViewById(R.id.txtEmail);
        // txtGender=findViewById(R.id.txtGen);
        // txtCity=findViewById(R.id.txtCity);
        txtPass = findViewById(R.id.txtPass);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        showGenRadio = findViewById(R.id.showGadioGen);

        showGenFemale = findViewById(R.id.showGenFemale);
        showGenMale = findViewById(R.id.showGenMale);

        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        showSpin = findViewById(R.id.showSpinCity);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String email = b.getString("email");

            String gender = b.getString("gender");

            String city = b.getString("city");
        }
        database = openOrCreateDatabase("Person", Context.MODE_PRIVATE, null);

        cursor = database.rawQuery("select * from pDetails where email=email", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    firstName = cursor.getString(cursor.getColumnIndex("fname"));
                    lastName = cursor.getString(cursor.getColumnIndex("lname"));
                    personEmail = cursor.getString(cursor.getColumnIndex("email"));
                    personGender = cursor.getString(cursor.getColumnIndex("gender"));
                    personCity = cursor.getString(cursor.getColumnIndex("city"));
                    personPass = cursor.getString(cursor.getColumnIndex("password"));

                    txtFname.setText(firstName);
                    txtLname.setText(lastName);
                    txtEmail.setText(personEmail);

                    if (personGender.equals("Female")) {
                        showGenFemale.setChecked(true);
                    } else {
                        showGenMale.setChecked(true);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listCity);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    showSpin.setAdapter(adapter);

                    if (personCity.equals("Ahmedabad")) {
                        //showSpin.getSelectedItem();
                        showSpin.setSelection(0);
                    } else if (personCity.equals("Baroda")) {

                        showSpin.setSelection(1);
                        // showSpin.getSelectedItem(showSpin.getSelectedItem());
                    } else if (personCity.equals("Surat")) {
                        showSpin.setSelection(2);
                        //showSpin.setSelected(true);
                    } else if (personCity.equals("Bharuch")) {
                        showSpin.setSelection(3);
                        // showSpin.setSelected(true);
                    }

                    // txtGender.setText(personGender);
                    // txtCity.setText(personCity);
                    txtPass.setText(personPass);

                } while (cursor.moveToNext());
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                database.execSQL("delete from pDetails where email='" + txtEmail.getText().toString() + "'");

                Toast.makeText(getApplicationContext(), "Record is Deleted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                break;

            case R.id.btnUpdate:
                Intent intent1 = new Intent(this, UpdateActivity.class);

                String radioValue = ((RadioButton) this.findViewById(showGenRadio.getCheckedRadioButtonId())).getText().toString();
                String spinValue = (String) showSpin.getItemAtPosition(showSpin.getSelectedItemPosition());

                Bundle b = new Bundle();

                b.putString("fname", txtFname.getText().toString());
                b.putString("lname", txtLname.getText().toString());
                b.putString("email", txtEmail.getText().toString());
                b.putString("gender", radioValue);
                b.putString("city", spinValue);
                b.putString("password", txtPass.getText().toString());

                intent1.putExtras(b);

                startActivity(intent1);
                break;
        }
    }

}
