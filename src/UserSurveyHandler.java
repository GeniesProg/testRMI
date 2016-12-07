import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


class UserSurveyHandler implements HttpHandler {

    public void handle(HttpExchange t) {
        String reponse = "<h1>Sondage</h1>";

        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();

        // Utilisation d'un flux pour lire les donn�es du message Http
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(t.getRequestBody(),"utf-8"));
        } catch(UnsupportedEncodingException e) {
            System.err.println("Erreur lors de la r�cup�ration du flux " + e);
            System.exit(-1);
        }
	
        // R�cup�ration des donn�es en POST
        try {
            query = br.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture d'une ligne " + e);
            System.exit(-1);
        }

        //-------------------ClientRMI------------------------//
 
    	Sondage so = null;

    	// Récupération de la personne distante
    	try {
    	    so = (Sondage)Naming.lookup("rmi://localhost/SurveyStock");
    	} catch(NotBoundException e) {
    	    System.err.println("Pas possible d'accéder à l'objet distant : " + e);
    	    System.exit(-1);
    	} catch(MalformedURLException e) {
    	    System.err.println("URL mal forme : " + e);
    	    System.exit(-1);
    	} catch(RemoteException e) {
    	    System.err.println("Pas possible d'accéder à l'objet distant : " + e);
    	    System.exit(-1);
    	}

    	try {
			System.out.println("Personne : " + so.getId() + " " + so.getTitre());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    	try {
			reponse+="<p>"+ so.getId() + " " + so.getTitre()+"</p>";
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        // Envoi de l'en-t�te Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.length());
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-t�te : " + e);
            System.exit(-1);
        }

        // Envoi du corps (donn�es HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
        }
    }

}