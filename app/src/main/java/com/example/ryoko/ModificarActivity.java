package com.example.ryoko;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

public class ModificarActivity extends AppCompatActivity implements View.OnClickListener {
    EditText id_res,fecha_in,fecha_fn,nom,tp,trans,tt;
    Button btn_cambio;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    String us;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        id_res=findViewById(R.id.id_res);
        fecha_in=findViewById(R.id.fecha_in);
        fecha_fn=findViewById(R.id.fecha_fn);
        fecha_in.setOnClickListener(this);
        fecha_fn.setOnClickListener(this);
        nom=findViewById(R.id.id_nom);
        tp=findViewById(R.id.id_tp);
        trans=findViewById(R.id.id_trans);
        tt=findViewById(R.id.id_tt);

        String [] reserva=getIntent().getExtras().getStringArray("usuarios");
        us=getIntent().getExtras().getString("usuario");
        id_res.setText(reserva[0].toString());
        fecha_in.setText(reserva[1].toString());
        fecha_fn.setText(reserva[2].toString());
        nom.setText(reserva[3]+" "+reserva[4]+ " "+reserva[5]);
        tp.setText(reserva[6]);
        trans.setText(reserva[7]+", "+reserva[8]);
        tt.setText("$ "+reserva[9]);

        btn_cambio=findViewById(R.id.btn_modificar);




    }




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fecha_in) {
            obtenerFecha(fecha_in);
        }
        if (v.getId() == R.id.fecha_fn) {
            obtenerFecha(fecha_fn);
        }
        if(v.getId()==R.id.btn_modificar){
            peticionPost(id_res.getText().toString(),fecha_in.getText().toString(),fecha_fn.getText().toString(), us);
        }
    }


    private void obtenerFecha(EditText fecha){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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

    public void peticionPost(String id, String inico, String fin, String us) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                String url = "https://ryokotravelsagencia.000webhostapp.com/API/modificar_reserva.php";



                HashMap params = new HashMap();
                params.put("id", id);
                params.put("inicio", inico);
                params.put("fin", fin);



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
                                    Toast.makeText(getBaseContext(), mensaje, Toast.LENGTH_LONG).show();
                                    //Si la respuesta es correcta cambiar de pantalla e iniciar
                                    if(exito){
                                        Intent i = new Intent(getBaseContext(), PrincipalActivity.class);
                                        i.putExtra("usuario",us);
                                        startActivity(i);
                                        finish();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(), "No se puede iniciar en este momento", Toast.LENGTH_LONG).show();
                            }
                        }
                );

                queue.add(jsonObjectRequest);
            }
        }).start();

    }






}