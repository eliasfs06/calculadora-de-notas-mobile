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

        String textN1 = inputN1.getText().toString();
        String textN2 = inputN2.getText().toString();
        String textN3 = inputN3.getText().toString();

        if(textN1.isEmpty() && textN2.isEmpty() && textN3.isEmpty()){
            showToast("Digite pelo menos 1 nota.");
        } else if(textN2.isEmpty() && textN3.isEmpty()){
            calculaNotaAprovacao(textN1, textN2);
        } else if(textN3.isEmpty()){
            calculaNotaAprovacao(textN1, textN2);
        } else {
            float n1 = Float.parseFloat(textN1);
            float n2 = Float.parseFloat(textN2);
            float n3 = Float.parseFloat(textN3);

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

    private void calculaNotaAprovacao(String textN1, String textN2) {

        float nota;
        float notaAprovacao;
        float n1 = Float.parseFloat(textN1);

        if(textN2.isEmpty()){
            nota = (15 - n1)/2;
            notaAprovacao = (21 - n1)/2;

            if(nota > 10){
                showToast("Você está na 4ª prova");
            } else {
                showToast("Aprovação por nota: " + nota + " na 2ª e na 3ª");
                if(notaAprovacao < 10) showToast("Aprovação por média: " + notaAprovacao + " na 2ª e na 3ª");
            }

        } else {
            float n2 = Float.parseFloat(textN2);
            nota = (15 - n1 - n2);
            notaAprovacao = (21 - n1 - n2);

            if(nota > 10){
                showToast("Você está na 4ª prova");
            } else {
                showToast("Aprovação por nota: " + nota + " na 3ª");
                if(notaAprovacao < 10) showToast("Aprovação por média: " + notaAprovacao + " na 2ª e na 3ª");
            }
        }
    }

    public void showToast(String mensagem){
        Toast toast = Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG);
        toast.show();
    }
}