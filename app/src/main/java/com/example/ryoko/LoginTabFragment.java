package com.example.ryoko;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {
    EditText usuario, contraseña;
    Button btn_inicio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        usuario=root.findViewById(R.id.user);
        contraseña=root.findViewById(R.id.contraseña);
        btn_inicio=root.findViewById(R.id.btn_iniciar);

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
}
