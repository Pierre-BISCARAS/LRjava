package lr;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lr.format.simple.FormatSimple;

public class LR {
    static final int LARGEUR = 1980;
    static final int HAUTEUR = 1080;
    static final int NBRAYONS = 100;
    static final int NIVEAU = 2;
    static final int NOMBRE_THREADS = 7; // Nombre de threads dans le pool

    public static void main(String[] args) {
        /*
         * Méthode principale qui lance le calcul d'illumination d'une scène et qui enregistre l'image
         */

        Renderer r = new Renderer(LARGEUR, HAUTEUR);
        Scene sc = new FormatSimple().charger("../src/web/uploads/simple.txt");

        sc.display();
        r.setScene(sc);
        r.setNiveau(NIVEAU);

        // Création du pool de threads
        ExecutorService threadPool = Executors.newFixedThreadPool(NOMBRE_THREADS);

        // Lancement des threads pour calculer les lignes de l'image
        for (int i = 0; i < HAUTEUR; i++) {
            ParalleleRenderer renderer = new ParalleleRenderer(NBRAYONS, i, i + 1, r);
            threadPool.execute(renderer);
        }

        // Fermeture du pool de threads
        threadPool.shutdown();

        // Attendre la fin de tous les threads
        while (!threadPool.isTerminated()) {
            // Attendre
        }

        Image image = r.getIm();
        image.save("image" + NIVEAU, "png");
    }
}
