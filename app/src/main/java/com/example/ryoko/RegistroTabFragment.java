package com.example.ryoko;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class RegistroTabFragment extends Fragment implements View.OnClickListener {
    EditText usuario, contraseña, nombre, primerAp, segundoAp, telefono, fecha;
    Button btn_registrar;
    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registro_tab_fragment, container, false);

        usuario = root.findViewById(R.id.r_user);
        contraseña = root.findViewById(R.id.r_contraseña);
        nombre = root.findViewById(R.id.nombre);
        primerAp = root.findViewById(R.id.primerAp);
        segundoAp = root.findViewById(R.id.segundoAp);
        telefono = root.findViewById(R.id.telefono);
        fecha = root.findViewById(R.id.fecha);
        btn_registrar = root.findViewById(R.id.btn_registrar);
        fecha.setOnClickListener(this);
        btn_registrar.setOnClickListener(this);

        usuario.setTranslationX(800);
        contraseña.setTranslationX(800);
        nombre.setTranslationX(800);
        primerAp.setTranslationX(800);
        segundoAp.setTranslationX(800);
        fecha.setTranslationX(800);
        telefono.setTranslationX(800);
        btn_registrar.setTranslationX(800);

        usuario.setAlpha(0);
        contraseña.setAlpha(0);
        nombre.setAlpha(0);
        primerAp.setAlpha(0);
        segundoAp.setAlpha(0);
        telefono.setAlpha(0);
        fecha.setAlpha(0);
        btn_registrar.setAlpha(0);

        usuario.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        contraseña.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        nombre.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        primerAp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        segundoAp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        telefono.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        fecha.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btn_registrar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return root;


    }

    @Override
    public void onClick(View v) {
        //Si se ds clic en el espacio para la fecha
        if (v.getId() == R.id.fecha) {
            obtenerFecha();
        }
        //Boton para registrar usuario/cliente
        if (v.getId() == R.id.btn_registrar) {
            //Sacar informacion
            String user=usuario.getText().toString().trim();
            String pass=contraseña.getText().toString().trim();
            String nom=nombre.getText().toString().trim();
            String pap=primerAp.getText().toString().trim();
            String sas=segundoAp.getText().toString().trim();
            String tel=telefono.getText().toString().trim();
            String fech=fecha.getText().toString().trim();

            //Verificar campos vacios
            if(user.isEmpty() || pass.isEmpty() || nom.isEmpty() || pap.isEmpty() || sas.isEmpty() || tel.isEmpty() || fech.isEmpty()){
                Toast.makeText(getActivity(), "No deje campos vacios", Toast.LENGTH_LONG).show();
            }else{
                //Verificacion de correo
                String regex = "(?:[^<>()\\[\\].,;:\\s@\"]+(?:\\.[^<>()\\[\\].,;:\\s@\"]+)*|\"[^\\n\"]+\")@(?:[^<>()\\[\\].,;:\\s@\"]+\\.)+[^<>()\\[\\]\\.,;:\\s@\"]{2,63}";
                if (!user.matches(regex)){
                    Toast.makeText(getActivity(), "Por favor, verifique su email", Toast.LENGTH_LONG).show();
                }else{
                    //Verificacion de telefono
                    if(telefono.length()==10){
                        Toast.makeText(getActivity(), "Por favor, verifique su numero de telefono", Toast.LENGTH_LONG).show();
                    }else{
                        //PAso las verificaciones, manda informacion
                    }
                }
               
            }

        }



    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                fecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }


}

