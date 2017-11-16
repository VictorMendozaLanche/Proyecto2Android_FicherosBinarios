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

//Importamos clase que nos da acceso a la lógica de la app
import archivosBinariosAndroid.ArchivoBinario;
import archivosBinariosAndroid.ComparaArchivosBinarios;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MyActivity";
    
    //Controles View
    Button leersd;
    Button escribirsd;
    Button compara_archivos_binarios;
    TextView textView;

    //Objeto que dará acceso a la lógica para manejar Archivos binarios
    ComparaArchivosBinarios comparaArchivos;
    ArchivoBinario binario1;//Archivos binarios que se procesarán...
    ArchivoBinario binario2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //instancia controles view
        leersd = (Button) findViewById(R.id.leersd);
        escribirsd = (Button) findViewById(R.id.escribirsd);
        compara_archivos_binarios = (Button) findViewById(R.id.boton_comparaArchivosBinarios);
        textView = (TextView) findViewById(R.id.textView);

        //inicializamos archivos binarios y objeto comparador
        try {
            //por defecto se llenan con valores del 1-10 (esta instrucción viene en el contructor)
            binario1 = new ArchivoBinario("binario1.txt");
            binario2 = new ArchivoBinario("binario2.txt");

            comparaArchivos = new ComparaArchivosBinarios(binario1, binario2);
        } catch (Exception e) {
            textView.setText("-->Error al crear los archivos");
        }

        //escucha a botonnes
        leersd.setOnClickListener(this);
        escribirsd.setOnClickListener(this);
        compara_archivos_binarios.setOnClickListener(this);

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
                        contenidoFichero = comparaArchivos.getFichero1().leerFichero();
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

            case (R.id.escribirsd): //Escribir en SD.
                textView.setText("");

                 {
                    try {
                        contenidoFichero = comparaArchivos.getFichero2().leerFichero();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (contenidoFichero.isEmpty() || contenidoFichero.equalsIgnoreCase("")) {
                    contenidoFichero = "Fichero vacío, primero escribe en él. ";
                }

                textView.setText(contenidoFichero);
                Toast.makeText(this, "Se ha leído el fichero", Toast.LENGTH_SHORT).show();
                /*
                 textView.setText("");
                 comparaArchivos.getFichero1().llenaAleatorio(100); //llenamos con 10 int aleatorios
                 Toast.makeText(this, "Se ha llenado el fichero con 10 enteros ", Toast.LENGTH_SHORT).show();
                 */
                break;

            case (R.id.boton_comparaArchivosBinarios): //Comparamos ficheros actuales                

                boolean ficherosIguales = false;
        try {
            ficherosIguales = comparaArchivos.sonIguales();
        } catch (FileNotFoundException ex) {
            Log.v(TAG, "Error al comparar ficheros");
        }

                if (ficherosIguales == true) {
                    Toast.makeText(this, "Los ficheros binarios --> SON IGUALES", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Los ficheros binarios --> SON DIFERENTES", Toast.LENGTH_SHORT).show();
                }


                break;

        }//fin switch

    }//fin onClick
}//fin class
