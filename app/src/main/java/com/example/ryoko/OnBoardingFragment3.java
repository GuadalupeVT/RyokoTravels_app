package com.example.ryoko;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingFragment3 extends Fragment {
    FloatingActionButton fab;
    TextView skip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_on_boarding3,container,false);

        fab=root.findViewById(R.id.fab);
        skip=root.findViewById(R.id.skip3);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i=new Intent(getActivity(),LoginActivity.class);
                startActivity(i);
            }
        });

        skip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i=new Intent(getActivity(),LoginActivity.class);
                startActivity(i);
            }
        });

        return root;
    }
}
