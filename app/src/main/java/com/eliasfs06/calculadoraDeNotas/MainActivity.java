package com.eliasfs06.calculadoraDeNotas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CalculadoraFragment calculadoraFragment;
    private NotasFragment notasFragment;

    @Override
    protected void onCreate(Bundle savinInstanceState) {
        super.onCreate(savinInstanceState);
        setContentView(R.layout.activity_main);
        calculadoraFragment = new CalculadoraFragment();
        notasFragment = new NotasFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, notasFragment)
                .commit();

   }

    public void inciarFragmentoCalculadora(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, calculadoraFragment)
                .commit();
    }

    public void inciarFragmentoNotas(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, notasFragment)
                .commit();
    }


}