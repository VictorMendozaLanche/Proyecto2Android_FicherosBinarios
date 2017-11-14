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

public class MainActivity extends Activity implements OnClickListener {
    
    Button leersd;
    Button escribirsd;
    TextView textView;

    //con estos atributos controlamos el estado de la SD
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //instancia controles view
        leersd = (Button) findViewById(R.id.leersd);
        escribirsd = (Button) findViewById(R.id.escribirsd);
        textView = (TextView) findViewById(R.id.textView);

        //escucha a botonnes
        leersd.setOnClickListener(this);
        escribirsd.setOnClickListener(this);

        // Código que me comprueba si existe SD y si puedo escribir o no
        String estado = Environment.getExternalStorageState();
        
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        } else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
        
    }//fin onCreate()

    @Override
    public void onClick(View v) {
        
        switch (v.getId()) {
            //----->ESTOS SON LOS CASOS QUE NOS INTERESAN <------
            case (R.id.leersd): //escribir en SD
                if (sdDisponible) {
                    
                    FileInputStream fis = null;
                    DataInputStream entrada = null;
                    String contenido_archivo = ""; //almacenará contenido total del archivo

                    try {
                        //Apuntamos a dirección de la SD
                        File ruta_sd = Environment.getExternalStorageDirectory();
                        File f = new File(ruta_sd.getAbsolutePath(), "ficherosd1.txt");
                        
                        fis = new FileInputStream(f);
                        entrada = new DataInputStream(fis);
                        int n = 0; //irá leyendo int por int el archivo

                        //LEEMOS ARCHIVO
                        while (true) {
                            n = entrada.readInt();  //se lee  un entero del fichero
                            contenido_archivo += n;
                            //System.out.println(n);  //se muestra en pantalla
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (EOFException e) {
                        System.out.println("Fin de fichero");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        try {
                            if (fis != null) {
                                fis.close();
                            }
                            if (entrada != null) {
                                entrada.close();
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    
                    textView.setText(contenido_archivo);
                    
                }
                
                break;
            case (R.id.escribirsd):
                if (sdAccesoEscritura && sdDisponible) { //si la SD está disponible y podemos escribir

                    FileOutputStream fos = null;
                    DataOutputStream salida = null;
                    
                    try {
                        File ruta_sd = Environment.getExternalStorageDirectory();
                        File f = new File(ruta_sd.getAbsolutePath(), "ficherosd1.txt");
                        
                        fos = new FileOutputStream(f);
                        salida = new DataOutputStream(fos);
                        
                        for (int i = 0; i < 10; i++) {
                            salida.writeInt(i + 1); //se escribe el número entero en el fichero
                        }
                        
                        salida.close();
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        try {
                            if (fos != null) {
                                fos.close();
                            }
                            if (salida != null) {
                                salida.close();
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    
                    break;
                }
            
        }//fin switch

    }//fin onClick
}//fin class
