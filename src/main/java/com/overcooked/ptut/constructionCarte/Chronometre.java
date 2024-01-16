package com.overcooked.ptut.constructionCarte;

import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Tomate;

import java.util.Timer;
import java.util.TimerTask;

public class Chronometre {

    private Aliment aliment;
    private Timer timer;

    public Chronometre(Aliment aliment, Runnable runnable) {
        this.aliment = aliment;
        this.timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("Timer lancé");
                    runnable.run();
                    timer.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }

    public static void main(String[] args) {
        Aliment aliment = new Tomate();
        new Chronometre(aliment, () -> {
            Transformateur transformateur = new Planche(0, 0);
            transformateur.ajouterElem(new Plat(aliment));
            Plat prout = transformateur.transform();
            System.out.println(prout.getRecettesComposees().getFirst());
        });
    }
}
