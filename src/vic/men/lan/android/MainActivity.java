package vic.men.lan.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import archivosBinariosAndroid.ArchivoBinario;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity implements OnClickListener {

    //Controles View
    Button leersd;
    Button escribirsd;
    TextView textView;

    //Objeto que dará acceso a la lógica para manejar Archivos binarios
    ArchivoBinario manejaFicheroBinario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //instancia controles view
        leersd = (Button) findViewById(R.id.leersd);
        escribirsd = (Button) findViewById(R.id.escribirsd);
        textView = (TextView) findViewById(R.id.textView);

        try {
            //inicializamos objeto manejaFicheroBinario enviando nombre del archivo a crear o buscar
            manejaFicheroBinario = new ArchivoBinario("binario1.txt");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        //escucha a botonnes
        leersd.setOnClickListener(this);
        escribirsd.setOnClickListener(this);

        // Código que me comprueba si existe SD y si puedo escribir o no
        String estado = Environment.getExternalStorageState();

    }//fin onCreate()

    @Override
    public void onClick(View v) {

        String contenidoFichero = "";

        switch (v.getId()) {
            //----->ESTOS SON LOS CASOS QUE NOS INTERESAN <------
            case (R.id.leersd): //leer SD
                
                textView.setText("");
        {
            try {
                contenidoFichero = manejaFicheroBinario.leerFichero();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

                if (contenidoFichero.isEmpty() || contenidoFichero.equalsIgnoreCase("")) {
                    contenidoFichero = "Fichero vacío, primero escribe en él. ";
                }

                textView.setText(contenidoFichero);
                Toast.makeText(this, "Se ha leído el fichero", Toast.LENGTH_SHORT).show();

                break;

            case (R.id.escribirsd): //Escribir en SD

                textView.setText("");
                manejaFicheroBinario.llenaAleatorio(100); //llenamos con 10 int aleatorios
                Toast.makeText(this, "Se ha llenado el fichero con 10 enteros ", Toast.LENGTH_SHORT).show();
                break;

        }//fin switch

    }//fin onClick
}//fin class
