package archivosBinariosAndroid;

import android.util.Log;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ComparaArchivosBinarios {

    private static final String TAG = "MyActivity";

    //atributos de CompararArchivoBinarios
    private ArchivoBinario fichero1; //Ficheros a comparar entre ellos
    private ArchivoBinario fichero2;

    //constructores
    public ComparaArchivosBinarios() throws FileNotFoundException {
        //La clase ArchivoBinario se encarga de crear los archivos en la path de la SD
        fichero1 = new ArchivoBinario("binario1");
        fichero2 = new ArchivoBinario("binario2");
    }

    /**
     * Recibe los 2 ficheros a comparar.
     *
     * @param fichero1
     * @param fichero2
     * @throws FileNotFoundException
     */
    public ComparaArchivosBinarios(File fichero1, File fichero2) throws FileNotFoundException, IOException {
        //La clase ArchivoBinario se encarga de crear los archivos en la path de la SD
        this.fichero1 = new ArchivoBinario(fichero1);
        this.fichero2 = new ArchivoBinario(fichero2);
    }

    /**
     * Recibe los 2 nombres de ficheros a comparar.
     *
     * @param nombreFichero1
     * @param nombreFichero2
     */
    public ComparaArchivosBinarios(String nombreFichero1, String nombreFichero2) throws FileNotFoundException {
        this.fichero1 = new ArchivoBinario(nombreFichero1);
        this.fichero2 = new ArchivoBinario(nombreFichero2);
    }

    public ComparaArchivosBinarios(ArchivoBinario fichero1, ArchivoBinario fichero2) {
        this.fichero1 = fichero1;
        this.fichero2 = fichero2;
    }

    //*-----------------> Métodos que permiten comparar ficheros binario <-----------------   
    /**
     * Método que checa si los 2 ficheros son iguales. Comparar elemento por
     * elemento(en este caso int) y si encuentra uno diferente devuelve false,
     * sino encuentra diferentes, devuelve true
     *
     * @return boolean
     */
    public boolean sonIguales() throws FileNotFoundException {

        fichero1.setFis(null);
        fichero1.setEntrada(null);
        fichero2.setFis(null);
        fichero2.setEntrada(null);

        fichero1.setFis(new FileInputStream(fichero1.getArchivo()));
        fichero1.setEntrada(new DataInputStream(fichero1.getFis()));
        fichero2.setFis(new FileInputStream(fichero2.getArchivo()));
        fichero2.setEntrada(new DataInputStream(fichero2.getFis()));

        Log.v(TAG, "Entrando a sonIguales()...");
        //si los tamaños de los ficheros son diferente, no son iguales
        long tamañoFichero1 = this.fichero1.getTamañoArchivo();
        long tamañoFichero2 = this.fichero2.getTamañoArchivo();

        if (tamañoFichero1 != tamañoFichero2) {
            Log.v(TAG, "Tamaños diferentes, no son iguales los archivos...");
            return false;
        }

        //si los tamaños son iguales, vamos a comparar elemento por elemento
        //si encontramos algún diferente devolvemos false, sino true
        try {

            int valorActual1, valorActual2;

            while (true) {
                Log.v(TAG, "Entrando a while que recorre ficheros...");
                valorActual1 = fichero1.getEntrada().readInt();
                valorActual2 = fichero2.getEntrada().readInt();
                Log.v(TAG, "Comparando " + valorActual1 + " con " + valorActual2 + " ... ");
                if (valorActual1 != valorActual2) { //si encuentra algún dato diferente
                    Log.v(TAG, "DIFERENTES, ARCHIVOS NO SON IGUALES!!!!");
                    return false; //devolvemos que no son iguales
                }
            }//while (value1 >= 0);

        } catch (FileNotFoundException e) {
            //System.out.println(e.getMessage());
        } catch (EOFException e) {
            //System.out.println("Fin de fichero");
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        } finally {
            try {
                if (fichero1.getFis() != null && fichero2.getFis() != null) {
                    fichero1.getFis().close();
                    fichero2.getFis().close();
                }
                if (fichero1.getEntrada() != null && fichero2.getEntrada() != null) {
                    fichero1.getEntrada().close();
                    fichero2.getEntrada().close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        Log.v(TAG, "ARCHIVOS IGUALES!!!!");
        return true; //si no encuentra ningun valor diferente, devuelve 'true'
    }

    //necesitamos más métodos!!!!
    
    //getters y setters
    public ArchivoBinario getFichero1() {
        return fichero1;
    }

    public void setFichero1(ArchivoBinario fichero1) {
        this.fichero1 = fichero1;
    }
    
    public void setFichero1(File fichero1) throws FileNotFoundException
    {
        this.fichero1 = new ArchivoBinario(fichero1.getAbsolutePath());
    }

    public ArchivoBinario getFichero2() {
        return fichero2;
    }

    public void setFichero2(File fichero2) throws FileNotFoundException
    {
        this.fichero2 = new ArchivoBinario(fichero2.getAbsolutePath());
    }
    public void setFichero2(ArchivoBinario fichero2) {
        this.fichero2 = fichero2;
    }

}//fin class ComparaArchivosBinarios

/*
//->Métodos que tienen detalles(aún no están terminados)<-
    public int cuentaIguales() throws FileNotFoundException {

        //obtenemos el contenido de los ficheros
        String contenidoFichero1 = fichero1.getContenido_archivo(); //recordar que los elementos vienen separados por comas
        String contenidoFichero2 = fichero2.getContenido_archivo();

        //si alguno de los archivos aún no tienen datos, mandamos a leer para que se actualicen las variables 'contenido'
        if (contenidoFichero1.isEmpty() || contenidoFichero1.equals("")) {
            fichero1.leerFichero();
            cuentaIguales(); //y luego volvemos a llamar al método
        } else if (contenidoFichero2.isEmpty() || contenidoFichero2.equals("")) {
            fichero2.leerFichero();
            cuentaIguales(); //y luego volvemos a llamar al método
        }

        //System.out.println("\nContenido fichero1 -> "+contenidoFichero1);
        //Log.v(TAG, "\nContenido fichero1 -> " + contenidoFichero1);
        //System.out.println("Contenido fichero2 -> "+contenidoFichero2);
        //Log.v(TAG, "\nContenido fichero2 -> " + contenidoFichero2);
        //Contamos cantidad de caracteres iguales
        int iguales = 0;
        char actualCadena1 = ' ';
        char actualCadena2 = ' ';
        String contadas = ""; //aquí guardaremos los elementos(letras) que ya se contaron para no repetir

        String datosYaComparados = ""; //aquí iremos guardando los datos que ya se compararon con el resto para no repetir comparaciones

        for (int i = 0; i < contenidoFichero1.length(); i++) {
            actualCadena1 = contenidoFichero1.charAt(i);

            if (contieneDato(contadas, actualCadena1)) { //Checamos si ya contamos el valor actual, para no contarlo otras vez

                //No hacemos nada, sólo ignoramos el bucle
                //System.out.println(actualCadena1+" ya la contamos, así que la ignoramos...");
            } else { //pero si no la hemos contado
                contadas += actualCadena1; //La agregamos a las contadas 

                if (contieneDato(contenidoFichero2, actualCadena1) == true) {
                    //System.out.println("Comparten -> " + actualCadena1);
                    Log.v(TAG, "Comparten -> " + actualCadena1);
                    iguales++;
                }
            }

        }//fin for cuenta

        //System.out.println("\nCantidad de iguales: " + iguales);
        return iguales;
    }//fin cuentaIguales

    public ArrayList obtenerElementosCompartidos() throws FileNotFoundException {

        //obtenemos el contenido de los ficheros
        String contenidoFichero1 = fichero1.getContenido_archivo();
        String contenidoFichero2 = fichero2.getContenido_archivo();

        //si alguno de los archivos aún no tienen datos, mandamos a leer para que se actualicen las variables 'contenido'
        if (contenidoFichero1.isEmpty() || contenidoFichero1.equals("")) {
            fichero1.leerFichero();
            cuentaIguales(); //y luego volvemos a llamar al método
        } else if (contenidoFichero2.isEmpty() || contenidoFichero2.equals("")) {
            fichero2.leerFichero();
            cuentaIguales(); //y luego volvemos a llamar al método
        }

        //System.out.println("\nContenido fichero1 -> "+contenidoFichero1);
        //Log.v(TAG, "\nContenido fichero1 -> " + contenidoFichero1);
        //System.out.println("Contenido fichero2 -> "+contenidoFichero2);
        //Log.v(TAG, "\nContenido fichero2 -> " + contenidoFichero2);
        //Contamos cantidad de caracteres iguales
        int iguales = 0;
        char actualCadena1 = ' ';
        char actualCadena2 = ' ';
        String contadas = ""; //aquí guardaremos los elementos(letras) que ya se contaron para no repetir

        String datosYaComparados = ""; //aquí iremos guardando los datos que ya se compararon con el resto para no repetir comparaciones
        ArrayList elementos_compartidos = new ArrayList();

        for (int i = 0; i < contenidoFichero1.length(); i++) {
            actualCadena1 = contenidoFichero1.charAt(i);

            if (contieneDato(contadas, actualCadena1)) { //Checamos si ya contamos el valor actual, para no contarlo otras vez

                //No hacemos nada, sólo ignoramos el bucle
                //System.out.println(actualCadena1+" ya la contamos, así que la ignoramos...");
            } else { //pero si no la hemos contado
                contadas += actualCadena1; //La agregamos a las contadas 

                if (contieneDato(contenidoFichero2, actualCadena1) == true) {
                    //System.out.println("Comparten -> " + actualCadena1);
                    elementos_compartidos.add(actualCadena1); //agregamos a la estructura
                    Log.v(TAG, "Comparten -> " + actualCadena1);
                    iguales++;
                }
            }

        }//fin for cuenta

        //System.out.println("\nCantidad de iguales: " + iguales);
        return elementos_compartidos;
    }//fin cuentaIguales

    public int cuentaDiferentes() throws FileNotFoundException {

        //obtenemos el contenido de los ficheros
        String contenidoFichero1 = fichero1.getContenido_archivo();
        String contenidoFichero2 = fichero2.getContenido_archivo();

        //si alguno de los archivos aún no tienen datos, mandamos a leer para que se actualicen las variables 'contenido'
        if (contenidoFichero1.isEmpty() || contenidoFichero1.equals("")) {
            fichero1.leerFichero();
            cuentaIguales(); //y luego volvemos a llamar al método
        } else if (contenidoFichero2.isEmpty() || contenidoFichero2.equals("")) {
            fichero2.leerFichero();
            cuentaIguales(); //y luego volvemos a llamar al método
        }

        //System.out.println("\nContenido fichero1 -> "+contenidoFichero1);
        Log.v(TAG, "\nContenido fichero1 -> " + contenidoFichero1);
        //System.out.println("Contenido fichero2 -> "+contenidoFichero2);
        Log.v(TAG, "\nContenido fichero2 -> " + contenidoFichero2);

        //Contamos cantidad de caracteres iguales
        int diferentes = 0;
        char actualCadena1 = ' ';
        char actualCadena2 = ' ';
        String contadas = ""; //aquí guardaremos los elementos(letras) que ya se contaron para no repetir

        String datosYaComparados = ""; //aquí iremos guardando los datos que ya se compararon con el resto para no repetir comparaciones

        for (int i = 0; i < contenidoFichero1.length(); i++) {
            actualCadena1 = contenidoFichero1.charAt(i);

            if (contieneDato(contadas, actualCadena1)) { //Checamos si ya contamos el valor actual, para no contarlo otras vez

                //No hacemos nada, sólo ignoramos el bucle
                //System.out.println(actualCadena1+" ya la contamos, así que la ignoramos...");
            } else { //pero si no la hemos contado
                contadas += actualCadena1; //La agregamos a las contadas 

                if (contieneDato(contenidoFichero2, actualCadena1) == false) {
                    //System.out.println("Comparten -> " + actualCadena1);
                    Log.v(TAG, "Comparten -> " + actualCadena1);
                    diferentes++;
                }
            }

        }//fin for cuenta

        return diferentes;
    }//fin cuentaIguales
    //calcular el número de veces que se repite un carácter en un String

    private int contarCaracteres(String cadena, char caracter) {
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        return contador;
    }

    private boolean contieneDato(String cadena, char dato) {
        if (cadena.isEmpty()) {
            return false;
        }
        if (cadena.indexOf(dato) != -1) {
            return true;
        } else {
            return false;
        }
    }
*/