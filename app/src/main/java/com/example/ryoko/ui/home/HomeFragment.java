package com.example.ryoko.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ryoko.PrincipalActivity;
import com.example.ryoko.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    EditText fecha_inicio,fecha_fin,destino;
    Spinner tipo;
    Button btn_registrar_reserva;

    private static final String CERO = "0";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        fecha_inicio=root.findViewById(R.id.fecha_inicio);
        fecha_inicio.setOnClickListener(this);

        fecha_fin=root.findViewById(R.id.fecha_fin);
        fecha_fin.setOnClickListener(this);

        destino=root.findViewById(R.id.destino);
        tipo=root.findViewById(R.id.spinner_tipo);

        btn_registrar_reserva=root.findViewById(R.id.btn_registrar_reserva);
        btn_registrar_reserva.setOnClickListener(this);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fecha_inicio) {
            obtenerFecha(fecha_inicio);
        }

        if (v.getId() == R.id.fecha_fin) {
            obtenerFecha(fecha_fin);
        }

        if(v.getId() == R.id.btn_registrar_reserva){
            //Sacar informacion
            String inicio=fecha_inicio.getText().toString().trim();
            String fin=fecha_fin.getText().toString().trim();
            String tipo_transporte=tipo.getSelectedItem().toString();
            String destn=destino.getText().toString().trim();
            //Verificar datos
            if(inicio.isEmpty() || fin.isEmpty() || destn.isEmpty() || tipo.getSelectedItemId()==0){
                Toast.makeText(getActivity(), "No deje campos vacios", Toast.LENGTH_LONG).show();
            }else{

            }


        }


    }

    private void obtenerFecha(EditText caja){
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
                caja.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    public void peticionPost(String inicio, String fin, String destino, String tipo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://ryokotravelsagencia.000webhostapp.com/API/alta_reserva.php";
                String usuario = getActivity().getIntent().getExtras().getString("usuario");
                HashMap params = new HashMap();
                params.put("usuario", usuario);
                params.put("inicio", inicio);
                params.put("fin", fin);
                params.put("destino", destino);
                params.put("tipo", tipo);


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