package com.example.ryoko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment implements View.OnClickListener {
    EditText usuario, contraseña;
    Button btn_inicio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        usuario=root.findViewById(R.id.user);
        contraseña=root.findViewById(R.id.contraseña);
        btn_inicio=root.findViewById(R.id.btn_iniciar);
        btn_inicio.setOnClickListener(this);

        usuario.setTranslationX(800);
        contraseña.setTranslationX(800);
        btn_inicio.setTranslationX(800);

        usuario.setAlpha(0);
        contraseña.setAlpha(0);
        btn_inicio.setAlpha(0);

        usuario.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        contraseña.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btn_inicio.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return root;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_iniciar) {

            String user= usuario.getText().toString();
            String pass=contraseña.getText().toString();
            peticionPost(user, pass);


        }

    }

    public void peticionPost(String user, String pass) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://ryokotravelsagencia.000webhostapp.com/API/validar_usuario.php";

        HashMap params = new HashMap();
        params.put("usuario", user);
        params.put("contraseña", pass);

        JSONObject parametros = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String cadena = response.toString();
                        Log.i("RESPUESTA", response.toString());

                        try {

                               JSONArray usuarios = response.getJSONArray("usuarios");

                                if(usuarios.length()==0){
                                    Toast.makeText(getActivity(), "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                                }else{
                                    for(int i=0; i<usuarios.length(); i++) {
                                        JSONObject u = usuarios.getJSONObject(i);
                                        Toast.makeText(getActivity(), "Hola"+user, Toast.LENGTH_LONG).show();
                                    }
                                    Intent i = new Intent(getActivity(), MenuActivity.class);
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



}
