package vic.men.lan.android;

import static android.app.Activity.RESULT_OK;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activity_SeleccionaFichero extends ListActivity {

    private File ruta;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);

        // Use the current directory as title
        ruta = Environment.getExternalStorageDirectory();
        path = ruta.getAbsolutePath();

        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);

        // Read all files sorted into the values-array
        List values = new ArrayList();
        File dir = new File(path);
        if (!dir.canRead()) {
            setTitle(getTitle() + " (inaccessible)");
        }
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                    values.add(file);
                }
            }
        }
        Collections.sort(values);

        // Put the data into the list
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String filename = (String) getListAdapter().getItem(position);
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }
        if (new File(filename).isDirectory()) { //si es un directorio
            //volvemos a mandar al mismo view pero ahora abriendo la carpeta o directorio seleccionado
            Intent intent = new Intent(this, Activity_SeleccionaFichero.class);
            intent.putExtra("path", filename);
            startActivity(intent);

            Toast.makeText(this, filename + " es un directorio, debes seleccionar archivo ", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, filename + " es un archivo, vamos a retornar su nombre", Toast.LENGTH_LONG).show();

            /*
             3. Cuando pulsemos sobre un elemento, se va a crear un Intent,
             el cual tendrá el String que hemos seleccionado.
             Una vez esté cargado, vamos a llamar al método setResult(), 
             el cual tiene la constante RESULT_OK, es decir, que el resultado que enviamos es correcto. 
             Si por el contrario quisieramos retroceder a la actividad anterior sin enviar nada, podríamos establecer RESULT_CANCELLED. 
             Como segundo argumento, recibe el Intent con los datos que hemos cargado en el. Una vez hecho esto, llamamos al método finish(),
             el cual finalizará la actividad y volverá a la primera actividad.
             */
            String nombreArchivo = filename; //Obtenemos el nombre del archivo archivo seleccionamos
            Intent data = new Intent(); //Creamos el intent
            data.setData(Uri.parse(nombreArchivo)); //establecemos el dato a devolver
            setResult(RESULT_OK, data); //devolvemos
            finish();
        }
    }
}
