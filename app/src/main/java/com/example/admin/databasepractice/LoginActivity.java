package com.example.admin.databasepractice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtLogEmail,edtLogPass;

    Button btnLoginn;

    SQLiteDatabase database;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogEmail=findViewById(R.id.edtLogEmail);
        edtLogPass=findViewById(R.id.edtLogPass);

        btnLoginn=findViewById(R.id.btnLoginn);

        btnLoginn.setOnClickListener(this);

        database=openOrCreateDatabase("Person", Context.MODE_PRIVATE,null);
    }

    @Override
    public void onClick(View v) {

        Log.i("Entered","Entered");
        cursor=database.rawQuery("select email,password from pDetails where email='"+edtLogEmail.getText().toString()+"'and password='"+edtLogPass.getText().toString()+"'",null);

        if (cursor!=null)
        {
            Toast.makeText(getApplicationContext(),"Entered",Toast.LENGTH_SHORT).show();

            if (cursor.moveToFirst())
            {
                do {

                    String email=cursor.getString(cursor.getColumnIndex("email"));
                    String pass=cursor.getString(cursor.getColumnIndex("password"));

                    Log.i("Email",""+email);
                    Log.i("Password",""+pass);

                    Toast.makeText(getApplicationContext(),"Loggged In",Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(this,ShowActivity.class);
                    Bundle b=new Bundle();
                    b.putString("email",edtLogEmail.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                }while (cursor.moveToNext());
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();
        }
    }
}
