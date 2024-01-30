package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculHeuristiquePlat extends SearchProblemAC {

    int[] coordonneesDepart;

    Plat platBut;

    AlimentCoordonnees depot;

    List<int[]> listeCoordonneesCuisson;

    List<int[]> listeCoordonneesPlanche;
    boolean retourDepot;

    DonneesJeu donneesJeu;

    int numJoueur;

    public CalculHeuristiquePlat(Plat plat, int numJoueur, DonneesJeu donneesJeu) {
        this.numJoueur = numJoueur;
        this.donneesJeu = donneesJeu;
        retourDepot = false;
        platBut = plat;
        Plat inventaire = donneesJeu.getJoueur(numJoueur).getInventaire();
        int num = 0;
        depot = new AlimentCoordonnees(new Aliment("Depot", "Aliment fictif"), donneesJeu.getCoordonneesElement("Depot").get(0));
        if (inventaire != null && platBut.equals(inventaire)) {
            retourDepot = true;
        }

    }

    private void calculerDonnees(DonneesJeu donneesJeu){

        List<AlimentCoordonnees> alimentCoordonneesList = new ArrayList<>();
        Plat inventaire = donneesJeu.getJoueur(numJoueur).getInventaire();
        int num = 0;
        depot = new AlimentCoordonnees(new Aliment("Depot", "Aliment fictif"), donneesJeu.getCoordonneesElement("Depot").get(0));


        listeCoordonneesCuisson = donneesJeu.getCoordonneesElement("Cuisson");
        listeCoordonneesPlanche = donneesJeu.getCoordonneesElement("Planche");
        for (Aliment a : platBut.getRecettesComposees()) {
            if (inventaire != null) {
                boolean continu = false;
                for (Aliment aliment : inventaire.getRecettesComposees()) {
                    if (aliment.equalsType(a)) {
                        continu = true;
                    }
                }
                if (continu) continue;
            }
            // Récupération des aliments en état but
            List<int[]> listeCoordonneesEtatNom = donneesJeu.getCoordonneesElement(a.getEtatNom());
            if (!listeCoordonneesEtatNom.isEmpty()) {
                for (int[] coordonnees : listeCoordonneesEtatNom) {
                    alimentCoordonneesList.add(new AlimentCoordonnees(a, coordonnees));
                    num++;
                }
            } else {
                if (a.getEtat() == 3) {
                    // Si l'état but est coupé et cuit et qu'il n'y a pas de coupé et cuit, on va chercher les coupé
                    List<int[]> listeCoordonneesEtat2 = donneesJeu.getCoordonneesElement(a.getNom() + "2");//si 3, aller prendre 2
                    if (!listeCoordonneesEtat2.isEmpty()) {
                        for (int[] coordonnees : listeCoordonneesEtat2) {
                            Aliment aliment = new Aliment(a.getNom());
                            aliment.setEtat(2);
                            alimentCoordonneesList.add(new AlimentCoordonnees(aliment, coordonnees));
                            num++;
                        }
                        break;
                    }
                }
                // Si pas d'aliments "proche" de l'état but, on prend les aliments primaires
                List<int[]> listeCoordonnees = donneesJeu.getCoordonneesElement(a.getNom());
                for (int[] coordonnees : listeCoordonnees) {
                    alimentCoordonneesList.add(new AlimentCoordonnees(new Aliment(a.getNom()), coordonnees));
                    num++;
                }
            }

        }

        ALIMENTCO = alimentCoordonneesList.toArray(new AlimentCoordonnees[0]);
    }

    @Override
    public ArrayList<AlimentCoordonnees> getAlimentCoordonnees(State s) {
        ArrayList<AlimentCoordonnees> actions = new ArrayList<>();
        CalculHeuristiquePlatState o = (CalculHeuristiquePlatState) s;
        DonneesJeu donneesJeuO = o.getDonneesJeu();
        this.donneesJeu = donneesJeuO;

        if (isDerniereAction(s)) {
            int[] pdt = o.getCoordonneeVisitePDT();
            if (pdt == null) {
                actions.add(depot);
            } else {
                // On récupère l'aliment sur le plan de travail
                actions.add(new AlimentCoordonnees(new Aliment("pdt2", "Aliment ficitif", pdt), pdt));
            }
            return actions;

        }

        calculerDonnees(donneesJeu);

        // On regarde si l'élément en main fait partit des éléments envisagé (dans ALIMENTCO)
        Aliment alimentVisitee = o.getDernierAliment();
        if (alimentVisitee != null) {
            for (AlimentCoordonnees a : ALIMENTCO) {
                if (a.getAliment().equalsType(alimentVisitee)) {
                    //On regarde si l'état de l'aliment visitee est plus avancé que les états de ALIMENTCO
                    int etatVisite = alimentVisitee.getEtat();
                    int etatEnCours = a.getAliment().getEtat();
                    if(etatVisite != etatEnCours){// Si l'état visite n'est pas le meme que ceux qu'on veut chercher
                        if(etatVisite < etatEnCours || (etatVisite ==2 && etatEnCours == 1)){ // Si l'état est "moins bon" ou pas le bon
                            // On dépose sur un plan de travail (probablement lorsque l'on veut nettoyer une planche
                            int[] coordonnees = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
                            actions.add(new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonnees), coordonnees));
                        }else {
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }
        }


        if (o.doitEtreCoupe(platBut)) {
            Aliment alimentFictifDecoupe = new Aliment("Decoupe", "Aliment fictif");
            if (o.doitEtreTransformer()) {
                actions.add(new AlimentCoordonnees(alimentFictifDecoupe, o.getCoordonneeTransformation()));
            } else {
                for (int[] coordonnees : listeCoordonneesPlanche) {
                    AlimentCoordonnees alimentCoordonnees = new AlimentCoordonnees(alimentFictifDecoupe, coordonnees);
                    if (o.isLegal(alimentCoordonnees)) actions.add(alimentCoordonnees);
                }
            }
            // Si aucune action n'est permise, on doit libérer une planche
            if(actions.isEmpty()){
                // Actions dans l'ordre: on pose sur un plan de travail - on récupere ce qu'il y a sur la planche
                // - on pose sur un autre plan de travail - on récupère le premier element - reprise du processus classique
                int[] coordonnees = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
                actions.add(new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonnees), coordonnees));
            }
            return actions;
        }

        if (o.doitCuire(platBut)) {
            Aliment alimentFictifCuisson = new Aliment("Cuisson", "Aliment fictif");
            if (o.doitEtreTransformer()) {
                actions.add(new AlimentCoordonnees(alimentFictifCuisson, o.getCoordonneeTransformation()));
            } else {
                for (int[] coordonnees : listeCoordonneesCuisson) {
                    AlimentCoordonnees alimentCoordonnees = new AlimentCoordonnees(alimentFictifCuisson, coordonnees);
                    if (o.isLegal(alimentCoordonnees)) actions.add(alimentCoordonnees);
                }
            }
            // Si aucune action n'est permise, on doit libérer une plaque
            if(actions.isEmpty()){
                int[] coordonnees = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
                actions.add(new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonnees), coordonnees));
            }
            return actions;
        }
        for (AlimentCoordonnees a : ALIMENTCO) {
            if (o.isLegal(a)) {
                if ((a.getAliment().doitCuire(platBut) || a.getAliment().doitEtreCoupe(platBut)) && !o.aPoser()) {
                    int[] coordonnees = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
                    actions.add(new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonnees), coordonnees));
                } else {
                    Bloc[][] objetsFixes = donneesJeu.getObjetsFixes();;
                    if(a.getAliment().doitEtreCoupe(platBut)){
                        // On vérifie que toutes les planches sont libres
                        boolean plancheLibre = false;
                        int[] cooPlusProches = listeCoordonneesPlanche.getFirst();
                        int[] cooActuelles = o.getCoordonneesActuelles();
                        for(int[] co: listeCoordonneesPlanche){
                            if(objetsFixes[co[0]][co[1]].getInventaire() == null){
                                plancheLibre = true;
                                cooPlusProches = co;
                                break;
                            }else {
                                // Calcul sur coordonnees plus proche
                                if(Math.abs(co[0] - cooActuelles[0]) + Math.abs(co[1] - cooActuelles[1]) < Math.abs(cooPlusProches[0] - cooActuelles[0]) + Math.abs(cooPlusProches[1] - cooActuelles[1])){
                                    cooPlusProches = co;
                                }
                            }
                        }

                        if(!plancheLibre) {
                            // On doit liberer une planche, donc on récupère l'objet dessus
                            Aliment alimentAAllerChercher = new Aliment(objetsFixes[cooPlusProches[0]][cooPlusProches[1]].getInventaire().getRecettesComposees().getFirst());
                            actions.add(new AlimentCoordonnees(alimentAAllerChercher, cooPlusProches));
                        }else{
                            actions.add(a);
                        }
                    }else if(a.getAliment().doitCuire(platBut)){
                        // On vérifie que toutes les planches sont libres
                        boolean plaqueLibe = false;
                        int[] cooPlusProches = listeCoordonneesCuisson.getFirst();
                        int[] cooActuelles = o.getCoordonneesActuelles();
                        for(int[] co: listeCoordonneesCuisson){
                            if(objetsFixes[co[0]][co[1]].getInventaire() == null){
                                plaqueLibe = true;
                                cooPlusProches = co;
                                break;
                            }else {
                                // Calcul sur coordonnees plus proche
                                if(Math.abs(co[0] - cooActuelles[0]) + Math.abs(co[1] - cooActuelles[1]) < Math.abs(cooPlusProches[0] - cooActuelles[0]) + Math.abs(cooPlusProches[1] - cooActuelles[1])){
                                    cooPlusProches = co;
                                }
                            }
                        }

                        if(!plaqueLibe) {
                            // On doit liberer une plaque de cuisson, donc on récupère l'objet dessus
                            Aliment alimentAAllerChercher = new Aliment(objetsFixes[cooPlusProches[0]][cooPlusProches[1]].getInventaire().getRecettesComposees().getFirst());
                            actions.add(new AlimentCoordonnees(alimentAAllerChercher, cooPlusProches));
                        }else{
                            actions.add(a);
                        }
                    }else {
                        actions.add(a);
                    }
                }
            }
        }
        return actions;
    }

    @Override
    public State doAlimentCoordonnees(State s, AlimentCoordonnees a) {
        CalculHeuristiquePlatState o = (CalculHeuristiquePlatState) s.clone();
        o.deplacement(a.getCoordonnees(), a.getAliment());
        return o;
    }

    @Override
    public boolean isGoalState(State s) {
        return ((CalculHeuristiquePlatState) s).estAuDepot();
    }

    public boolean isDerniereAction(State s) {
        if (retourDepot) {
            return true;
        }
        CalculHeuristiquePlatState o = (CalculHeuristiquePlatState) s;
        List<Aliment> aliments = o.visitees;
        List<Aliment> alimentBut = platBut.getRecettesComposees();

        List<Aliment> alimentSansPDT = new ArrayList<>();

        for (Aliment a : aliments) {
            if (!(Objects.equals(a.getNom(), "pdt") || Objects.equals(a.getNom(), "pdt2"))) {
                alimentSansPDT.add(a);
            }
        }

        if (alimentSansPDT.size() != alimentBut.size()) {
            return false;
        }
        for (Aliment a : alimentBut) {
            if (!alimentSansPDT.contains(a)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double getAlimentCost(State s, AlimentCoordonnees a) {
        if (((CalculHeuristiquePlatState) s).estDepose()) {
            return 0;
        }
        int[] coordonneesActuelle = ((CalculHeuristiquePlatState) s).getCoordonneesActuelles();
        int[] coordonneesAliment = a.getCoordonnees();
        double cout = Math.abs(coordonneesActuelle[0] - coordonneesAliment[0]) + Math.abs(coordonneesActuelle[1] - coordonneesAliment[1]);

        return cout == 0? 1 : cout;
    }
}
