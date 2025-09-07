package com.example.iot_lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Actividad_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        //lo que mencionamos del nombre que se tiene que guardar y vbolver aparecer de la anterior vista.
        TextView tvNombreUsuario = findViewById(R.id.tvNombreUsuario);

        String nombre = getIntent().getStringExtra("NOMBRE_USUARIO");
        if (nombre != null && !nombre.trim().isEmpty()) {
            tvNombreUsuario.setText(nombre);
        } else {
            tvNombreUsuario.setText("Bienvenido invitado");
        }

        //para este caso es el mismo toolbar que la vista1
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.titulo_principal));
        }

        //hacemos funcionar los botones de las tematicas
        Button btnRedes = findViewById(R.id.btnRedes);
        Button btnCiberseguridad = findViewById(R.id.btnCiberseguridad);
        Button btnFibraOptica = findViewById(R.id.btnFibraOptica);

        btnRedes.setOnClickListener(v -> {
            Intent intent = new Intent(this, Actividad_3.class);
            intent.putExtra("TEMA", "REDES");
            startActivity(intent);
        });

        btnCiberseguridad.setOnClickListener(v -> {
            Intent intent = new Intent(this, Actividad_3.class);
            intent.putExtra("TEMA", "CIBERSEGURIDAD");
            startActivity(intent);
        });

        btnFibraOptica.setOnClickListener(v -> {
            Intent intent = new Intent(this, Actividad_3.class);
            intent.putExtra("TEMA", "FIBRA");
            startActivity(intent);
        });
    }


    //hacemos funcioanr el boton hacia atras tambien
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_stats) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
