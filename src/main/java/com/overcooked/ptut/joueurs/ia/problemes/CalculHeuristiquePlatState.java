package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu;
import com.overcooked.ptut.constructionCarte.Copie;
import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculHeuristiquePlatState extends State {

    List<Aliment> visitees;

    DonneesJeu donneesJeu;
    int[] coordonneesActuelles;

    int coutTot;

    boolean doitEtreTransformer;

    public CalculHeuristiquePlatState(DonneesJeu donneesJeu2, int numJoueur) {
        this.doitEtreTransformer = false;
        this.coordonneesActuelles = donneesJeu2.getJoueur(numJoueur).getPositionCible();

        this.donneesJeu = new DonneesJeu(donneesJeu2);
        if (donneesJeu.getJoueur(numJoueur).getInventaire() != null) {
            Plat platPose = donneesJeu.getJoueur(numJoueur).poser();
            this.visitees = new ArrayList<>(platPose.getRecettesComposees());
        } else {
            Bloc[][] carte = donneesJeu.getObjetsFixes();
            Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
            if (bloc instanceof Transformateur transformateur) {
                Plat plat = bloc.getInventaire();
                if (plat != null && !transformateur.estTransforme()) {
                    Aliment a = transformateur.getInventaire().getRecettesComposees().getFirst();
                    a.setCoordonnees(coordonneesActuelles);
                    this.visitees = new ArrayList<>();
                    this.visitees.add(a);
                    this.doitEtreTransformer = true;
                } else {
                    this.visitees = new ArrayList<>();
                }
            } else {
                this.visitees = new ArrayList<>();
            }
        }
        this.coordonneesActuelles = donneesJeu.getJoueur(numJoueur).getPosition();

        coutTot = 0;
    }

    public CalculHeuristiquePlatState(DonneesJeu donneesJeu, List<Aliment> visiteesAncien, int[] coordonneesActuelles, int coutTot, boolean doitEtreTransformer) {
        this.donneesJeu = new DonneesJeu(donneesJeu);
        this.visitees = new ArrayList<>();
        for (Aliment aliment : visiteesAncien) {
            this.visitees.add(new Aliment(aliment));
        }
        this.coordonneesActuelles = coordonneesActuelles.clone();
        this.coutTot = coutTot;
        this.doitEtreTransformer = doitEtreTransformer;
    }

    @Override
    protected State cloneState() {
        return new CalculHeuristiquePlatState(donneesJeu, visitees, coordonneesActuelles, coutTot, doitEtreTransformer);
    }

    @Override
    protected boolean equalsState(State o) {
        //on vérifie les coordonnees actuelle et les aliments visités
        CalculHeuristiquePlatState etat = (CalculHeuristiquePlatState) o;
        if (coordonneesActuelles[0] != etat.coordonneesActuelles[0] || coordonneesActuelles[1] != etat.coordonneesActuelles[1]) {
            return false;
        }
        if (visitees.size() != etat.visitees.size()) {
            return false;
        }
        for (Aliment a : visitees) {
            if (!etat.visitees.contains(a)) {
                return false;
            }
        }
        return ComparateurDonneesJeu.ComparerDonneesJeu(donneesJeu, etat.donneesJeu);
    }

    @Override
    protected int hashState() {
        return 0;
    }

    public boolean isLegal(AlimentCoordonnees a) {
        if (Objects.equals(a.getAliment().getNom(), "Decoupe") || Objects.equals(a.getAliment().getNom(), "Cuisson")) {
            // On vérifie si le transformateur est vide
            Bloc[][] carte = donneesJeu.getObjetsFixes();
            Bloc bloc = carte[a.getCoordonnees()[0]][a.getCoordonnees()[1]];
            if (bloc.getInventaire() != null) return false;
            for (Aliment aliment : visitees) {
                if (Objects.equals(aliment.getNom(), a.getAliment().getNom()) && aliment.getCoordonnees()[0] == a.getCoordonnees()[0] && aliment.getCoordonnees()[1] == a.getCoordonnees()[1])
                    return false;
            }
        }
        //on parcout visite et on vérifie si il y a un nom en commun
        for (Aliment aliment : visitees) {
            if (Objects.equals(aliment.getNom(), a.getAliment().getNom())) return false;
        }
        return true;
    }

    public void deplacement(int[] nouvelleCo, Aliment a) {
        coordonneesActuelles = nouvelleCo;
        if (Objects.equals(a.getNom(), "Decoupe") || Objects.equals(a.getNom(), "Cuisson")) {
            if (doitEtreTransformer) {
                Bloc[][] carte = donneesJeu.getObjetsFixes();
                Bloc bloc = carte[nouvelleCo[0]][nouvelleCo[1]];
                if(bloc instanceof Transformateur transformateur) transformateur.retirerElem();
                doitEtreTransformer = false;
            }
            if (Objects.equals(a.getNom(), "Decoupe")) {
                visitees.getLast().decouper();
            } else {
                visitees.getLast().cuire();
            }
        } else if (Objects.equals(a.getNom(), "pdt")) {
            Bloc[][] carte = donneesJeu.getObjetsFixes();
            Bloc bloc = carte[nouvelleCo[0]][nouvelleCo[1]];
            ((PlanDeTravail) bloc).poserDessus(new Plat(visitees.getLast()));
            visitees.clear();
        } else {
            Bloc[][] carte = donneesJeu.getObjetsFixes();
            Bloc bloc = carte[nouvelleCo[0]][nouvelleCo[1]];
            if (bloc instanceof PlanDeTravail planDeTravail) {
                planDeTravail.prendre();
            } else if (bloc instanceof Transformateur transformateur) {
                transformateur.retirerElem();
            }
            visitees.add(a);
        }

    }

    public int[] getCoordonneesActuelles() {
        return coordonneesActuelles;
    }

    public boolean estAuDepot() {
        return !visitees.isEmpty() && Objects.equals(visitees.getLast().getEtatNom(), "Depot");
    }

    public boolean estDepose() {
        return !donneesJeu.getPlatDepose().isEmpty();
    }

    public boolean doitCuire(Plat platBut) {
        if (visitees.isEmpty()) return false;
        return visitees.getLast().doitCuire(platBut);
    }

    public boolean doitEtreCoupe(Plat platBut) {

        if (visitees.isEmpty()) return false;
        return visitees.getLast().doitEtreCoupe(platBut);
    }

    public boolean aPoser() {
        return visitees.isEmpty() || Objects.equals(visitees.getLast().getNom(), "pdt");
    }

    public int[] getCoordonneeVisitePDT() { // TODO: pouvoir récupéré pls aliment
        int[] pdt = null;
        for (Aliment a : visitees) {
            if (Objects.equals(a.getNom(), "pdt")) {
                pdt = a.getCoordonnees();
            } else if (Objects.equals(a.getNom(), "pdt2")) {
                pdt = null;
            }
        }
        return pdt;
    }

    /**
     * Méthode permettant de savoir si l'aliment en cours doit être transformer maintenant (sans déplacement)
     * @return
     */
    public boolean doitEtreTransformer() {
        return doitEtreTransformer;
    }

    public int[] getCoordonneeTransformation() {
        return this.visitees.getLast().getCoordonnees();
    }

    public DonneesJeu getDonneesJeu() {
        return donneesJeu;
    }

    public Aliment getDernierAliment(){
        return visitees.isEmpty()? null: visitees.getLast();
    }
}
