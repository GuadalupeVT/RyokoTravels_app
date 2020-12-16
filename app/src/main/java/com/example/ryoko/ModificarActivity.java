package com.example.ryoko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import java.util.HashMap;

public class ModificarActivity extends AppCompatActivity {
    EditText id_res,fecha_inicio,fecha_fin;
    Button btn_cambio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        String [] reserva=getIntent().getExtras().getStringArray("usuarios");
    }


    public void peticionPost(String id, String inico, String fin) {
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