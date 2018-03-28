package com.facebook.appcontactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Detalles_de_contacto extends AppCompatActivity {

    private Button bEditar;
    private TextView receptorNombre;
    private TextView receptorFecha;
    private TextView receptorTelefono;
    private TextView receptorEmail;
    private TextView receptorDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_de_contacto);

        bEditar = findViewById(R.id.btEditar);
        receptorNombre = findViewById(R.id.tvname);
        receptorFecha = findViewById(R.id.tvdate);
        receptorTelefono = findViewById(R.id.tvtelefo);
        receptorEmail = findViewById(R.id.tvmail);
        receptorDescripcion = findViewById(R.id.tvdescription);

        //Recepcion de datos
        Bundle parametros = this.getIntent().getExtras();
            if (parametros !=null){
                String dato1 = parametros.getString("NombreCompleto");
                String dato2 = parametros.getString("fecha");
                String dato3 = parametros.getString("telefono");
                String dato4 = parametros.getString("mailto");
                String dato5 = parametros.getString("descripcion");

                receptorNombre.setText(dato1);
                receptorFecha.setText(dato2);
                receptorTelefono.setText(dato3);
                receptorEmail.setText(dato4);
                receptorDescripcion.setText(dato5);

            }


        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Detalles_de_contacto.this , MainActivity.class );
                startActivity(i);
            }
        });






    }
    public boolean onKeyDown( int KeyCode, KeyEvent event){
        if(KeyCode == KeyEvent.KEYCODE_BACK){
            Intent intent =  new Intent(Detalles_de_contacto.this, MainActivity.class);
            startActivity(intent);}
        return super.onKeyDown(KeyCode, event);
    }
}
