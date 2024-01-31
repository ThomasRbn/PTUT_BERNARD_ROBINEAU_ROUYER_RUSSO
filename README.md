# Projet tutorée Overcooked IA

### _Par Bernard Julien, Robineau Thomas, Rouyer Hugolin, Russo Nicolas_

#### _Projet réalisé dans le cadre du projet tutoré de 3ème année de BUT Informatique à l'IUT de Nancy-Charlemagne année 2023-2024._


## Présentation du projet

Le projet tutoré Overcooked IA est un projet de développement d'un agent artificiel pour le jeu Overcooked.
Le but de ce projet est de développer une IA capable de jouer au jeu Overcooked, un jeu de cuisine coopératif. Il faut également
recréer une interface de jeu. Cette interface reste cependant minimaliste.
Le jeu se joue à 1 ou 2 joueurs, et le but est de préparer des plats en suivant des recettes. Le jeu se joue
en temps réel, et les joueurs doivent communiquer entre eux pour réussir à préparer les plats dans les temps.



## Lancement du projet

Pour lancer le projet, il faut lancer le fichier "OvercookedJavaFX" situé à la racine du dossier "src". Un menu va alors s'ouvrir,
et il suffit de sélectionner le niveau que l'on veut jouer. Ensuite, un deuxième menu s'ouvre, et il faut sélectionner le
type de joueurs. Voici les différents types de joueurs disponibles :
- Humain : le joueur est un humain, et il peut jouer avec les touches du clavier.
- IA : le joueur est un agent artificiel, et il joue en suivant une stratégie définie.
- IADecentralisee : le joueur est un agent artificiel, et il joue en suivant une stratégie définie. Cette stratégie est décentralisée, c'est-à-dire que chaque agent prend ses décisions de manière indépendante.
- AutoN4: Le joueur va suivre un pattern prédéfini pour jouer. Il ne prend pas de décision et ne prend pas en compte l'état du jeu. Il fonctionne uniquement sur le niveau 4.

Il faut remplir le champ de texte avec la durée en secondes souhaitée pour la partie, puis cliquer sur le bouton "Valider".
La partie va ensuite se lancer avec les paramètres fournis précédemment.


## Règles du jeu

Le but du jeu est de préparer des plats en suivant des recettes. Pour cela, il faut récupérer les ingrédients nécessaires, les couper, les cuire, et les assembler puis de les déposer dans le dépôt.
Les recettes sont affichées à droite de l'écran. Le carré blanc correspond à la planche à découper, le carré jaune à la cuisson, le carré gris au dépôt et le reste sont des plans de travail.


## Commandes pour le joueur humain

- Z : déplacement vers le haut
- Q : déplacement vers la gauche
- S : déplacement vers le bas
- D : déplacement vers la droite
- Space : prendre ou poser un plat
- E : couper/cuire un ingrédient
- R : ne rien faire
