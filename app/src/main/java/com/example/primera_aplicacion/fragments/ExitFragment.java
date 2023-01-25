package com.example.primera_aplicacion.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.primera_aplicacion.R;

public class ExitFragment extends Fragment {
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exit, container, false);

//        textView = rootView.findViewById(R.id.fr_exit_tv);
//        textView.setText("Se ha accedido a la vista del Exit");

        return rootView;
    }
}