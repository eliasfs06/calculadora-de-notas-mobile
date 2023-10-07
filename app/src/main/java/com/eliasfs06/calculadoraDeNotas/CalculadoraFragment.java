package com.eliasfs06.calculadoraDeNotas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CalculadoraFragment extends Fragment {

    private List<Character> buffer = new ArrayList<>();
    private Map<Button, Character> numerosButtons = new ArrayMap();
    private Map<Button, Character> operacoesButtons =  new ArrayMap();
    private TextView textBuffer;
    private TextView resultado;

    List<String> bufferFinal = new ArrayList<>();


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
        textBuffer =  view.findViewById(R.id.operacoes_calculadora);
        resultado =  view.findViewById(R.id.resultado_calculadora);
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
                    if(buffer.size() != 0) {
                        Character lastEntry = buffer.get(buffer.size() - 1);
                        if(lastEntry == '/' && value == '0') {
                            showToast("Não é permitido divisão por 0.");
                            return;
                        }
                    }
                    buffer.add(value);
                    setTextBuffer();
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
                    if(buffer.size() != 0 && value != 'd') {
                        Character lastEntry = buffer.get(buffer.size() - 1);
                        if (lastEntry == '+' || lastEntry == '-' || lastEntry == '*' || lastEntry == '/'
                                || lastEntry == '=') {
                            showToast("Não é possível realizar duas operações em seguida.");
                            return;
                        }
                    }

                    if(value == 'd'){
                        buffer.remove(buffer.size() - 1);
                    } else if(value == '='){
                        buffer.add(value);
                        preparaCalculo();
                    } else {
                        buffer.add(value);
                    }
                    setTextBuffer();
                }
            });
        }
    }

    public void preparaCalculo(){
        String number = "";
        for(Character valor : buffer) {
            if (valor != '+' && valor != '-' && valor != 'd'
                    && valor != '*' && valor != '/' && valor != '=') {
                number += valor;
            } else {
                //Fim de um número
                bufferFinal.add(number);
                number = "";

                bufferFinal.add(valor.toString());
            }
        }
        bufferFinal.remove(bufferFinal.size() -1); //remove =
        finalizaCalculo();
    }

   public void finalizaCalculo() {

        int index = 0;
        Double numero = Double.parseDouble(bufferFinal.get(index));

        while (index < bufferFinal.size()) {
            String operador = bufferFinal.get(index);
            index++;
            double proximoNumero = Double.parseDouble(bufferFinal.get(index));
            index++;

            if (operador.equals("+")) {
                numero += proximoNumero;
            } else if (operador.equals("-")) {
                numero -= proximoNumero;
            } else if (operador.equals("*")) {
                numero *= proximoNumero;
            } else if (operador.equals("/")) {
                numero /= proximoNumero;
            }
        }

        bufferFinal.clear();
        buffer.clear();
        resultado.setText(numero.toString());
    }

    public void setTextBuffer(){
        String operacoes = "";
        for(Character value : buffer){
            operacoes += value;
        }
        textBuffer.setText(operacoes);
    }

    public void showToast(String mensagem){
        Toast toast = Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG);
        toast.show();
    }
}