package com.example.ryoko.ui.gallery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ryoko.AdaptadorReservas;
import com.example.ryoko.ModificarActivity;
import com.example.ryoko.PrincipalActivity;
import com.example.ryoko.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;

    RecyclerView recycler;
    EditText parametro;

    ArrayList<String> datos = new ArrayList<>();
    AdaptadorReservas adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recycler=root.findViewById(R.id.recycler_view_consultas);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        parametro=root.findViewById(R.id.caja_parametro);




        parametro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                consultarReservas(getActivity().getIntent().getExtras().getString("usuario"),parametro.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        peticionPost(getActivity().getIntent().getExtras().getString("usuario"));




        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;

    }

    @Override
    public void onClick(View v) {

    }

    public void peticionPost(String user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://ryokotravelsagencia.000webhostapp.com/API/consultar_reserva.php";

                HashMap params = new HashMap();
                params.put("id_cliente", user);



                JSONObject parametros = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String cadena = response.toString();
                                Log.i("RESPUESTA", response.toString());
                                try {
                                    JSONArray reserva = response.getJSONArray("reservas");

                                    String cad = "";
                                    for (int i=0; i<reserva.length();i++){

                                        cadena = reserva.getJSONObject(i).getString("id_Reserva") + "," +
                                                 reserva.getJSONObject(i).getString("fecha_inicio") + "," +
                                                 reserva.getJSONObject(i).getString("fecha_fin") + "," +
                                                 reserva.getJSONObject(i).getString("nombreCliente") + "," +
                                                 reserva.getJSONObject(i).getString("primerAp") + "," +
                                                 reserva.getJSONObject(i).getString("segundoAp") + "," +
                                                 reserva.getJSONObject(i).getString("tipoHabitacion") + "," +
                                                 reserva.getJSONObject(i).getString("tipoTransporte") + "," +
                                                 reserva.getJSONObject(i).getString("linea") + "," +
                                                 reserva.getJSONObject(i).getString("total");

                                        datos.add(cadena);

                                    }

                                            adapter=new AdaptadorReservas(datos);



                                    adapter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            int posicion = recycler.getChildAdapterPosition(view);

                                            String cad = datos.get(posicion);
                                            String alumno[] = cad.split(",");

                                            AlertDialog dialogo = new AlertDialog
                                                    .Builder(getActivity()) // NombreDeTuActividad.this, o getActivity() si es dentro de un fragmento
                                                    .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent i = new Intent(getActivity(), ModificarActivity.class);
                                                            i.putExtra("usuarios",alumno);
                                                            startActivity(i);
                                                        }
                                                    })
                                                    .setNegativeButton("Elimiar", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {


                                                            // Instantiate the RequestQueue.
                                                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                                                            String url = "https://ryokotravelsagencia.000webhostapp.com/API/baja_reserva.php";
                                                            HashMap params = new HashMap();
                                                           // Toast.makeText(getContext(), alumno[0]+":C", Toast.LENGTH_LONG).show();
                                                            params.put("id", alumno[0]);
                                                            Log.i("RESPUESTA", alumno[0]);


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
                                                                                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                                                                                recycler.removeAllViewsInLayout();
                                                                                datos.clear();
                                                                                peticionPost(getActivity().getIntent().getExtras().getString("usuario"));
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
                                                    })
                                                    .setTitle("Acción") // El título
                                                    .setMessage("¿Que deseas hacer con la reservación?") // El mensaje
                                                    .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog!
                                            dialogo.show();

                                        }
                                    });

                                    recycler.setAdapter(adapter);



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


    public void consultarReservas(String user, String parametro){
        recycler.removeAllViewsInLayout();
        datos.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://ryokotravelsagencia.000webhostapp.com/API/consulta_reserva_filtro.php";

                HashMap params = new HashMap();
                params.put("id_cliente", user);
                params.put("parametro",parametro);


                JSONObject parametros = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String cadena = response.toString();
                                Log.i("RESPUESTA", response.toString());
                                try {
                                    JSONArray reserva = response.getJSONArray("reservas");

                                    String cad = "";
                                    for (int i=0; i<reserva.length();i++){

                                        cadena = reserva.getJSONObject(i).getString("id_Reserva") + "|" +
                                                reserva.getJSONObject(i).getString("fecha_inicio") + "|" +
                                                reserva.getJSONObject(i).getString("fecha_fin") + "|" +
                                                reserva.getJSONObject(i).getString("nombreCliente") + "|" +
                                                reserva.getJSONObject(i).getString("primerAp") + "|" +
                                                reserva.getJSONObject(i).getString("segundoAp") + "|" +
                                                reserva.getJSONObject(i).getString("tipoHabitacion") + "|" +
                                                reserva.getJSONObject(i).getString("tipoTransporte") + "|" +
                                                reserva.getJSONObject(i).getString("linea") + "|" +
                                                reserva.getJSONObject(i).getString("total");

                                        datos.add(cadena);

                                    }

                                    adapter=new AdaptadorReservas(datos);
                                    recycler.setAdapter(adapter);



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

    public void alert(String id, String incio, String fin) {
        AlertDialog dialogo = new AlertDialog
                .Builder(getActivity()) // NombreDeTuActividad.this, o getActivity() si es dentro de un fragmento
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hicieron click en el botón positivo, así que la acción está confirmada
                    }
                })
                .setNegativeButton("Elimiar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminar(id);
                    }
                })
                .setTitle("Acción") // El título
                .setMessage("¿Que deseas hacer con la reservación?") // El mensaje
                .create();// No olvides llamar a Create, ¡pues eso crea el AlertDialog!
        dialogo.show();
    }


    public void eliminar(String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "https://ryokotravelsagencia.000webhostapp.com/API/baja_reserva.php";
                HashMap params = new HashMap();
                params.put("id", id);


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
                                    Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                                    peticionPost(getActivity().getIntent().getExtras().getString("usuario"));
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