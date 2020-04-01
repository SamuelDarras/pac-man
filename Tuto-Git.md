# Utiliser Git
1. Utiliser : `git clone https://github.com/SamuelDarras/pac-man` pour copier le repository

2. Apporter des modifications
  *On peut voir les modifications non enregistrées avec `git status`*
    1. Ajouter les fichiers/dossiers modifiés : `git add <nom_fichier(s)>`
    2. Enregistrer les modifications : `git commit -m "commentaire sur l'ajout"`
    3. Proposer les ajouts au serveur GitHub : `git push origin <nom_de_la_branche>`

3. A propos des branches
* Voir les branches : `git branch -a` (le `*` signifie que c'est la branche active)
* Changer de branche : `git checkout <nom_de_la_branche>`
