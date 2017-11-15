/**
 * Clase que modelará y manejará ficheros binarios. Crear, escribir, leer y
 * comparar son de las acciones que podrá realizar esta clase
 */
package archivosBinariosAndroid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    public ArchivoBinario() {

        infoSD = new TarjetaSD();
        archivo = new File(infoSD.getRuta(), "archivo.txt"); //nombre por default del archivo
        contenido_archivo = ""; //por default no tiene contenido
        //inicializamos flujos
        fis = null;
        entrada = null;
        fos = null;
        salida = null;
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
        fis = null;
        entrada = null;
        fos = new FileOutputStream(archivo);
        salida = new DataOutputStream(fos);
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
        fis = null;
        entrada = null;
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
        //si tenemos SD disponible y podemos escribir sobre ella
        if (infoSD.TieneSD() && infoSD.PuedeEscribir()) {
            try {

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

    /**
     * Lee contenido de archivo binario. Devuelve un String con los elementos
     * 'formateados' listos para imprimir pero además almacena el contenido REAL
     * en el atributo contenido_archivo de la clase para hacer cálculos y/o
     * comparaciones
     *
     * @return contenido_fichero_formateado
     */
    public String leerFichero() {

        //boolean seLeyoCorrectamente = false; //informará si el fichero binario se leyó  correctamente con un 'true', si devuelve 'false' es que hubo alguna excepción
        String contenido_archivo_formateado = "";
        //PODEMOS TENER OTRO STRING PARA TENER EL CONTENIDO 'FORMATEADO' PARA IMPRIMIR Y LA VARIABLE DE ARRIBA UTILIZAR PARA LA COMPARACIÓN
        //si tenemos SD disponible
        if (infoSD.TieneSD()) {
            try {

                int n = 0; //irá leyendo int por int el archivo

                //LEEMOS ARCHIVO
                while (true) {
                    n = entrada.readInt();  //se lee siguiente Int en el fichero
                    this.contenido_archivo += n; //vamos concatenando los elementosC
                    contenido_archivo_formateado = contenido_archivo_formateado + n + " - "; //vamos concatenando los elementosC

                    //System.out.println(n);  //se muestra en pantalla
                }
                //seLeyoCorrectamente = true; //sólo si se ejecuta correctamente, devolvemos true

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

        //return seLeyoCorrectamente;
        return contenido_archivo_formateado;
    }

}//fin class ArchivoBinario
