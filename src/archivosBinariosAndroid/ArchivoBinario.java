package archivosBinariosAndroid;

import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase que modelará y manejará ficheros binarios. Crear, escribir, leer y
 * comparar son de las acciones que podrá realizar esta clase
 *
 * @author WM
 */
public class ArchivoBinario {

    /**
     * Auxiliar para mensajes Log en consola android.
     */
    private static final String TAG = "MyActivity";

    //-->Controles View
    private File archivo; //manejará fichero
    private TarjetaSD infoSD; //objeto que nos informará del estado de la SD
    private String contenido_archivo; //almacena el contenido del fichero como string

    //flujos que permitirán leer o escribir sobre el archivo binario
    private FileInputStream fis;
    private DataInputStream entrada;
    private FileOutputStream fos;
    private DataOutputStream salida;

    //constructores
    /**
     * Crea archivo binario con ruta de la sd + "archivo.txt". Además crea
     * objeto que apunta a la info necesaria de la SD, así como inicializa los
     * flujos
     */
    public ArchivoBinario() throws FileNotFoundException {

        infoSD = new TarjetaSD();
        archivo = new File(infoSD.getRuta(), "archivo.txt"); //nombre por default del archivo
        contenido_archivo = ""; //por default no tiene contenido
        //inicializamos flujos
        fis = null;
        entrada = null;
        fos = null;
        salida = null;
        fis = new FileInputStream(archivo);
        entrada = new DataInputStream(fis);

        //AQUÍ LLENAMOS CON LOS VALORES DESEADOS
        //Llena(10);
    }

    /**
     * Crea archivo binario con 'absolutePath' del archivo recibido. Además crea
     * objeto que apunta a la info necesaria de la SD, así como inicializa los
     * flujos
     *
     * @param nombreArchivo
     */
    public ArchivoBinario(String absolutePath) throws FileNotFoundException {

        infoSD = new TarjetaSD();
        archivo = new File(absolutePath); //nombre por default del archivo
        Log.v(TAG, "Tamaño del archivo reinicializado -> "+archivo.length());
        contenido_archivo = ""; //por default no tiene contenido
        //inicializamos flujos
        /*fos = new FileOutputStream(archivo);
        salida = new DataOutputStream(fos);
        fis = new FileInputStream(archivo);
        entrada = new DataInputStream(fis);
        */
        //AQUÍ LLENAMOS CON LOS VALORES DESEADOS
        //llenaAleatorio(10);
    }

    /**
     * Crea archivo binario a partir del nombre del archivo File recibido. Crea
     * archivo binario con ruta de la sd + "nombreArchivo" recibido. Además crea
     * objeto que apunta a la info necesaria de la SD, así como inicializa los
     * flujos
     *
     * @param archivo
     */
    public ArchivoBinario(File nombreArchivo) throws FileNotFoundException, IOException {

        infoSD = new TarjetaSD();
        this.archivo = new File(infoSD.getRuta(), nombreArchivo.toString()); //nombre por default del archivo
        if (this.archivo.exists() == false) {
            this.archivo.createNewFile();
        }
        contenido_archivo = ""; //por default no tiene contenido
        //inicializamos flujos
        fis = new FileInputStream(this.archivo);
        entrada = new DataInputStream(fis);
        fos = new FileOutputStream(this.archivo);
        salida = new DataOutputStream(fos);

        //AQUÍ LLENAMOS CON LOS VALORES DESEADOS
        //Llena(10);
    }

    //Métodos de archivos binarios
    /**
     * Llena fichero con int del 1-100.
     *
     * @return boolean
     */
    public boolean Llena() {

        boolean seLlenoCorrectamente = false; //informará si el fichero binario se llenó correctamente con un 'true', si devuelve 'false' es que hubo alguna excepción

        fos = null;
        salida = null;

        //si tenemos SD disponible y podemos escribir sobre ella
        if (infoSD.TieneSD() && infoSD.PuedeEscribir()) {
            try {

                fos = new FileOutputStream(archivo);
                salida = new DataOutputStream(fos);

                //Escribimos 1-100 en el fichero
                for (int i = 0; i < 100; i++) {
                    salida.writeInt((i + 1)); //escribe del 1-100
                }

                salida.close(); //cerramos flujo de escritura
                seLlenoCorrectamente = true; //sólo si se ejecuta correctamente, devolvemos true

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                //Cerramos flujos 
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
        }

        return seLlenoCorrectamente;
    }

    /**
     * Llena fichero con int del 1-n recibido.
     *
     * @param cantidad_enteros
     * @return
     */
    public boolean Llena(int cantidad_enteros) {

        Log.v(TAG, "Llenando archivo llamado "+this.archivo.getName()+ " ...");
        boolean seLlenoCorrectamente = false; //informará si el fichero binario se llenó correctamente con un 'true', si devuelve 'false' es que hubo alguna excepción

        fos = null;
        salida = null;

        //si tenemos SD disponible y podemos escribir sobre ella
        if (infoSD.TieneSD() && infoSD.PuedeEscribir()) {
            try {

                fos = new FileOutputStream(archivo);
                salida = new DataOutputStream(fos);

                //Escribimos 1-100 en el fichero
                for (int i = 0; i < cantidad_enteros; i++) {
                    salida.writeInt((i + 1)); //escribe del 1-100
                    Log.v(TAG, "Se agregó -> "+(i+1)+ " ...");
                }

                salida.close(); //cerramos flujo de escritura
                seLlenoCorrectamente = true; //sólo si se ejecuta correctamente, devolvemos true

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                //Cerramos flujos 
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
        }

        return seLlenoCorrectamente;
    }

    /**
     * Llena fichero con los elementos del arreglo de enteros recibido.
     *
     * @param datos_nuevos
     * @return boolean
     */
    public boolean Llena(int datos_nuevos[]) {

        Log.v(TAG, "Vamos a llenar el archivo " + this.getName() + " con los datos recibidos...");
        boolean seLlenoCorrectamente = false; //informará si el fichero binario se llenó correctamente con un 'true', si devuelve 'false' es que hubo alguna excepción

        fos = null;
        salida = null;

        //si tenemos SD disponible y podemos escribir sobre ella
        if (infoSD.TieneSD() && infoSD.PuedeEscribir()) {
            try {

                fos = new FileOutputStream(archivo);
                salida = new DataOutputStream(fos);

                //Escribimos 1-100 en el fichero
                for (int i = 0; i < datos_nuevos.length; i++) {
                    salida.writeInt(datos_nuevos[i]); //escribe del 1-100
                    Log.v(TAG, "Se escribió -> " + datos_nuevos[i]);
                }

                salida.close(); //cerramos flujo de escritura
                seLlenoCorrectamente = true; //sólo si se ejecuta correctamente, devolvemos true

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                //Cerramos flujos 
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
        }

        return seLlenoCorrectamente;
    }

    /**
     * Llena el fichero con n aleatorios enteros de forma binaria. Devuelve true
     * si se llenó correctamente o false si hubo alguna excepción.
     *
     * @param cantidad_de_aleatorios
     * @return
     */
    public boolean llenaAleatorio(int cantidad_de_aleatorios) {

        boolean seLlenoCorrectamente = false; //informará si el fichero binario se llenó correctamente con un 'true', si devuelve 'false' es que hubo alguna excepción

        fos = null;
        salida = null;

        //si tenemos SD disponible y podemos escribir sobre ella
        if (infoSD.TieneSD() && infoSD.PuedeEscribir()) {
            try {

                fos = new FileOutputStream(archivo);
                salida = new DataOutputStream(fos);

                //Escribimos n números aleatorios
                int n = 10;
                int numero_aleatorio = 0; //aquí guardaremos el número aleatorio que se va generando

                for (int i = 0; i < cantidad_de_aleatorios; i++) {
                    numero_aleatorio = (int) (Math.random() * n) + 1; //<- Genera números aleatorios entre el 1-10
                    salida.writeInt(numero_aleatorio); //se escribe el número aleatorio generado en el fichero
                }

                salida.close(); //cerramos flujo de escritura
                seLlenoCorrectamente = true; //sólo si se ejecuta correctamente, devolvemos true

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                //Cerramos flujos 
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
        }

        return seLlenoCorrectamente;
    }

    //Agregar más formas de llenar el fichero(String, float, etc...)
    /**
     * Lee contenido de archivo binario. Devuelve un String con los elementos
     * 'formateados' listos para imprimir pero además almacena el contenido REAL
     * en el atributo contenido_archivo de la clase para hacer cálculos y/o
     * comparaciones
     *
     * @return contenido_fichero_formateado
     */
    public String leerFichero() throws FileNotFoundException {

        Log.v(TAG, "Vamos a leer el archivo " + this.archivo.getName() + " ...");
        fis = null;
        entrada = null;
        contenido_archivo = "";

        String contenido_formateado = "";
        int n;
        try {

            fis = new FileInputStream(this.archivo);
            entrada = new DataInputStream(fis);

            //LEEMOS ARCHIVO
            while (true) {
                n = entrada.readInt();  //se lee  un entero del fichero
                Log.v(TAG, "Se leyó " + n + "...");
                contenido_archivo = contenido_archivo + n; //
                contenido_formateado = contenido_formateado + n + " - ";
            }
        } catch (FileNotFoundException e) {
            //System.out.println(e.getMessage());
            Log.v(TAG, "ERROR VIC: " + e.getMessage());
        } catch (EOFException e) {
            //System.out.println("Fin de fichero");
            Log.v(TAG, "Terminamos de leer el archivo: " + e.getMessage());
        } catch (IOException e) {
            Log.v(TAG, "ERROR VIC: " + e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                //System.out.println(e.getMessage());
                Log.v(TAG, "ERROR VIC: " + e.getMessage());
            }
        }

        return contenido_formateado;
    }

    //Métodos que ayudarán a la comparación de archivos
    /**
     * Método que retorna true si es un archivo(no un directorio), false si no
     * lo es.
     *
     * @return boolean
     */
    public boolean isFile() {
        return this.archivo.isFile(); //checa si el objeto con que se llama es File o no
    }

    /**
     * Método que retorna true si es un directorio(no un archivo), false si no
     * lo es.
     *
     * @return boolean
     */
    public boolean isDirectory() {
        return this.archivo.isDirectory();
    }

    /**
     * Obtiene nombre del archivo.
     *
     * @return String
     */
    public String getName() {
        return this.archivo.getName();
    }

    /**
     * Obtiene la absolutePath del archivo.
     *
     * @return String
     */
    public String getAbsolutePath() {
        return this.archivo.getAbsolutePath();
    }

    //Agregar más formas de leer el fichero(String, float, etc...)
    //Métodos para comparación de ficheros
    //getters y setters necesarios
    public TarjetaSD getInfoSD() {
        return infoSD;
    }

    public void setInfoSD(TarjetaSD infoSD) {
        this.infoSD = infoSD;
    }

    public String getContenido_archivo() {
        return contenido_archivo;
    }

    public void setContenido_archivo(String contenido_archivo) {
        this.contenido_archivo = contenido_archivo;
    }

    public FileInputStream getFis() {
        return fis;
    }

    public void setFis(FileInputStream fis) {
        this.fis = fis;
    }

    public DataInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

    public FileOutputStream getFos() {
        return fos;
    }

    public void setFos(FileOutputStream fos) {
        this.fos = fos;
    }

    public DataOutputStream getSalida() {
        return salida;
    }

    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    public long getTamañoArchivo() {
        return this.archivo.length();
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

}//fin class ArchivoBinario
