package vic.men.lan.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;

//-->Clase que dará acceso a la lógica para manejar Archivos binarios
import archivosBinariosAndroid.ArchivoBinario;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Activity_CrearFicheros extends Activity implements View.OnClickListener {

    //-->Controles View
    private EditText edittext_nombreArchivo;
    private EditText edittext_datosArchivo;
    private Button boton_creaFicheroAleatorio;
    private Button boton_creaConDatosIngresados;
    private Button boton_creaFicheroHastaDiez;
    private TextView textView_resultadosCrear;

    //-->Objetos que nos dará acceso a la lógica para crear ficheros binarios
    ArchivoBinario manejaBinarios;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_crearbinario);

        //-->Instancia controles View
        edittext_nombreArchivo = (EditText) findViewById(R.id.edittext_nombreArchivo);
        edittext_datosArchivo = (EditText) findViewById(R.id.edittext_datosArchivo);
        boton_creaConDatosIngresados = (Button) findViewById(R.id.boton_creaConDatosIngresados);
        boton_creaFicheroAleatorio = (Button) findViewById(R.id.boton_creaFicheroAleatorio);
        boton_creaFicheroHastaDiez = (Button) findViewById(R.id.boton_creaFicheroHastaDiez);
        textView_resultadosCrear = (TextView) findViewById(R.id.textView_resultadosCrear);

        //--> Inicializamos objeto para la lógica
        manejaBinarios = null; //aún no sabemos los datos del archivo

        //-->Escucha a botones
        boton_creaFicheroAleatorio.setOnClickListener(this);
        boton_creaConDatosIngresados.setOnClickListener(this);
        boton_creaFicheroHastaDiez.setOnClickListener(this);

        //-->Limpiamos mensajes 
        textView_resultadosCrear.setText("");

    }//fin onCreate

    //-->Maneja eventos onClick de los controles
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.boton_creaConDatosIngresados): //si damos clic en crear con datos ingresados por el usuario
                creaFicheroBinario(1); //mandamos a crear archivo por datos ingresados por el usuario
                break;
            case (R.id.boton_creaFicheroAleatorio): //si damos clic en crea fichero aleatorio
                creaFicheroBinario(2); //crea con enteros aleatorios
                //visualizaToast("Se ha creado correctamente el archivo " + nombre + " ve a visualizarlo y/o compararlo", 0);
                break;
            case (R.id.boton_creaFicheroHastaDiez):
                creaFicheroBinario(3); //crea con enteros 1-10
                break;

        }//fin switch
    }//fin onClick()

    /**
     * Crea fichero binario. El id ayudará a saber que botón se presionó, es
     * decir: 1. Si creamos con los datos que ingresó el usuario 2. De forma
     * aleatoria 3. Con enteros del 1-10
     *
     * @param id
     * @return
     */
    private boolean creaFicheroBinario(int id) {
        //obtenemos datos del archivo
        String nombre = edittext_nombreArchivo.getText().toString();
        String datos_nuevos = edittext_datosArchivo.getText().toString();

        //validaciones sencillas
        if (nombre.isEmpty()) {
            visualizaToast("¡Debes ingresar nombre del archivo!\nCon el formato -> nombreArchivo.txt o nombreArchivo.dat", 1);
            edittext_nombreArchivo.requestFocus(); //manda el foco al control ingresa nombre
            textView_resultadosCrear.append("\n\n-->Error: Debes ingresar un nombre del fichero!!!!\n\n");
            return false; //rompemos flujo
        }

        //una vez que todo es correcto, procedemos a crear fichero
        try {

            manejaBinarios = new ArchivoBinario(new File(nombre)); //creamos fichero nuevo

            //Aquí definimos como llenaremos el fichero
            if (id == 1) //llenamos con los datos que ingresó el usuario
            {
                //Obtenemos los datos ingresados por el usuario
                String datos[] = edittext_datosArchivo.getText().toString().split(" "); //Separamos los elementos
                int datosNuevosEnteros[] = new int[datos.length];
                //los agregamos a un arreglo de enteros, si hay una excepción se ingresaron mal los datos
                try {

                    for (int i = 0; i < datos.length; i++) {
                        datosNuevosEnteros[i] = Integer.parseInt(datos[i]);
                    }
                } catch (Exception e) {
                    visualizaToast("\nError al capturar los datos del fichero\nEl formato debe ser: Enteros separados por un espacio", 1);
                    textView_resultadosCrear.append("\n\n--->Error al capturar los datos del fichero\n    El formato debe ser: Enteros separados por un espacio\n\n");
                    edittext_datosArchivo.requestFocus();
                    return false;
                }
                manejaBinarios.Llena(datosNuevosEnteros);
            } else if (id == 2) //llenamos con aleatorios
            {
                manejaBinarios.llenaAleatorio(10);
            } else if (id == 3) //llenamos con 1-10
            {
                manejaBinarios.Llena(10);
            }

        } catch (FileNotFoundException ex) {
            textView_resultadosCrear.setText("Error al crear archivo, inténtalo de nuevo..." + ex.getMessage());
            return false; //rompemos flujo
        } catch (IOException ex) {
            Logger.getLogger(Activity_CrearFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Visualizamos resultados
        try {
            textView_resultadosCrear.append("\n\nSe creó el archivo --> " + manejaBinarios.getArchivo().getName()
                    + "\nRuta -->" + manejaBinarios.getAbsolutePath()
                    + "\nDatos --> " + manejaBinarios.leerFichero());
        } catch (FileNotFoundException ex) {
            textView_resultadosCrear.setText("Error al tratar de mostrar resultados de crear...");
            return false;
        }

        return true;
    }

    /**
     * Visualiza Toast con mensaje recibido. Si duracion = 1 --> Long duration
     * si duration = 0 --> Short duration
     *
     * @param mensaje
     * @param duracion
     */
    private void visualizaToast(String mensaje, int duracion) {
        Toast.makeText(this, mensaje,
                duracion).show();
    }
}//fin class
