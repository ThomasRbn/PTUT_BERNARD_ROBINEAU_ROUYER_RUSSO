package com.overcooked.ptut;

import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

public class Overcooked {
    // Liste de joueurs
    private Joueur[] joueurs;

    // Constructeur
    public Overcooked(Joueur[] j) {
        this.joueurs = j;
    }

    public void jeu(){
        Boolean jeuFinis = false;
        while (!jeuFinis){
            // Creation de thread pour chaque joueur
            ArrayList<Thread> threads = new ArrayList<>();
            for (Joueur joueur : joueurs) {
                Thread thread = new Thread(() -> {
//                    Action action = joueur.demanderAction(donneesJeu);
                    //Traitement de l'action

                });
                threads.add(thread);
            }

            // Démarrage de tous les threads simultanément
            for (Thread thread : threads) {
                thread.start();
            }

            // On attend que tous les threads aient terminé
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Le jeu peut maintenant continuer...
            System.out.println("Le jeu continue...");
        }
    }
}
