/**
 * Clase que manejará memoria SD. Para poder escribir y leer en la tarjeta SD,
 * necesitamos saber ciertas características de la misma, esta clase nos lo
 * proporcionará.
 */
package archivosBinariosAndroid;

//También podría ir incrustada en la clase ArchivoBinario
import android.os.Environment;

public class TarjetaSD {

    //atributos SD
    private String estado; //estado en el que se encuentra la SD
    private boolean tieneSD; //informará si el teléfono cuenta con SD montada
    private boolean puedeEscribir; //informará si se puede escribir en la SD (necesario para escribir archivos)

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
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            tieneSD = true;
            puedeEscribir = false;
        } else {
            tieneSD = false;
            puedeEscribir = false;
        }
    }

    //getters y setters
    public TarjetaSD(String estado, boolean tieneSD, boolean puedeEscribir) {
        this.estado = estado;
        this.tieneSD = tieneSD;
        this.puedeEscribir = puedeEscribir;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isTieneSD() {
        return tieneSD;
    }

    public void setTieneSD(boolean tieneSD) {
        this.tieneSD = tieneSD;
    }

    public boolean isPuedeEscribir() {
        return puedeEscribir;
    }

    public void setPuedeEscribir(boolean puedeEscribir) {
        this.puedeEscribir = puedeEscribir;
    }

}//fin class TarjetaSD
