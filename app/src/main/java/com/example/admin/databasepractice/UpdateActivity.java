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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUpdtFname,edtUpdtLname,edtUpdtEmail,edtUpdtPass;

    RadioGroup updtRadioGen;

    RadioButton updtRadioMale,updtRadioFemale;

    Spinner updtSpinCity;

    Button btnUpdatee;

    String[] listCity=new String[]{"Ahmedabad","Baroda","Surat","Bharuch"};

    SQLiteDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtUpdtFname=findViewById(R.id.edtUpdtFname);
        edtUpdtLname=findViewById(R.id.edtUpdtLname);
        edtUpdtEmail=findViewById(R.id.edtUpdtEmail);
        edtUpdtPass=findViewById(R.id.edtUpdtPass);

        updtRadioGen=findViewById(R.id.updtRadioGen);

        updtRadioMale=findViewById(R.id.updtGenMale);
        updtRadioFemale=findViewById(R.id.updtGenFemale);

        updtSpinCity=findViewById(R.id.updtSpinCity);

        btnUpdatee=findViewById(R.id.btnUpdatee);

        Intent in=getIntent();

        Bundle b=in.getExtras();

        String fnm=b.getString("fname");
        String lnm=b.getString("lname");
        String emal=b.getString("email");
        String gen=b.getString("gender");
        String city=b.getString("city");
        String pass=b.getString("password");

        edtUpdtFname.setText(fnm);
        edtUpdtLname.setText(lnm);
        edtUpdtEmail.setText(emal);

        if (gen.equals("Female"))
        {
            updtRadioFemale.setChecked(true);
        }
        else {
            updtRadioMale.setChecked(true);
        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listCity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updtSpinCity.setAdapter(adapter);


        if (city.equals("Ahmedabad"))
        {
            updtSpinCity.setSelection(0);
        }
        else if (city.equals("Baroda"))
        {
            updtSpinCity.setSelection(1);
        }
        else if (city.equals("Surat"))
        {
            updtSpinCity.setSelection(2);
        }
        else if (city.equals("Bharuch"))
        {
            updtSpinCity.setSelection(3);
        }
        edtUpdtPass.setText(pass);


        database=openOrCreateDatabase("Person", Context.MODE_PRIVATE,null);

        btnUpdatee.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnUpdatee) {
            String radioValue = ((RadioButton) this.findViewById(updtRadioGen.getCheckedRadioButtonId())).getText().toString();
            String spinValue = (String) updtSpinCity.getItemAtPosition(updtSpinCity.getSelectedItemPosition());

            database.execSQL("update pDetails set fname='" + edtUpdtFname.getText().toString() + "',lname='" + edtUpdtLname.getText().toString() + "',email='" + edtUpdtEmail.getText().toString() + "',gender='" + radioValue + "',city='" + spinValue + "',password='" + edtUpdtPass.getText().toString() + "'");

            Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ShowActivity.class);
            startActivity(intent);
        }
    }


}
