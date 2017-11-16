package vic.men.lan.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//Importamos clase que nos da acceso a la lógica de la app
import archivosBinariosAndroid.ArchivoBinario;
import archivosBinariosAndroid.ComparaArchivosBinarios;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity implements OnClickListener {

    /**
     * Auxiliar para mensajes Log en consola android.
     */
    private static final String TAG = "MyActivity";
    /**
     * Ayuda para retorno de información de activitySeleccionaArchivo. 1. Como
     * podemos ver, tenemos una variable de instancia de tipo int llamada
     * request_code, la cual será usada mas tarde en el método onActivityResult
     * para comprobar lo que ha llegado
     */
    private int request_code = 1;
    /**
     * Ayudará a saber si seleccionamos fichero1 o fichero2. 1 -> fichero1 2 ->
     * fichero2
     */
    private int archivoSeleccionado;

    //-->Controles View
    private TextView textView_presentacionApp;
    private TextView textView_mensaje0;
    private TextView textView_mensaje1;
    private TextView textView_mensaje21;
    private TextView textView_mensaje22;
    private TextView textView_mensaje3;
    private EditText editText_nombreBinario1;
    private EditText editText_nombreBinario2;
    private EditText edittext_resultadosComparacion;
    private Button boton_buscarBinario1;
    private Button boton_buscarBinario2;
    private Button boton_modificarFicherosBinarios;
    private Button boton_crearFicherosBinarios;
    private LinearLayout contenedor_presentacion; //contendrá elementos del mes
    private Button boton_compararAhoraSi;

    //-->Objeto que dará acceso a la lógica para manejar Archivos binarios
    private ComparaArchivosBinarios comparaArchivos;
    private ArchivoBinario binario1;//Archivos binarios que se procesarán...
    private ArchivoBinario binario2;

    //-->Oncreate...
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //-->Instancia controles View
        textView_presentacionApp = (TextView) findViewById(R.id.textView_presentacion);
        textView_mensaje0 = (TextView) findViewById(R.id.textView_mensaje0);
        textView_mensaje1 = (TextView) findViewById(R.id.textView_mensaje1);
        textView_mensaje21 = (TextView) findViewById(R.id.textView_mensaje21);
        textView_mensaje22 = (TextView) findViewById(R.id.textView_mensaje22);
        textView_mensaje3 = (TextView) findViewById(R.id.textView_mensaje3);
        editText_nombreBinario1 = (EditText) findViewById(R.id.edittext_nombreBinario1);
        editText_nombreBinario2 = (EditText) findViewById(R.id.edittext_nombreBinario2);
        boton_buscarBinario1 = (Button) findViewById(R.id.boton_buscarBinario1);
        boton_buscarBinario2 = (Button) findViewById(R.id.boton_buscarBinario2);
        edittext_resultadosComparacion = (EditText) findViewById(R.id.edittext_resultadosComparacion);
        boton_modificarFicherosBinarios = (Button) findViewById(R.id.boton_modificarFicherosBinarios);
        boton_crearFicherosBinarios = (Button) findViewById(R.id.boton_crearFicherosBinarios);
        contenedor_presentacion = (LinearLayout) findViewById(R.id.contenedor_presentacion);
        boton_compararAhoraSi = (Button) findViewById(R.id.boton_compararAhoraSi);

        //inicializamos archivos binarios y objeto comparador
        try {
            //por defecto se llenan con valores del 1-10 (esta instrucción viene en el contructor)
            binario1 = new ArchivoBinario("binario1.txt");
            binario2 = new ArchivoBinario("binario2.txt");

            comparaArchivos = new ComparaArchivosBinarios(binario1, binario2); //inicializamos con 2 ficheros por defecto, esto va a cambiar cualdo hayamos obtenido los 2 a procesar
            archivoSeleccionado = 0;
        } catch (Exception e) {
            edittext_resultadosComparacion.setText("Error al crear los archivos");
        }

        //-->Escucha a botonnes
        boton_buscarBinario1.setOnClickListener(this);
        boton_buscarBinario2.setOnClickListener(this);
        boton_modificarFicherosBinarios.setOnClickListener(this);
        boton_crearFicherosBinarios.setOnClickListener(this);
        boton_compararAhoraSi.setOnClickListener(this);

        //leersd.setOnClickListener(this);
        //-->Escucha de controles View
        //Oculta mensaje de bienvenida(textView) al evento onLongClick
        textView_presentacionApp.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {
                textView_presentacionApp.setAlpha(0);
                textView_mensaje0.setAlpha(0);
                contenedor_presentacion.setVisibility(View.GONE); //ocultamos layout que contiene la presentación
                return true;
            }
        });

    }//fin onCreate()

    //-->Maneja eventos onClick de los controles
    public void onClick(View v) {

        Intent intento = null; //Intent que permitirá redireccionar a otro Activity

        switch (v.getId()) { //Checamos que control desencadenó el evento..
            case (R.id.boton_crearFicherosBinarios):
                intento = new Intent(this, Activity_CrearFicheros.class);
                startActivity(intento);
                visualizaToast("Vamos a crear ficheros binarios...", 0);

                break;
            case (R.id.boton_buscarBinario1):
                visualizaToast("Abre fichero1(previamente creado) de " + comparaArchivos.getFichero1().getInfoSD().getRuta() + " ...", 0);

                archivoSeleccionado = 1; //indicamos que seleccionamos el fichero1
                /*
                 * Como retornamos información. 2. En el método onClick, el cual
                 * se ejecuta al pulsar en el Button, vemos que no usamos la
                 * llamada mediante el método startActivity, sino que usamos el
                 * método startActivityForResult(). Este método inicia una
                 * actividad de la cual queremos retornar un valor a la
                 * actividad que la ha llamado.
                 *
                 */
                intento = new Intent(this, Activity_SeleccionaFichero.class);
                startActivityForResult(intento, request_code);

                break;

            case (R.id.boton_buscarBinario2):
                visualizaToast("Abre fichero2(previamente creado) de " + comparaArchivos.getFichero1().getInfoSD().getRuta() + " ...", 0);

                archivoSeleccionado = 2; //indicamos que seleccionamos el fichero2
                /*
                 * Como retornamos información. 2. En el método onClick, el cual
                 * se ejecuta al pulsar en el Button, vemos que no usamos la
                 * llamada mediante el método startActivity, sino que usamos el
                 * método startActivityForResult(). Este método inicia una
                 * actividad de la cual queremos retornar un valor a la
                 * actividad que la ha llamado.
                 *
                 */
                intento = new Intent(this, Activity_SeleccionaFichero.class);
                startActivityForResult(intento, request_code);
                break;

            case (R.id.boton_modificarFicherosBinarios):
                modificaBinario();
                break;

            case (R.id.boton_compararAhoraSi):

                //validamos que ya tengamos la path de los archivos a comparar en la view
                if (editText_nombreBinario1.getText().toString().isEmpty() || editText_nombreBinario1.getText().toString().isEmpty()) {
                    edittext_resultadosComparacion.setText("Error, antes de comparar debes crear y/o abrir ficheros binarios");
                    visualizaToast("Error, antes de comparar debes crear y/o abrir ficheros binarios", 1);
                    return;
                }

                //si todo bien, mandamos a calcular y visualizar
                edittext_resultadosComparacion.setText(""); //limpiamos
                String resultadoComparacion = "";
                try {
                    resultadoComparacion = obtenerResultadosComparacion();
                    edittext_resultadosComparacion.setText(resultadoComparacion);
                } catch (FileNotFoundException ex) {
                    edittext_resultadosComparacion.setText("Error al realizar comparación de archivos");
                }

                break;

        }
    }//fin onClick()

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

    /**
     * Infor sobre el estado de la opción 'Modificar binario'.
     */
    private void modificaBinario() {

        Toast.makeText(this, "Sólo disponible para la versión Premium", Toast.LENGTH_LONG).show();
    }

    /**
     * Retorna String con todos los resultados de la comparación de los
     * ficheros.
     *
     * @return
     */
    private String obtenerResultadosComparacion() throws FileNotFoundException {
        String resultado = "";

        //Obtenemos el contenido de ambos archivos
        String contenidoFichero1 = comparaArchivos.getFichero1().leerFichero();
        String contenidoFichero2 = comparaArchivos.getFichero2().leerFichero();

        //Llenando si ambos ficheros son iguales
        String iguales = "";
        if (comparaArchivos.sonIguales() == true) {
            iguales = "\n\t----->LOS FICHEROS SON IGUALES<-----";
        } else {
            iguales = "\t----->LOS FICHEROS SON DIFENTES<-----";
        }
        resultado += iguales;

        //Llenando info de fichero1
        resultado += "\n\nFICHERO 1: \n"
                + "Absolute Path: " + comparaArchivos.getFichero1().getAbsolutePath() + "\n"
                + " Nombre: " + comparaArchivos.getFichero1().getName() + "\n"
                + " Tamaño en bytes: " + comparaArchivos.getFichero1().getTamañoArchivo() + "\n"
                + " Elementos: " + comparaArchivos.getFichero1().leerFichero();

        //LLenamos info de fichero2
        resultado += "\n\nFICHERO 2: \n"
                + "Absolute Path: " + comparaArchivos.getFichero2().getAbsolutePath() + "\n"
                + " Nombre: " + comparaArchivos.getFichero2().getName() + "\n"
                + " Tamaño en bytes: " + comparaArchivos.getFichero2().getTamañoArchivo() + "\n"
                + " Elementos: " + comparaArchivos.getFichero2().leerFichero();

        return resultado;
    }

    /**
     * Método que obtiene el valor devuelto por SeleccionarFicheroActivity e
     * imprime en editText correspondiente. Este método el cual se encuentra en
     * la primera actividad, es el encargado de gestionar el Intent que hemos
     * recibido de la segunda actividad.
     *
     * Este método trae consigo los parámetros requestCode, resultCode y data.
     * Los 2 primeros parámetros los usaremos para comprobar, es decir, si el
     * requestCode es igual que el que definimos en la clase de la actividad, y
     * si el resultCode tiene el mismo valor que la constante RESULT_OK,
     * entonces extraemos los datos que trae consigo el Intent.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK)) {
            //tvTexto.setText(data.getDataString());

            //Mandamos a visualizar al editText correspondiente el nombre del archivo seleccionado
            if (archivoSeleccionado == 1) {
                editText_nombreBinario1.setText(data.getDataString());
                editText_nombreBinario1.requestFocus();
            } else {
                editText_nombreBinario2.setText(data.getDataString());
                editText_nombreBinario1.requestFocus();
            }

        }
    }//fin onActivityResult

}//fin class

/**
 * OLD ONCLICK
 *
 * @Override public void onClick(View v) {
 *
 * String contenidoFichero = "";
 *
 * switch (v.getId()) { //----->ESTOS SON LOS CASOS QUE NOS INTERESAN <------
 * case (R.id.leersd): //leer SD
 *
 * textView.setText("");
 *
 * {
 * try {
 * contenidoFichero = comparaArchivos.getFichero1().leerFichero();
 * } catch (FileNotFoundException ex) {
 * Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
 * }
 * }
 *
 * if (contenidoFichero.isEmpty() || contenidoFichero.equalsIgnoreCase("")) {
 * contenidoFichero = "Fichero vacío, primero escribe en él. ";
 * }
 *
 * textView.setText(contenidoFichero);
 * Toast.makeText(this, "Se ha leído el fichero", Toast.LENGTH_SHORT).show();
 *
 * break;
 *
 * case (R.id.escribirsd): //Escribir en SD.
 * textView.setText("");
 *
 * {
 * try {
 * contenidoFichero = comparaArchivos.getFichero2().leerFichero();
 * } catch (FileNotFoundException ex) {
 * Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
 * }
 * }
 *
 * if (contenidoFichero.isEmpty() || contenidoFichero.equalsIgnoreCase("")) {
 * contenidoFichero = "Fichero vacío, primero escribe en él. ";
 * }
 *
 * textView.setText(contenidoFichero);
 * Toast.makeText(this, "Se ha leído el fichero", Toast.LENGTH_SHORT).show();
 * /*
 * textView.setText("");
 * comparaArchivos.getFichero1().llenaAleatorio(100); //llenamos con 10 int aleatorios
 * Toast.makeText(this, "Se ha llenado el fichero con 10 enteros ", Toast.LENGTH_SHORT).show();
 *
 * break;
 *
 * case (R.id.boton_comparaArchivosBinarios): //Comparamos ficheros actuales      *
 * boolean ficherosIguales = false;
 * try {
 * ficherosIguales = comparaArchivos.sonIguales();
 * } catch (FileNotFoundException ex) {
 * Log.v(TAG, "Error al comparar ficheros");
 * }
 *
 * if (ficherosIguales == true) {
 * Toast.makeText(this, "Los ficheros binarios --> SON IGUALES",
 * Toast.LENGTH_SHORT).show(); } else { Toast.makeText(this, "Los ficheros
 * binarios --> SON DIFERENTES", Toast.LENGTH_SHORT).show(); }
 *
 * Intent intento = new Intent(this, Activity_SeleccionaFichero.class);
 *
 * //ENVIAR INFO AL OTRO ACTIVITY //id, variable_a_enviar
 * //intento.putExtra(Activity_SeleccionaFichero.datoExtra, nombre);
 * startActivity(intento); Toast.makeText(this, "Abre un archivo .txt",
 * Toast.LENGTH_SHORT).show(); break;
 *
 * }//fin switch
 *
 * }//fin onClick
 */
