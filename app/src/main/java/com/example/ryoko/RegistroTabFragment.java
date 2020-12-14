package com.example.ryoko;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class RegistroTabFragment extends Fragment {
    EditText usuario,contraseña,nombre,primerAp,segundoAp,correo;
    Button btn_registrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.registro_tab_fragment, container, false);

        usuario=root.findViewById(R.id.r_user);
        contraseña=root.findViewById(R.id.r_contraseña);
        nombre=root.findViewById(R.id.nombre);
        primerAp=root.findViewById(R.id.primerAp);
        segundoAp=root.findViewById(R.id.segundoAp);
        correo= root.findViewById(R.id.correo);
        btn_registrar=root.findViewById(R.id.btn_registrar);

        usuario.setTranslationX(800);
        contraseña.setTranslationX(800);
        nombre.setTranslationX(800);
        primerAp.setTranslationX(800);
        segundoAp.setTranslationX(800);
        correo.setTranslationX(800);
        btn_registrar.setTranslationX(800);

        usuario.setAlpha(0);
        contraseña.setAlpha(0);
        nombre.setAlpha(0);
        primerAp.setAlpha(0);
        segundoAp.setAlpha(0);
        correo.setAlpha(0);
        btn_registrar.setAlpha(0);

        usuario.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        contraseña.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        nombre.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        primerAp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        segundoAp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        correo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btn_registrar.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();


        return root;
    }
}
