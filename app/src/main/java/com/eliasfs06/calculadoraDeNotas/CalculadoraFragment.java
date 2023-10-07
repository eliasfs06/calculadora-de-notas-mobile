package com.eliasfs06.calculadoraDeNotas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CalculadoraFragment extends Fragment {

    private List<Character> buffer = new ArrayList<>();
    private Map<Button, Character> numerosButtons = new ArrayMap();
    private Map<Button, Character> operacoesButtons =  new ArrayMap();


    public CalculadoraFragment() {
    }

    public static CalculadoraFragment newInstance(String param1, String param2) {
        CalculadoraFragment fragment = new CalculadoraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);
        setNumerosButtons(view, numerosButtons);
        setOperacoesButtons(view, operacoesButtons);

        return view;
    }

    public void setNumerosButtons(View view, Map<Button, Character> numerosButtons){
        numerosButtons.put(view.findViewById(R.id.button1), '1');
        numerosButtons.put(view.findViewById(R.id.button2), '2');
        numerosButtons.put(view.findViewById(R.id.button3), '3');
        numerosButtons.put(view.findViewById(R.id.button4), '4');
        numerosButtons.put(view.findViewById(R.id.button5), '5');
        numerosButtons.put(view.findViewById(R.id.button6), '6');
        numerosButtons.put(view.findViewById(R.id.button7), '7');
        numerosButtons.put(view.findViewById(R.id.button8), '8');
        numerosButtons.put(view.findViewById(R.id.button9), '9');
        numerosButtons.put(view.findViewById(R.id.button0), '0');

        for (final Map.Entry<Button, Character> entry : numerosButtons.entrySet()) {
            final Character value = entry.getValue();
            entry.getKey().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buffer.add(value);
                }
            });
        }
    }

    public void setOperacoesButtons(View view, Map<Button, Character> operacoesButtons){
        operacoesButtons.put(view.findViewById(R.id.buttonMulti), '*');
        operacoesButtons.put(view.findViewById(R.id.buttonDiv), '/');
        operacoesButtons.put(view.findViewById(R.id.buttonSoma), '+');
        operacoesButtons.put(view.findViewById(R.id.buttonSubstracao), '-');
        operacoesButtons.put(view.findViewById(R.id.buttonDel), 'd');
        operacoesButtons.put(view.findViewById(R.id.buttonIgual), '=');

        for (final Map.Entry<Button, Character> entry : operacoesButtons.entrySet()) {
            final Character value = entry.getValue();
            entry.getKey().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Character lastEntry = buffer.get(buffer.size() - 1);
                    if(lastEntry == '+'|| lastEntry == '-' || lastEntry == 'd'
                            || lastEntry == '*' || lastEntry == '/' || lastEntry == '=') {
                        showToast("Não é possível realizar duas operações em seguida");
                    } else if(value == 'd'){
                        buffer.remove(buffer.size() - 1);
                    } else if(value == '='){
                        finalizaCalculo();
                    } else {
                        buffer.add(value);
                    }
                }
            });
        }
    }

    public void finalizaCalculo(){
        validaCalculo();
    }

    public void validaCalculo(){
        //Verifica divisão por 0
        for(int i = 0; i < buffer.size(); i++){
            if(buffer.get(i) == '/' && buffer.get(i+1) == '0'){
                showToast("Não é permitido divisão por zero");
                buffer.clear();
            }
        }
    }

    public void showToast(String mensagem){
        Toast toast = Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG);
        toast.show();
    }
}