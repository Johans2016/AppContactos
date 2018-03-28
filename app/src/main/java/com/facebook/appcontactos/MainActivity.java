package com.facebook.appcontactos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button bfecha;
    private Button bSiguiente;
    private TextView tvfecha;
    private EditText datonombre;
    private EditText datoTelefono;
    private EditText datoEmail;
    private EditText datoDescripcion;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String PREF_KEY = "mispreferencias";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declarando los findViewById
        tvfecha = findViewById(R.id.tvFecha);
        bfecha = findViewById(R.id.btFecha);
        bSiguiente = findViewById(R.id.btsiguiente);
        datonombre = findViewById(R.id.etNombre);
        datoTelefono = findViewById(R.id.ettelefono);
        datoEmail = findViewById(R.id.etemail);
        datoDescripcion = findViewById(R.id.etdescripcion);

        datonombre.setText(getValuePreference(getApplicationContext(),"NombreDato"));
        datoTelefono.setText(getValuePreference(getApplicationContext(),"TelefonoDato"));
        datoEmail.setText(getValuePreference(getApplicationContext(),"EmailDato"));
        datoDescripcion.setText(getValuePreference(getApplicationContext(),"DesDato"));
        tvfecha.setText(getValuePreference(getApplicationContext(),"FechaDato"));

        //para validar email
        //final String validarEmail = datoEmail.getText().toString().trim();
        //final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

         bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                Calendar date = new GregorianCalendar(year,month,day);
                final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
                tvfecha.setText(dateFormat.format(date.getTime()));

            }
        };

        bSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //obtengo el texto de los editText
                String dnombre = datonombre.getText().toString();
                String dtelefono = datoTelefono.getText().toString();
                String demail = datoEmail.getText().toString();
                String ddescripcion = datoDescripcion.getText().toString();
                String novalido ="email no válido";


                Intent i = new Intent(MainActivity.this , Detalles_de_contacto.class );
                i.putExtra("NombreCompleto", dnombre);
                i.putExtra("telefono", dtelefono);
                if (emailValidator(demail) == true) {
                    i.putExtra("mailto", demail);
                }
                else
                {
                    i.putExtra("mailto",novalido);
                }
                i.putExtra("descripcion", ddescripcion);
                i.putExtra("fecha", tvfecha.getText());

                //ejecuto el metodo para guardar el valor
                saveValuePreference(getApplicationContext(),"NombreDato", datonombre.getText().toString());
                saveValuePreference(getApplicationContext(),"TelefonoDato", datoTelefono.getText().toString());
                saveValuePreference(getApplicationContext(),"EmailDato", datoEmail.getText().toString());
                saveValuePreference(getApplicationContext(),"DesDato", datoDescripcion.getText().toString());
                saveValuePreference(getApplicationContext(),"FechaDato", tvfecha.getText().toString());


                startActivity(i);
            }
        });


    }
    //salvo los valores mediante los dos siguientes métodos
    public void saveValuePreference(Context context, String identificador, String text){
        SharedPreferences settings = context.getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(identificador, text);
        editor.commit();
    }

    public String getValuePreference(Context context, String identificador){
        SharedPreferences preferences = context.getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        return preferences.getString(identificador, "");
    }

    //metodo para validar email
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
