import java.io.IOException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

/**
 * Classe correspondant à un serveur Http simple.
 * Le serveur �coute sur le port 8080 sur le contexte 'usersurvey.html'.
 * @author Cyril Rabat
 * @version 2015/06/25
 */
public class ServeurPortailBackOffice {

    public static void main(String[] args) {	
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la cr�ation du serveur " + e);
            System.exit(-1);
        }

        serveur.createContext("/usersurvey.html", new UserSurveyHandler());
        serveur.setExecutor(null);
        serveur.start();
        
	System.out.println("Serveur d�marr�. Pressez CRTL+C pour arr�ter.");
    }

}