package lr;


public class ParalleleRenderer implements Runnable {
    /*
     * Classe qui représente un calcul d'illumination à partir d'une scene et d'une grille de pixels
     */
    private int rayon;
    private int min;
    private int max;
    private Renderer renderer;

    public ParalleleRenderer(int r, int min, int max, Renderer renderer ) {
        /*
         * Constructeur 
         */
        this.rayon = r;
        this.min = min;
        this.max = max;
        this.renderer = renderer;
    }

    public void run() {
        /*
         * Méthode qui lance le calcul les lignes de l'image
         */
        for (int i = min; i < max; i++) {
            renderer.renderLine(i, rayon);
        }
    }
    
    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

}