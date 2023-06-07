<link rel="stylesheet" type="text/css" href="style.css">

<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['scene'])) {
    // Chemin de sauvegarde du fichier de scène
    $uploadsDir = 'uploads/';
    $sceneFile = $uploadsDir . basename($_FILES['scene']['name']);
    
    // Déplace le fichier de scène vers le dossier d'upload
    if (move_uploaded_file($_FILES['scene']['tmp_name'], $sceneFile)) {
        // Exécute le lancer de rayons
        chdir('../../bin');
        exec('cp ../src/lr/simple.txt .');
        exec('java lr.LR');
        exec('cp image2.png ../src/web/uploads');
        
        // Affiche l'image calculée
        echo '<h2>Image calculée :</h2>';
        echo '<img src="uploads/image2.png" alt="Image calculée">';
        
        // Ajoute le bouton de retour en arrière
        echo '<button onclick="window.history.back()">Retour</button>';
    } else {
        echo 'Erreur lors du téléchargement du fichier de scène.';
    }
} else {
    echo 'Veuillez sélectionner un fichier de scène.';
}
?>
