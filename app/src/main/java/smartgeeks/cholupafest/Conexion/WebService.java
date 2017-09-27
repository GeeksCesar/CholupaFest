package smartgeeks.cholupafest.Conexion;

/**
 * Created by CesarLiizcano on 21/09/2017.
 */

public class WebService {

    public static final String TAG = "Test";

    private static final String IP = "http://domiciliosgbc.com/";
    private static final String PROJECT = "webservice/";

    public static final String URL_DAY_VIERNES = IP + PROJECT + "viernes.json";
    public static final String URL_DAY_SABADO = IP + PROJECT + "sabado.json";
    public static final String URL_DAY_DOMINGO = IP + PROJECT + "domingo.json" ;

    public static final String URL_NOTICIAS = IP + PROJECT + "noticias.json" ;

    //VARIABLES
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPCION = "descripcion";
    public static final String HORA = "hora";
    public static final String IMG = "img";
}
