package com.example.tramsys_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class funcion extends AppCompatActivity
{
    //declaracion de variables
    EditText et_seg;
    Button iniciar ;
    TextView mostrarPersonas, mostrarId,mostrarFecha, mostrarHora;
    String ident;
    long tiempoTotal =  0;
    String f_personas;
 //   String EnviarPersonas;
    String fecha_final;
    String hora_final;

    DatabaseReference miBasedeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcion);

        miBasedeDatos = FirebaseDatabase.getInstance().getReference();

        et_seg = findViewById(R.id.editText2);//conectar con la interfaz
        iniciar = findViewById(R.id.button2);

        iniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                recivirIdentificador();
                String very = et_seg.getText().toString();//extraemos datos del campo de texto y los convertimos en cadena de caracteres
                if (!very.isEmpty())//verificamos si el campo esta vacio
                {
                     iniciarConteo();//llamamos a la funcion de contar
                }
                else Toast.makeText(funcion.this, "Escribe el tiempo en segundos animal", Toast.LENGTH_SHORT).show();//si el campo esta vacio
            }
        });
    }
//----------------------------------------------------------------------------------------------------------------
    public void recivirIdentificador ()//funcion que trae el identificador de la variable anterior
    {
        mostrarId = findViewById(R.id.txt_cantidad_paquetes);//conecta con la interfaz
        Bundle extras = getIntent().getExtras();//extrae la candena de texto
        ident = extras.getString("identificador");//asigna a una variable la informacion recivida
        mostrarId.setText(ident);//muestra el identificador

        mostrarFecha = findViewById(R.id.txt_fecha);
        mostrarHora = findViewById(R.id.txt_hora);
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        mostrarHora.setText(hourFormat.format(date));
        hora_final = hourFormat.format(date);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        mostrarFecha.setText(dateFormat.format(date));
        fecha_final = dateFormat.format(date);


    }
//-----------------------------------------------------------------------------------------------------------------
    private void iniciarConteo()//funcion de cuenta regresiva
    {
        int segu = (Integer.valueOf(et_seg.getText().toString())*1000);//convertimos los segundos a milisegundos
        final long t_seg = Integer.valueOf(et_seg.getText().toString());//almacenamos los segundos normales en una variable long
        long valor = segu;//asignamos a valor los milisegundos
        CountDownTimer cuenta = new CountDownTimer(valor, 1000)
        {
            @Override
            public void onTick(long l)//la cuenta regresiva
            {
                iniciar.setEnabled(false);//desactiva el boton de iniciar
                long tiempo = l/1000;//divide el tiempo entre mil
                long segundos = tiempo %60;//extrae el modulo del tiempo para pasarlos a segundos
                String mostrarsegundos = String.format("%02d", segundos);//asigna un formato para mostrar los segundos
                Toast.makeText(funcion.this, mostrarsegundos,Toast.LENGTH_SHORT).show();//muestra los segundos en un dato emergente
            }

            @Override
            public void onFinish()//cuando finaliza la cuenta regresiva
            {
                Toast.makeText(funcion.this, "finalizado", Toast.LENGTH_SHORT).show();//
                tiempoTotal= t_seg;//asigna a la variable tiempototal los segundos normales
                long personas = tiempoTotal/2;//divide el tiempo entre dos para simular las entidades contadas
                Toast.makeText(funcion.this, personas+"personas", Toast.LENGTH_SHORT).show();//mensaje emergente

                if (personas % 2 == 0)//evalua el resultado si es par o impar
                {
                    Toast.makeText(funcion.this, "segundos pares", Toast.LENGTH_SHORT).show();//mensaje emergente
                }

                mostrarPersonas = findViewById(R.id.txt_entidades_contadas);//muestra la cantidad de personas contadas
                f_personas = Long.toString(personas);
                mostrarPersonas.setText(f_personas);
                iniciar.setEnabled(true);//habilita el boton de iniciar


                enviarDatos();
            }
        }.start();//repite el ciclo
    }

   private void enviarDatos()
    {
        int f_mostrarPersonas = Integer.parseInt(f_personas);
        Map<String, Object> Datos = new HashMap<>();//crea un mapa de datos en un string
        Datos.put("Entidades contadas",f_mostrarPersonas);//almacena en la variable Datos los textos introducidos
        Datos.put("Fecha",fecha_final);//almacena en la variable Datos los textos introducidos
        Datos.put("Hora",hora_final);
        Datos.put("Identificador",ident);

        Toast.makeText(funcion.this, "enviando datos", Toast.LENGTH_SHORT).show();
        miBasedeDatos.child("Unidades").push().setValue(Datos);//crea una rema dentro de la base de datos, esta rama se llama sugerencias, y en envia los datos almacenados


    }
}
