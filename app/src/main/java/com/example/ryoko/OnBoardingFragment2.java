package com.example.ryoko;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class OnBoardingFragment2 extends Fragment {
    TextView skip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_on_boarding2,container,false);
        skip=root.findViewById(R.id.skip2);
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
