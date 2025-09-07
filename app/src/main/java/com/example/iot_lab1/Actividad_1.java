package com.example.iot_lab1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Actividad_1 extends AppCompatActivity {

    private EditText etNombre;
    private Button btnJugar;
    private TextView Titulo_teleahorcado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad1);

        //configuramos el toolbar, para mostrar el título.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.titulo_principal));
        }
        //hacemos funciona el boton de ingresar texto y el boton de jugar para que asi nos mande a la siguiente vista.
        etNombre = findViewById(R.id.IngreseNombre);
        btnJugar = findViewById(R.id.BotonJugar);
        Titulo_teleahorcado = findViewById(R.id.Titulo_teleahorcado);

        registerForContextMenu(Titulo_teleahorcado);
        btnJugar.setEnabled(false);

        etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnJugar.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        //además hay que tener en cuenta que el nombre que ingresemo se tiene que guardar y usar en la siguiente vista.
        btnJugar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            Intent intent = new Intent(Actividad_1.this, Actividad_2.class);
            intent.putExtra("NOMBRE_USUARIO", nombre);
            startActivity(intent);
        });
    }
    //es para el titulo del toolbar
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lab, menu);
    }
    //tenemos que recordar que el titulo tiene que cambiar de color.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.color_azul) {
            Titulo_teleahorcado.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            return true;
        } else if (id == R.id.color_verde) {
            Titulo_teleahorcado.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            return true;
        } else if (id == R.id.color_rojo) {
            Titulo_teleahorcado.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            return true;
        }

        return super.onContextItemSelected(item);
    }
}
