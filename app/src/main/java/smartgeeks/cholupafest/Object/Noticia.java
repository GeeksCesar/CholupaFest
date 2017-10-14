package smartgeeks.cholupafest.Object;

/**
 * Created by CesarLiizcano on 26/09/2017.
 */

public class Noticia {

    int  idNotice;
    String titleNotice, descripcionNotice , urlImgNotice;


    public Noticia() {
    }



    public int getIdNotice() {
        return idNotice;
    }

    public void setIdNotice(int idNotice) {
        this.idNotice = idNotice;
    }

    public String getUrlImgNotice() {
        return urlImgNotice;
    }

    public void setUrlImgNotice(String urlImgNotice) {
        this.urlImgNotice = urlImgNotice;
    }

    public String getTitleNotice() {
        return titleNotice;
    }

    public void setTitleNotice(String titleNotice) {
        this.titleNotice = titleNotice;
    }

    public String getDescripcionNotice() {
        return descripcionNotice;
    }

    public void setDescripcionNotice(String descripcionNotice) {
        this.descripcionNotice = descripcionNotice;
    }
}
