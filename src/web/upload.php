<link rel="stylesheet" type="text/css" href="style.css">

<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['scene'])) {
    // Chemin de sauvegarde du fichier de scène
    $uploadsDir = 'uploads/';
    // Nom du fichier de scène (simple.txt)
    $sceneFile = $uploadsDir . 'simple.txt';
    
    $fileExtension = pathinfo($_FILES['scene']['name'], PATHINFO_EXTENSION);
    if ($fileExtension === 'txt') {
        // Vérifie le type MIME du fichier de scène
        $finfo = finfo_open(FILEINFO_MIME_TYPE);
        $mime = finfo_file($finfo, $_FILES['scene']['tmp_name']);
        finfo_close($finfo);
        
        // Vérifie la taille du fichier de scène (limite à 5 Mo)
        $maxFileSize = 5 * 1024 * 1024; // 5 Mo
        if ($mime === 'text/plain' && $_FILES['scene']['size'] <= $maxFileSize) {
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
            echo 'Le fichier de scène doit être un fichier texte (extension .txt) et ne doit pas dépasser 5 Mo.';
            echo '<button onclick="window.history.back()">Retour</button>';
        }
    } else {
        echo 'Le fichier de scène doit avoir l\'extension .txt.';
        echo '<button onclick="window.history.back()">Retour</button>';
    }
} else {
    echo 'Veuillez sélectionner un fichier de scène.';
}
?>
