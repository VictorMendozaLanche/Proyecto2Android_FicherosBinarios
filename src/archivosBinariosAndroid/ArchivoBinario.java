package archivosBinariosAndroid;

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

    private File archivo; //manejará fichero
    private TarjetaSD infoSD; //objeto que nos informará del estado de la SD
    private String contenido_archivo;

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
    }

    /**
     * Crea archivo binario con ruta de la sd + "nombreArchivo" recibido. Además
     * crea objeto que apunta a la info necesaria de la SD, así como inicializa
     * los flujos
     *
     * @param nombreArchivo
     */
    public ArchivoBinario(String nombreArchivo) throws FileNotFoundException {

        infoSD = new TarjetaSD();
        archivo = new File(infoSD.getRuta(), nombreArchivo); //nombre por default del archivo
        contenido_archivo = ""; //por default no tiene contenido
        //inicializamos flujos
        fos = new FileOutputStream(archivo);
        salida = new DataOutputStream(fos);
        fis = new FileInputStream(archivo);
        entrada = new DataInputStream(fis);
    }

    /**
     * Crea archivo binario a partir de File recibido. Crea archivo binario con
     * ruta de la sd + "nombreArchivo" recibido. Además crea objeto que apunta a
     * la info necesaria de la SD, así como inicializa los flujos
     *
     * @param archivo
     */
    public ArchivoBinario(File archivo) throws FileNotFoundException {

        infoSD = new TarjetaSD();
        this.archivo = new File(archivo.getPath(), archivo.getName()); //nombre por default del archivo
        contenido_archivo = ""; //por default no tiene contenido
        //inicializamos flujos
        fis = new FileInputStream(archivo);
        entrada = new DataInputStream(fis);
        fos = new FileOutputStream(archivo);
        salida = new DataOutputStream(fos);
    }

    //Métodos de archivos binarios
    /**
     * Llena el fichero con n enteros binarios. Devuelve true si se llenó
     * correctamente o false si hubo alguna excepción.
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

        fis = null;
        entrada = null;

        String contenido_formateado = "";
        int n;
        try {

            fis = new FileInputStream(archivo);
            entrada = new DataInputStream(fis);

            //LEEMOS ARCHIVO
            while (true) {
                n = entrada.readInt();  //se lee  un entero del fichero
                contenido_formateado = contenido_formateado + n + " - ";
                System.out.println(n);  //se muestra en pantalla
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

        return contenido_formateado;
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
}//fin class ArchivoBinario

/**
 * DESPUÉS VEMOS ESTA ALTERNATIVA!!! Clase que hereda de archivoBinario y
 * servirá para comparar 2 ficheros binarios. Hereda de clase ArchivoBinario por
 * tanto reutiliza la mayoría de elemnetos de clase Padre y
 *
 * @author WM
 */

/*
 class ComparaArchivosBinarios extends ArchivoBinario {

    
 public ComparaArchivosBinarios() {
 super();
 }

    
 }
 */
