<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $uploadDir = 'uploads/';
    $uploadedFile = $uploadDir . basename($_FILES['sceneFile']['name']);
    $imageFile = 'rendered_image.png';

    // Vérifier si le fichier est valide
    $fileType = strtolower(pathinfo($uploadedFile, PATHINFO_EXTENSION));
    if ($fileType !== 'txt') {
        echo 'Le fichier doit être au format texte.';
        exit();
    }

    // Déplacer le fichier vers le répertoire d'upload
    if (move_uploaded_file($_FILES['sceneFile']['tmp_name'], $uploadedFile)) {
        // Exécuter le rendu de ray tracing sur la scène
        $command = 'java -jar RayTracer.jar ' . $uploadedFile . ' ' . $imageFile;
        exec($command);

        // Rediriger vers l'index avec le chemin de l'image rendue en paramètre
        header('Location: index.html?image=' . $imageFile);
    } else {
        echo 'Erreur lors du téléchargement du fichier.';
    }
}
?>
