package com.example.iot_lab1;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Random;

public class Actividad_3 extends AppCompatActivity {

    // Como son para cada temática diferente colocamos palabras clave para cada categoria.
    private String[] palabrasRedes = {"ROUTER", "SWITCH", "IPV6", "DNS", "CABLE"};
    private String[] palabrasCiber = {"HACKER", "MALWARE", "PHISHING", "FIREWALL", "TROYANO"};
    private String[] palabrasFibra = {"OPTICA", "CONECTOR", "LASER", "BANDA", "SPLITTER"};

    private String[] palabras; // arreglo dinámico según tema
    private String palabraActual;
    private StringBuilder progreso;
    private int errores;
    private long startTime;

    private ImageView ivHead, ivTorso, ivBrazoIzq, ivBrazoDer, ivPiernaIzq, ivPiernaDer;
    private TextView tvPalabra;
    private GridLayout gridLetras;

    private String tema;
    private int aciertosConsecutivos = 0;
    private int comodines = 0;

    private ImageButton btnComodin;
    private TextView tvComodines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.teleahorcado));
        }


// para que aparezca el boton de retroceder
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        tvPalabra = findViewById(R.id.tvPalabra);
        gridLetras = findViewById(R.id.gridLetras);

        ivHead = findViewById(R.id.ivHead);
        ivTorso = findViewById(R.id.ivTorso);
        ivBrazoIzq = findViewById(R.id.ivBrazoIzq);
        ivBrazoDer = findViewById(R.id.ivBrazoDer);
        ivPiernaIzq = findViewById(R.id.ivPiernaIzq);
        ivPiernaDer = findViewById(R.id.ivPiernaDer);

        Button btnNuevoJuego = findViewById(R.id.btnNuevoJuego);
        btnComodin = findViewById(R.id.btnComodin);
        tvComodines = findViewById(R.id.tvComodines);

        btnComodin.setOnClickListener(v -> usarComodin());
        // creamos con un for las letras de a-z y configruamos las dimensaioens de cada tecla y que funcione al hacerle click
        for (char c = 'A'; c <= 'Z'; c++) {
            Button b = new Button(this);
            b.setText(String.valueOf(c));
            b.setTextSize(14);
            b.setAllCaps(true);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 120;
            params.height = 120;
            params.setMargins(4, 4, 4, 4);
            b.setLayoutParams(params);
            b.setOnClickListener(this::onLetraClick);
            gridLetras.addView(b);
        }


        btnNuevoJuego.setOnClickListener(v -> iniciarJuego());
        startTime = System.currentTimeMillis();

        // recibimos la tematica de la vista2 y utiulizamos las palabras claves correspondeitnes

        tema = getIntent().getStringExtra("TEMA");
        TextView tvTema = findViewById(R.id.tvTema);

        if (tema != null) {
            tvTema.setText( tema);
            switch (tema) {
                case "REDES":
                    palabras = palabrasRedes;
                    break;
                case "CIBERSEGURIDAD":
                    palabras = palabrasCiber;
                    break;
                case "FIBRA":
                    palabras = palabrasFibra;
                    break;
                default:
                    palabras = new String[]{"DEFAULT"};
            }
        } else {
            palabras = new String[]{"DEFAULT"};
        }

        iniciarJuego();
        startTime = System.currentTimeMillis();

    }

    //para poner hacer funcionar al boton nuevo juego y para volver habilitar las letras.
    private void iniciarJuego() {
        Random rand = new Random();
        palabraActual = palabras[rand.nextInt(palabras.length)];
        progreso = new StringBuilder("_".repeat(palabraActual.length()));
        errores = 0;
        comodines = 1;
        actualizarComodines();
        ;

        tvPalabra.setText(progreso);
        tvPalabra.setTextColor(android.graphics.Color.BLACK);

        ivHead.setVisibility(View.INVISIBLE);
        ivTorso.setVisibility(View.INVISIBLE);
        ivBrazoIzq.setVisibility(View.INVISIBLE);
        ivBrazoDer.setVisibility(View.INVISIBLE);
        ivPiernaIzq.setVisibility(View.INVISIBLE);
        ivPiernaDer.setVisibility(View.INVISIBLE);
        for (int i = 0; i < gridLetras.getChildCount(); i++) {
            gridLetras.getChildAt(i).setEnabled(true);
        }
    }

//configuracion los aciertos e inciertos para ver si el usuario gana o no la partida
    private void onLetraClick(View v) {
        Button b = (Button) v;
        String letra = b.getText().toString();
        b.setEnabled(false);

        boolean acierto = false;
        for (int i = 0; i < palabraActual.length(); i++) {
            if (palabraActual.charAt(i) == letra.charAt(0)) {
                progreso.setCharAt(i, letra.charAt(0));
                acierto = true;
            }
        }
        if (acierto) {
            tvPalabra.setText(progreso);
            if (progreso.toString().equals(palabraActual)) {
                long endTime = System.currentTimeMillis();
                long segundos = (endTime - startTime) / 1000;
                tvPalabra.setText("¡Ganaste! Palabra: " + palabraActual + "\nTiempo: " + segundos + "s");
                tvPalabra.setTextColor(android.graphics.Color.GREEN);

                // en caso de que pierda desabilitamos las teclas para que no pueda seguir jugando y no transcurra el tiempo.
                for (int i = 0; i < gridLetras.getChildCount(); i++) {
                    gridLetras.getChildAt(i).setEnabled(false);
                }
            }
        } else {
            errores++;
            mostrarParte(errores);
            if (errores >= 6) {
                long endTime = System.currentTimeMillis();
                long segundos = (endTime - startTime) / 1000;
                tvPalabra.setText("Perdiste :( Palabra: " + palabraActual + "\nTiempo: " + segundos + "s");
                tvPalabra.setTextColor(android.graphics.Color.RED);

                for (int i = 0; i < gridLetras.getChildCount(); i++) {
                    gridLetras.getChildAt(i).setEnabled(false);
                }
            }
        }

    }
    //para las partes del cuerpo
    private void mostrarParte(int error) {
        switch (error) {
            case 1: ivHead.setVisibility(View.VISIBLE); break;
            case 2: ivTorso.setVisibility(View.VISIBLE); break;
            case 3: ivBrazoIzq.setVisibility(View.VISIBLE); break;
            case 4: ivBrazoDer.setVisibility(View.VISIBLE); break;
            case 5: ivPiernaIzq.setVisibility(View.VISIBLE); break;
            case 6: ivPiernaDer.setVisibility(View.VISIBLE); break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
   //esto pertenece a la actividad 4 para los comodines
    private void usarComodin() {
        if (comodines > 0) {
            comodines--;

            // la idea es buscar la oalabra que falta de manera alazar y ayudar al usuario a ganar
            Random rand = new Random();
            int index;
            do {
                index = rand.nextInt(palabraActual.length());
            } while (progreso.charAt(index) != '_'); // aseguramos que sea letra oculta

            progreso.setCharAt(index, palabraActual.charAt(index));
            tvPalabra.setText(progreso);

            actualizarComodines();

            // ahora tenemos que considerar el caso en que el usaurio gane con el comodin
            if (progreso.toString().equals(palabraActual)) {
                long endTime = System.currentTimeMillis();
                long segundos = (endTime - startTime) / 1000;
                tvPalabra.setText("¡Ganaste! Palabra: " + palabraActual + "\nTiempo: " + segundos + "s");
                tvPalabra.setTextColor(android.graphics.Color.GREEN);

                for (int i = 0; i < gridLetras.getChildCount(); i++) {
                    gridLetras.getChildAt(i).setEnabled(false);
                }
            }
        }
    }
    private void actualizarComodines() {
        tvComodines.setText("⭐ " + comodines);
        btnComodin.setEnabled(comodines > 0);

    }

}
