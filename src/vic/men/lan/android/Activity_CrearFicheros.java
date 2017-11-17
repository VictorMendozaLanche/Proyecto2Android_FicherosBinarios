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
    private Button boton_creaFichero;
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
        boton_creaFichero = (Button) findViewById(R.id.boton_creaFichero);
        textView_resultadosCrear = (TextView) findViewById(R.id.textView_resultadosCrear);

        //--> Inicializamos objeto para la lógica
        manejaBinarios = null; //aún no sabemos los datos del archivo

        //-->Escucha a botones
        boton_creaFichero.setOnClickListener(this);

    }//fin onCreate

    //-->Maneja eventos onClick de los controles
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.boton_creaFichero):

                //obtenemos datos del archivo
                String nombre = edittext_nombreArchivo.getText().toString();
                String datos_nuevos = edittext_datosArchivo.getText().toString();

                //validaciones sencillas
                if (nombre.isEmpty()) {
                    visualizaToast("¡Debes ingresar nombre del archivo!\nCon el formato -> nombreArchivo.txt o nombreArchivo.dat", 1);
                    edittext_nombreArchivo.requestFocus(); //manda el foco al control ingresa nombre
                    textView_resultadosCrear.setText("Error: Debes ingresar un nombre del fichero!!!!");
                    return; //rompemos flujo
                }

                //una vez que todo es correcto, procedemos a crear fichero
                try {

                    manejaBinarios = new ArchivoBinario(new File(nombre)); //creamos fichero nuevo
                    manejaBinarios.llenaAleatorio(10);
                } catch (FileNotFoundException ex) {
                    textView_resultadosCrear.setText("Error al crear archivo, inténtalo de nuevo..." + ex.getMessage());
                    return; //rompemos flujo
                } catch (IOException ex) {
                    Logger.getLogger(Activity_CrearFicheros.class.getName()).log(Level.SEVERE, null, ex);
                }

                textView_resultadosCrear.setText("Se ha creado correctamente el archivo " + nombre + " ve a visualizarlo...");
                visualizaToast("Se ha creado correctamente el archivo " + nombre + " ve a visualizarlo y/o compararlo", 0);

                break;

        }//fin switch
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
}//fin class
