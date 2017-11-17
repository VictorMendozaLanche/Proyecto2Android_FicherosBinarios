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

        //Apuntamos a path de la SD
        ruta = Environment.getExternalStorageDirectory();
        path = ruta.getAbsolutePath();

        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }

        //Ponemos de título de path actual
        setTitle(path);

        //Lee todos los archivos del directorio actual y los ordena
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

        //Ordena los archivos/directorios de la path actual
        Collections.sort(values);

        //Visualizamos elementos en la lista
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, values);
        setListAdapter(adapter);
    }

    /**
     * Método que maneja evento de dar clic sobre un elemento.
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //Obtiene el nombre del item seleccionado
        String filename = (String) getListAdapter().getItem(position);

        //Maneja separador adecuado
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }

        //Checa si es un directorio o un archivo
        if (new File(filename).isDirectory()) { //si es un directorio...
            //volvemos a mandar al mismo view pero ahora abriendo la carpeta o directorio seleccionado
            Intent intent = new Intent(this, Activity_SeleccionaFichero.class);
            intent.putExtra("path", filename);
            startActivity(intent);
            Toast.makeText(this, filename + " es un directorio, ¡Debes seleccionar un archivo(.txt o .dat)!", Toast.LENGTH_SHORT).show();
        } else { //si es un archivo
            //Devolvemos el filename(ruta completa del archivo) a MainActivity 
            Intent data = new Intent();
            data.setData(Uri.parse(filename));
            setResult(RESULT_OK, data);
            finish();
        }
    }//fin onListItemClick()
}
