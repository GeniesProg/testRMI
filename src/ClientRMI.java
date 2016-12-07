import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

/**
 * Client permettant d'interroger la personne sur le serveur distant.
 * @author Cyril Rabat
 * @version 22/10/2013
 */
public class ClientRMI {

    /**
     * Méthode principale.
     * @param args inutilisé
     */
    public static void main(String[] args) {
	ArrayList<ISondage> so = null;

	// Récupération du sondage distant
	try {
	    so = (ArrayList<ISondage>)Naming.lookup("rmi://localhost/sondages");
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

	for (ISondage sondage : so) {
		try {
			System.out.println(sondage.getTitre());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    }

}