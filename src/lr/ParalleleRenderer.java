package lr;

public class ParalleleRenderer implements Runnable {
    private int rayon;
    private int min;
    private int max;
    private Renderer renderer;

    // Constructeur 
    public ParalleleRenderer(int r, int min, int max, Renderer renderer ) {
        this.rayon = r;
        this.min = min;
        this.max = max;
        this.renderer = renderer;
    }
    
    // Méthode exécutée par le thread
    public void run() {
        // Parcourt des lignes à rendre
        for (int i = min; i < max; i++) {
            renderer.renderLine(i, rayon);
        }
    }
    
    // Définition de la valeur minimale des lignes à rendre
    public void setMin(int min) {
        this.min = min;
    }

    // Définition de la valeur maximale des lignes à rendre
    public void setMax(int max) {
        this.max = max;
    }

}
