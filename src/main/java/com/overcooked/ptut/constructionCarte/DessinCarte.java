//package com.overcooked.ptut.constructionCarte;
//
//import com.overcooked.ptut.moteurJeu.Jeu;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//public class DessinCarte {
//
//    private final static int TAILLE = 30;
//
//    @Override
//    public void dessinerJeu(Jeu jeu, Canvas canvas) {
//        LabyJeu laby = (LabyJeu) jeu;
//
//        //apparition labyrinthe
//        GraphicsContext ghc = canvas.getGraphicsContext2D();
//        ghc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        ghc.setFill(Color.BLACK);
//
//        for (int i = 0; i < laby.getLengthY(); i++) {
//            for (int j = 0; j < laby.getLength(); j++) {
//                if (laby.getMur(j, i)) {
//                    ghc.fillRect(j * TAILLE, i * TAILLE, TAILLE, TAILLE);
//                }
//            }
//        }
//
//        //apparition porte fermée
//        ghc.setFill(Color.ORANGE);
//        ghc.fillRect(laby.getCoordonneesPorte()[0] * TAILLE, laby.getCoordonneesPorte()[1] * TAILLE, TAILLE, TAILLE);
//
//        //apparition amulette
//        ghc.setFill(Color.YELLOW);
//        ghc.fillOval(laby.getAmulette().getX() * TAILLE, laby.getAmulette().getY() * TAILLE, TAILLE, TAILLE);
//
//        //apparition perso
//        ghc.setFill(laby.getColorAttaque(laby.getPj()));
//        ghc.fillOval(laby.getPj().getX() * TAILLE, laby.getPj().getY() * TAILLE, TAILLE, TAILLE);
//
//        //apparition monstre
//        for (int j = 0; j < laby.getEnnemi().size(); j++) {
//            ghc.setFill(laby.getColorAttaque(laby.getEnnemi().get(j)));
//            ghc.fillOval(laby.getEnnemi().get(j).getX() * TAILLE, laby.getEnnemi().get(j).getY() * TAILLE, TAILLE, TAILLE);
//        }
//
//
//        //Modif modèle perso si il porte l'amulette
//        if (laby.getPj().isPorterAmulette()) {
//
//            //Porte ouverte
//            ghc.setFill(Color.GREEN);
//            ghc.fillRect(laby.getCoordonneesPorte()[0] * TAILLE, laby.getCoordonneesPorte()[1] * TAILLE, TAILLE, TAILLE);
//
//            //Disparition de l'amulette
//            ghc.setFill(Color.rgb(244, 244, 244));
//            ghc.fillOval(laby.getAmulette().getX() * TAILLE, laby.getAmulette().getY() * TAILLE, TAILLE, TAILLE);
//
//            //Création du personnage
//            ghc.setFill(laby.getColorAttaque(laby.getPj()));
//            ghc.fillOval(laby.getPj().getX() * TAILLE, laby.getPj().getY() * TAILLE, TAILLE, TAILLE);
//
//            //L'amulette apparait sur le modèle du personnage
//            ghc.setFill(Color.YELLOW);
//            ghc.fillOval(laby.getPj().getX() * TAILLE, laby.getPj().getY() * TAILLE, TAILLE / 4., TAILLE / 4.);
//        }
//    }
//}