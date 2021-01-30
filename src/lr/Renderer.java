package lr;

/**
 * Classe qui représente un calcul d'illumination à partir d'une scene et d'une
 * grille de pixels
 */

public class Renderer {

	/** Largeur de l'image */
	private int largeur;

	/** Hauteur de l'image */
	private int hauteur;

	/** largeur d'un pixel */
	private float largeurPixel;

	/** hauteur d'un pixel */
	private float hauteurPixel;

	/** ratio de l'image */
	private float ratio;

	/** Scèene a calculer */
	private Scene sc;

	/** position de l'observateur */
	private Point obs = new Point(0.0f, 0.0f, 2.0f);

	/** niveau de réflexion (nombre de réflexions autorisées depuis le rayon primaire) */
	private int niveau = 0;

	private Image im;

/**
 * Constructeur avec taille de l'image et activation ou non de l'antialiasage
 * @param largeur
 * @param hauteur
 * @param antialiasage
 */
	public Renderer(int largeur, int hauteur) {

		this.largeur = largeur;
		this.hauteur = hauteur;

		this.largeurPixel = 2.0f / largeur;
		this.hauteurPixel = 2.0f / hauteur;

		this.ratio = (float) largeur / (float) hauteur;

		im = new Image(this.largeur, this.hauteur);

	}


	/**
	 * Spécifie la position de l'observateur (la caméra)
	 * @param obs
	 */
	public void setObs(Point obs) {
		this.obs = obs;
	}


	/**
	 * Permet de charger la scène
	 * 
	 * @param sc
	 */
	public void setScene(Scene sc) {
		this.sc = sc;
	}

	
	/**
	 * Spécifie le nombre de niveaux de calculs
	 * @param niveau
	 */
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}


	/**
	 * Permet de calculer l'intensité pour un pixel en spécifiant le nombre de rayons 
	 * 
	 * @param x
	 * @param y
	 * @param nbrayon
	 * @return
	 */
	public Intensite getIntensite(int x, int y, int nbrayon) {

		Intensite in = new Intensite();

		for (int n = 0; n < nbrayon; n++) {

			
			float dx;
			float dy;

			// si un rayon on choisit le centre du pixel
			if (nbrayon == 1){
				dx = 0.5f;
				dy = 0.5f;
			} else {
				dx = (float) Math.random();
				dy = (float) Math.random();
			}

			float posX = -1.0f + (x + dx) * largeurPixel;
			float posY = 1.0f - (y + dy) * hauteurPixel;

			// générer le rayon primaire
			Point dir = new Point(posX, posY / ratio, 0.0f);
			Rayon ray = new Rayon(obs, dir);

			// calculer l'intersection du rayon primaire avec la scene
			Intersection inter = sc.intersecte(ray);

			// lancer le calcul d'eclairage du point d'intersection
			if (inter != null) {
				in.add(inter.eclairer(sc, obs, niveau, x, y));
			}
		} // for n
		in.div(nbrayon);
		return in;
	}


	public Image renderFullImage(int nbrayon){

		
		for (int y = 0; y < this.hauteur; y++) {
			for (int x = 0; x < this.largeur; x++) {
				im.setPixel(x, y, (this.getIntensite(x, y, nbrayon)).getColor());
			}
		}

		return im;
	} 
	
}