/*
 * Clase que manejará memoria SD. Para poder escribir y leer en la tarjeta SD
 * necesitamos saber ciertas características de la misma, esta clase nos lo
 * proporcionará.
 */
package archivosBinariosAndroid;

//También podría ir incrustada en la clase ArchivoBinario
import android.os.Environment;
import java.io.File;

public class TarjetaSD {

    //atributos SD
    private String estado; //estado en el que se encuentra la SD
    private boolean tieneSD; //informará si el teléfono cuenta con SD montada
    private boolean puedeEscribir; //informará si se puede escribir en la SD (necesario para escribir archivos)
    private File ruta; //nos dará la ruta Absoluta de la SD, necesaria para crear en ella los ficheros

    //constructores SD    
    /**
     * Constructor por defecto que obtiene las características necesarias de la
     * SD. Características necesarias de conocer para leer y escribir archivos.
     */
    public TarjetaSD() {
        estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            tieneSD = true;
            puedeEscribir = true;
            ruta = Environment.getExternalStorageDirectory();
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            tieneSD = true;
            puedeEscribir = false;
            ruta = Environment.getExternalStorageDirectory();
        } else {
            tieneSD = false;
            puedeEscribir = false;
        }
    }

    public TarjetaSD(String estado, boolean tieneSD, boolean puedeEscribir) {
        this.estado = estado;
        this.tieneSD = tieneSD;
        this.puedeEscribir = puedeEscribir;
    }

    //getters y setters
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean TieneSD() {
        return tieneSD;
    }

    public void setTieneSD(boolean tieneSD) {
        this.tieneSD = tieneSD;
    }

    public boolean PuedeEscribir() {
        return puedeEscribir;
    }

    public void setPuedeEscribir(boolean puedeEscribir) {
        this.puedeEscribir = puedeEscribir;
    }

    public File getRuta() {
        return ruta;
    }

    public void setRutaSD(File ruta) {
        this.ruta = ruta;
    }

}//fin class TarjetaSD
