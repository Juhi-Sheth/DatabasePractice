package com.example.admin.databasepractice;

import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFname,edtLname,edtEmail,edtPassword;

    RadioGroup radioGen;

    RadioButton radioMale,radioFemale;

    Spinner spinnCity;

    Button btnRegi,btnLogin;

    String[] spinn=new String[]{
      "Ahmedabad","Baroda","Surat","Bharuch"
    };

    SQLiteDatabase database;

    String radioValue,spinnerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFname=findViewById(R.id.edtFname);
        edtLname=findViewById(R.id.edtLname);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPass);

        radioGen=findViewById(R.id.radioGen);

        radioFemale=findViewById(R.id.genFemale);
        radioMale=findViewById(R.id.genMale);

        spinnCity=findViewById(R.id.spinCity);

        btnRegi=findViewById(R.id.btnRegi);
        btnLogin=findViewById(R.id.btnLogin);


        ArrayAdapter adapterCity=new ArrayAdapter(this,android.R.layout.simple_list_item_1,spinn);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnCity.setAdapter(adapterCity);

        database=openOrCreateDatabase("Person", Context.MODE_PRIVATE,null);

        database.execSQL("create table if not exists pDetails(rid integer primary key autoincrement,fname varchar(30),lname varchar(30),email varchar(40),gender varchar(6),city varchar(20),password varchar(15))");

        btnRegi.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegi:

                radioValue=((RadioButton) this.findViewById(radioGen.getCheckedRadioButtonId())).getText().toString();
                spinnerValue=(String) spinnCity.getItemAtPosition(spinnCity.getSelectedItemPosition());

                if (edtFname.getText().toString().equals("null") && edtLname.getText().toString().equals("null") && edtEmail.getText().toString().equals("null"))
                {
                    Toast.makeText(getApplicationContext(),"Fill Values",Toast.LENGTH_SHORT).show();
                }
                else {
                    database.execSQL("insert into pDetails values(?,'" + edtFname.getText().toString() + "','" + edtLname.getText().toString() + "','" + edtEmail.getText().toString() + "','" + radioValue + "','" + spinnerValue + "','"+edtPassword.getText().toString()+"')");

                    Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(this,ShowActivity.class);

                    Bundle b=new Bundle();
                    b.putString("email",edtEmail.getText().toString());
                    b.putString("gender",radioValue);
                    b.putString("city",spinnerValue);

                    intent.putExtras(b);

                    startActivity(intent);

                    refreshData();
                }
                break;

            case R.id.btnLogin:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void refreshData() {
        edtFname.setText("");
        edtLname.setText("");
        edtEmail.setText("");
        radioGen.clearCheck();
        edtPassword.setText("");
    }
}
