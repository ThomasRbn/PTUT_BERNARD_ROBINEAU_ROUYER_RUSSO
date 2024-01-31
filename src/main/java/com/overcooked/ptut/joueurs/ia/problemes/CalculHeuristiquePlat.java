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
        if (platBut.equals(inventaire)) retourDepot = true;


    }

    private void calculerDonnees(DonneesJeu donneesJeu) {
        // récupération des données
        List<AlimentCoordonnees> alimentCoordonneesList = new ArrayList<>();
        Plat inventaire = donneesJeu.getJoueur(numJoueur).getInventaire();
        int num = 0;
        depot = new AlimentCoordonnees(new Aliment("Depot", "Aliment fictif"), donneesJeu.getCoordonneesElement("Depot").get(0));
        listeCoordonneesCuisson = donneesJeu.getCoordonneesElement("Cuisson");
        listeCoordonneesPlanche = donneesJeu.getCoordonneesElement("Planche");

        // On cherche à récupéré les actions possibles, donc on regarde ce dont on a besoin
        for (Aliment a : platBut.getRecettesComposees()) {
             // Récupération des aliments en état but
            List<int[]> listeCoordonneesEtatNom = donneesJeu.getCoordonneesElement(a.getEtatNom());

            // Si la liste n'est pas vide, on ajoute ces aliments dans ce que l'ont doit aller chercher
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

        // Si on est a la derneire action, on va au plan de travail
        if (isDerniereAction(s)) {
            actions.add(depot);
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
                    if (etatVisite != etatEnCours) {// Si l'état visite n'est pas le meme que ceux qu'on veut chercher
                        if (etatVisite < etatEnCours || (etatVisite == 2 && etatEnCours == 1)) { // Si l'état est "moins bon" ou pas le bon
                            // On dépose sur un plan de travail (probablement lorsque l'on veut nettoyer une planche
                            int[] coordonnees = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
                            actions.add(new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonnees), coordonnees));
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }


        int[] coordonneesPdt = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
        AlimentCoordonnees pdt = new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonneesPdt), coordonneesPdt);

        if (o.doitEtreCoupe(platBut) || o.doitCuire(platBut)) {
            Aliment alimentFictifTransformation = o.doitEtreCoupe(platBut) ? new Aliment("Decoupe", "Aliment fictif") : new Aliment("Cuisson", "Aliment fictif");
            if (o.doitEtreTransformer()) {
                actions.add(new AlimentCoordonnees(alimentFictifTransformation, o.getCoordonneeTransformation()));
            } else {
                for (int[] coordonnees : o.doitEtreCoupe(platBut) ? listeCoordonneesPlanche : listeCoordonneesCuisson) {
                    AlimentCoordonnees alimentCoordonnees = new AlimentCoordonnees(alimentFictifTransformation, coordonnees);
                    if (o.isLegal(alimentCoordonnees)) actions.add(alimentCoordonnees);
                }
            }
            // Si aucune action n'est permise, on doit libérer une planche ou une plaque
            // Rappel: nous sommes ici dans le cas ou l'inventaire n'est pas vide
            if (actions.isEmpty()) {
                // Actions dans l'ordre: on pose sur un plan de travail - on récupere ce qu'il y a sur la planche
                // - on pose sur un autre plan de travail - on récupère le premier element - reprise du processus classique
                actions.add(pdt);
            }
            return actions;
        }

        for (AlimentCoordonnees a : ALIMENTCO) {
            if (o.isLegal(a)) {
                if ((a.getAliment().doitCuire(platBut) || a.getAliment().doitEtreCoupe(platBut)) && !o.aPoser()) {
                    actions.add(pdt);
                } else {
                    Bloc[][] objetsFixes = donneesJeu.getObjetsFixes();
                    // Si l'aliment doit être transformer
                    if (a.getAliment().doitEtreCoupe(platBut) || a.getAliment().doitCuire(platBut)) {
                        // On vérifie que tous les transformateurs (selon ceux voulus) sont libres
                        boolean transformateurLibre = false;
                        int[] cooPlusProches = a.getAliment().doitEtreCoupe(platBut) ? listeCoordonneesPlanche.getFirst() : listeCoordonneesCuisson.getFirst();
                        int[] cooActuelles = o.getCoordonneesActuelles();
                        for (int[] co : a.getAliment().doitEtreCoupe(platBut) ? listeCoordonneesPlanche : listeCoordonneesCuisson) {
                            if (objetsFixes[co[0]][co[1]].getInventaire() == null) {
                                transformateurLibre = true;
                                cooPlusProches = co;
                                break;
                            } else {
                                // Calcul sur coordonnées plus proche
                                if (Math.abs(co[0] - cooActuelles[0]) + Math.abs(co[1] - cooActuelles[1]) < Math.abs(cooPlusProches[0] - cooActuelles[0]) + Math.abs(cooPlusProches[1] - cooActuelles[1])) {
                                    cooPlusProches = co;
                                }
                            }
                        }

                        if (!transformateurLibre) {
                            // On doit libérer le transformateur le plus proche, donc on récupère l'objet dessus
                            Aliment alimentAAllerChercher = new Aliment(objetsFixes[cooPlusProches[0]][cooPlusProches[1]].getInventaire().getRecettesComposees().getFirst());
                            actions.add(new AlimentCoordonnees(alimentAAllerChercher, cooPlusProches));
                        } else {
                            actions.add(a);
                        }
                    } else {
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
        if (retourDepot) return true;

        CalculHeuristiquePlatState o = (CalculHeuristiquePlatState) s;
        List<Aliment> aliments = o.visitees;
        List<Aliment> alimentBut = platBut.getRecettesComposees();
        List<Aliment> alimentSansPDT = new ArrayList<>();

        for (Aliment a : aliments) {
            if (!(Objects.equals(a.getNom(), "pdt"))) alimentSansPDT.add(a);
        }

        if (alimentSansPDT.size() != alimentBut.size()) return false;

        for (Aliment a : alimentBut) {
            if (!alimentSansPDT.contains(a)) return false;
        }
        return true;
    }

    @Override
    public double getAlimentCost(State s, AlimentCoordonnees a) {
        if (((CalculHeuristiquePlatState) s).estDepose()) return 0;

        int[] coordonneesActuelle = ((CalculHeuristiquePlatState) s).getCoordonneesActuelles();
        int[] coordonneesAliment = a.getCoordonnees();
        double cout = Math.abs(coordonneesActuelle[0] - coordonneesAliment[0]) + Math.abs(coordonneesActuelle[1] - coordonneesAliment[1]);

        return cout == 0 ? 1 : cout;
    }
}
