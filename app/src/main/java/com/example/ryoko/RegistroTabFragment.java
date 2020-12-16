package com.example.ryoko;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

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
            String sap=segundoAp.getText().toString().trim();
            String tel=telefono.getText().toString().trim();
            String fech=fecha.getText().toString().trim();

            //Verificar campos vacios
            if(user.isEmpty() || pass.isEmpty() || nom.isEmpty() || pap.isEmpty() || sap.isEmpty() || tel.isEmpty() || fech.isEmpty()){
                Toast.makeText(getActivity(), "No deje campos vacios", Toast.LENGTH_LONG).show();
            }else{
                //Verificacion de correo
                String regex = "(?:[^<>()\\[\\].,;:\\s@\"]+(?:\\.[^<>()\\[\\].,;:\\s@\"]+)*|\"[^\\n\"]+\")@(?:[^<>()\\[\\].,;:\\s@\"]+\\.)+[^<>()\\[\\]\\.,;:\\s@\"]{2,63}";
                if (!user.matches(regex)){
                    Toast.makeText(getActivity(), "Por favor, verifique su email", Toast.LENGTH_LONG).show();
                }else{
                    //Verificacion de telefono
                    if(telefono.length()!=10){
                        Toast.makeText(getActivity(), "Por favor, verifique su numero de telefono", Toast.LENGTH_LONG).show();
                    }else{
                        //PAso las verificaciones, manda informacion
                        peticionPost(user, pass, nom, pap, sap,tel,fech);
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


    public void peticionPost(String user, String pass, String nom, String pap, String sap,String tel,String fech) {
       new Thread(new Runnable() {
           @Override
           public void run() {
               // Instantiate the RequestQueue.
               RequestQueue queue = Volley.newRequestQueue(getActivity());
               String url = "https://ryokotravelsagencia.000webhostapp.com/API/alta_cliente_usuario.php";

               HashMap params = new HashMap();
               params.put("email", user);
               params.put("contraseña", pass);
               params.put("nombre", nom);
               params.put("primerAp", pap);
               params.put("segundoAp", sap);
               params.put("telefono", tel);
               params.put("fecha_nac", fech);


               JSONObject parametros = new JSONObject(params);

               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros,
                       new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {

                               String cadena = response.toString();
                               Log.i("RESPUESTA", response.toString());
                               try {
                                   Boolean exito = response.getBoolean("exito");
                                   String mensaje=response.getString("mensaje");
                                   Log.i("RESPUESTA 2", exito.toString());
                                   Log.i("RESPUESTA 3", mensaje);
                                   //Mostrar mensaje
                                   Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                                   //Si la respuesta es correcta cambiar de pantalla e iniciar
                                   if(exito){
                                       Intent i = new Intent(getActivity(), PrincipalActivity.class);
                                       i.putExtra("usuario",user);
                                       startActivity(i);
                                       getActivity().finish();
                                   }

                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }

                           }
                       },
                       new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               Toast.makeText(getContext(), "No se puede iniciar en este momento", Toast.LENGTH_LONG).show();
                           }
                       }
               );

               queue.add(jsonObjectRequest);
           }
       }).start();

    }



}

