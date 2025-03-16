# Description du projet
Le projet consiste en la conception d’un jeu de stratégie au tour par tour où des joueurs ou robots s’affrontent sur une grille bidimensionnelle. Chaque joueur contrôle un combattant doté d’une énergie limitée et d’armes variées, telles que des mines, des bombes ou des tirs à portée limitée. L’objectif est de survivre en éliminant les adversaires grâce à des actions stratégiques tout en gérant judicieusement les ressources disponibles.

Le terrain de jeu est une grille paramétrable, composée d’éléments interactifs comme des murs infranchissables, des pastilles d’énergie à collecter et des armes posées ou utilisées par les joueurs. Une particularité du jeu réside dans la visibilité partielle : chaque joueur ne perçoit qu’une portion des informations pertinentes pour ses actions, ce qui est géré via un mécanisme de Proxy.

Pour garantir un développement structuré et évolutif, le jeu repose sur une architecture MVC. Cette approche sépare la logique métier de l’interface utilisateur, permettant de réutiliser le modèle dans différents contextes (interface graphique, terminal, ou application en réseau). Divers design patterns ont été intégrés, notamment Proxy pour la visibilité limitée, Factory pour la création des combattants, et Strategy pour définir les comportements des joueurs et la disposition initiale de la grille.

Le projet met également en avant une configuration facile des paramètres, une simulation avec des joueurs automatisés et une interface graphique qui intègre des vues multiples pour refléter la perspective de chaque joueur

# Commandes pour le lancement du jeu

Lancer la commande suivante dans le dossier livraison :
```bash
  ant run
```
# Membres du groupe 

DIALLO Mariatou Pellel

DIALLO Aissatou

COTCHO Zadelein Patrice

MAOUDE Samir
