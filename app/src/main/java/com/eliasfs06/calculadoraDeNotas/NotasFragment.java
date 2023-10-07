package com.eliasfs06.calculadoraDeNotas;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NotasFragment extends Fragment {


    private EditText inputN1;
    private EditText inputN2;
    private EditText inputN3;
    private TextView resultado;
    public NotasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        Button buttonCalcular = view.findViewById(R.id.button_calcular_nota);
        inputN1 = view.findViewById(R.id.input_n1);
        inputN2 = view.findViewById(R.id.input_n2);
        inputN3 = view.findViewById(R.id.input_n3);
        resultado = view.findViewById(R.id.situacao);

        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularResultadoMedia();
            }
        });

        return view;
    }

    public void calcularResultadoMedia(){

        float n1 = Float.parseFloat(inputN1.getText().toString());
        float n2 = Float.parseFloat(inputN2.getText().toString());
        float n3 = Float.parseFloat(inputN3.getText().toString());

        float media = (n1 + n2 + n3) / 3;
        if (media >= 7) {
            resultado.setText("Aprovado: " + media);
        } else if (media < 7 && media >= 5) {
            resultado.setText("Aprovado por nota: " + media);
        } else if (media < 5 && media >= 3) {
            resultado.setText("Recuperação: " + media);
        } else {
            resultado.setText("Reprovado: " + media);
        }

    }
}