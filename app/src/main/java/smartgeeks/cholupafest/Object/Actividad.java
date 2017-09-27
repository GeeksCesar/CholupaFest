package smartgeeks.cholupafest.Object;

/**
 * Created by CesarLiizcano on 21/09/2017.
 */

public class Actividad {

    int idCronograma;
    String title, hora, descripcion, UrlImgActividad;

    public Actividad() {
    }

    public Actividad(int idCronograma, String title, String hora, String descripcion, String urlImgActividad) {
        this.idCronograma = idCronograma;
        this.title = title;
        this.hora = hora;
        this.descripcion = descripcion;
        UrlImgActividad = urlImgActividad;
    }

    public String getUrlImgActividad() {
        return UrlImgActividad;
    }

    public void setUrlImgActividad(String urlImgActividad) {
        UrlImgActividad = urlImgActividad;
    }

    public int getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(int idCronograma) {
        this.idCronograma = idCronograma;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
