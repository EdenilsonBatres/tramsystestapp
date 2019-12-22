package com.example.tramsys_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    EditText identif;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        identif = findViewById(R.id.editText);

    }
    public void clic(View view)
    {
        String identificador = identif.getText().toString();

        Intent intent = new Intent(this, funcion.class);
        intent.putExtra("identificador",identificador);
        startActivity(intent);
        identif.setEnabled(false);


    }
}
