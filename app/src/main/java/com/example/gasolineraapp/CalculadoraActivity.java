package com.example.gasolineraapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculadoraActivity extends AppCompatActivity {

    private EditText etLitros, etPrecioHabitual, etPrecioBarata, etKm, etConsumo;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        etLitros = findViewById(R.id.etLitros);
        etPrecioHabitual = findViewById(R.id.etPrecioHabitual);
        etPrecioBarata = findViewById(R.id.etPrecioBarata);
        etKm = findViewById(R.id.etKm);
        etConsumo = findViewById(R.id.etConsumo);
        tvResultado = findViewById(R.id.tvResultado);

        findViewById(R.id.btnCalcular).setOnClickListener(v -> calcular());
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish()); // vuelve a la primera pantalla
    }

    private void calcular() {
        // 1. Comprobar que no hay campos vacíos
        if (vacio(etLitros) || vacio(etPrecioHabitual) || vacio(etPrecioBarata)
                || vacio(etKm) || vacio(etConsumo)) {
            Toast.makeText(this, R.string.error_campos_vacios, Toast.LENGTH_SHORT).show();
            return;
        }

        double litros = Double.parseDouble(etLitros.getText().toString());
        double precioHabitual = Double.parseDouble(etPrecioHabitual.getText().toString());
        double precioBarata = Double.parseDouble(etPrecioBarata.getText().toString());
        double km = Double.parseDouble(etKm.getText().toString());
        double consumo = Double.parseDouble(etConsumo.getText().toString());

        // 2. Comprobar que los valores tienen sentido
        if (litros <= 0 || precioHabitual <= 0 || precioBarata <= 0 || km <= 0 || consumo <= 0) {
            Toast.makeText(this, R.string.error_valores, Toast.LENGTH_SHORT).show();
            return;
        }
        if (precioBarata >= precioHabitual) {
            Toast.makeText(this, R.string.error_precios, Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. Cálculo
        double ahorroBruto = litros * (precioHabitual - precioBarata);
        double costeDesvio = (km * consumo / 100) * precioBarata;
        double ahorroNeto = ahorroBruto - costeDesvio;

        // 4. Mostrar resultado en pantalla y en Toast a la vez
        String texto;
        if (ahorroNeto >= 0) {
            texto = getString(R.string.resultado_ahorro, ahorroNeto);
        } else {
            texto = getString(R.string.resultado_perdida, -ahorroNeto);
        }
        tvResultado.setText(texto);
        Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
    }

    private boolean vacio(EditText et) {
        return et.getText().toString().trim().isEmpty();
    }
}