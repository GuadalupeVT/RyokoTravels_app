package com.example.ryoko.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ryoko.LoginAdapter;
import com.example.ryoko.PrincipalActivity;
import com.example.ryoko.R;

public class SlideshowFragment extends Fragment implements View.OnClickListener {
    Button boton_salir;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        boton_salir=root.findViewById(R.id.btn_cerrar);
        boton_salir.setOnClickListener(this);

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cerrar) {
            Intent i = new Intent(getActivity(), LoginAdapter.class);
            startActivity(i);
            getActivity().finish();
        }
    }
}