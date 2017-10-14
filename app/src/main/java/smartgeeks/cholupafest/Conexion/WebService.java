package smartgeeks.cholupafest.Conexion;

/**
 * Created by CesarLiizcano on 21/09/2017.
 */

public class WebService {

    public static final String TAG = "Test";

    private static final String IP = "http://smartgeeks.com.co/cholupafest/";
    private static final String PROJECT = "Webservice/";


    public static final String URL_NOTICIAS = IP + PROJECT + "noticias.json" ;

    public static final String SET_USUARIO = IP + PROJECT + "setUsuario.php";
    public static final String SET_ASISTENCIA = IP + PROJECT + "setAsistencia.php";
    public static final String CONSULTAR_ACTIVIDAD = IP + PROJECT + "consultarActividad.php?dia=";
    public static final String CONSULTAR_ASISTENTES = IP + PROJECT + "consultarAsistentes.php?idActividad=";
    public static final String CONSULTAR_NOTICIAS = IP + PROJECT + "consultarNoticias.php" ;
    public static final String CONSULTAR_CODIGO = IP + PROJECT + "consultarCodigo.php?codigo=";
    public static final String CONSULTAR_MAPA = IP + PROJECT + "mapa.php" ;


    //VARIABLES
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final String HORA = "hora";
    public static final String IMG = "img";
    public static final String MAX = "maxActividad";
    public static final String DIA = "dia" ;
    public static final String WEB = "web";
}
