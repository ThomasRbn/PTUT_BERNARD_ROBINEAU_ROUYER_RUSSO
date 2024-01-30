package com.overcooked.ptut.joueurs.ia.problemes.decentralisee;

import com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu;
import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculHeuristiquePlatStateDecentr extends State {

    public List<Aliment> visitees;

    public DonneesJeu donneesJeu;
    int[] coordonneesActuelles;

    int coutTot;

    public CalculHeuristiquePlatStateDecentr(DonneesJeu donneesJeu, int numJoueur) {
        this.coordonneesActuelles = donneesJeu.getJoueur(numJoueur).getPositionCible();

        this.donneesJeu = new DonneesJeu(donneesJeu);
        if (donneesJeu.getJoueur(numJoueur).getInventaire() != null) {
            this.visitees = new ArrayList<>(donneesJeu.getJoueur(numJoueur).getInventaire().getRecettesComposees());
        } else {
            Bloc[][] carte = donneesJeu.getObjetsFixes();
            Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
            if(bloc instanceof Transformateur transformateur) {
                Plat plat = bloc.getInventaire();
                if (plat != null && !transformateur.estTransforme()) {
                    this.visitees = new ArrayList<>(transformateur.getInventaire().getRecettesComposees());
                }else {
                    this.visitees = new ArrayList<>();
                }
            } else{
                this.visitees = new ArrayList<>();
            }
        }
        this.coordonneesActuelles = donneesJeu.getJoueur(numJoueur).getPosition();

        coutTot = 0;
    }

    public CalculHeuristiquePlatStateDecentr(DonneesJeu donneesJeu, List<Aliment> visiteesAncien, int[] coordonneesActuelles, int coutTot) {
        this.donneesJeu = new DonneesJeu(donneesJeu);
        this.visitees = new ArrayList<>();
        for (Aliment aliment : visiteesAncien) {
            this.visitees.add(new Aliment(aliment));
        }
        this.coordonneesActuelles = coordonneesActuelles.clone();
        this.coutTot = coutTot;
    }

    @Override
    protected State cloneState() {
        return new CalculHeuristiquePlatStateDecentr(donneesJeu, visitees, coordonneesActuelles, coutTot);
    }

    @Override
    protected boolean equalsState(State o) {
        //on vérifie les coordonnees actuelle et les aliments visités
        CalculHeuristiquePlatStateDecentr etat = (CalculHeuristiquePlatStateDecentr) o;
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
        return ComparateurDonneesJeu.ComparerDonneesJeu(donneesJeu,etat.donneesJeu);
    }

    @Override
    protected int hashState() {
        return 0;
    }

    public boolean isLegal(AlimentCoordonnees a) {
        if(Objects.equals(a.getAliment().getNom(), "Decoupe") || Objects.equals(a.getAliment().getNom(), "Cuisson")){
            for (Aliment aliment : visitees) {
                if (Objects.equals(aliment.getNom(), a.getAliment().getNom()) && aliment.getCoordonnees()[0] == a.getCoordonnees()[0] && aliment.getCoordonnees()[1] == a.getCoordonnees()[1]) return false;
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
        if (Objects.equals(a.getNom(), "Decoupe")) {
//            Bloc[][] carte = donneesJeu.getObjetsFixes();
//            Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
//            if(bloc instanceof Transformateur transformateur) {
//                Plat plat = bloc.getInventaire();
//                if (plat != null) {
//                    transformateur.transform();
//                    System.out.println("transform");
//                } else {
                    visitees.getLast().decouper();
//                }
//            }
        } else if (Objects.equals(a.getNom(), "Cuisson")) {
//            Bloc[][] carte = donneesJeu.getObjetsFixes();
//            Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
//            if(bloc instanceof Transformateur transformateur) {
//                Plat plat = bloc.getInventaire();
//                if (plat != null) {
//                    transformateur.transform(); // TODO: vérifier le getblock
//                } else {
                    visitees.getLast().cuire();
//                }
//            }
        } else {
            visitees.add(a);
        }
    }

    public int[] getCoordonneesActuelles() {
        return coordonneesActuelles;
    }

    public boolean estAuDepot() {
        return !visitees.isEmpty() && visitees.getLast().getEtatNom() == "Depot";
    }

    public boolean estDepose() {
        return !donneesJeu.getPlatDepose().isEmpty();
    }

    public boolean estSurPlaqueEtDoitCuire(Plat platBut){
        Bloc[][] carte = donneesJeu.getObjetsFixes();
        Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
        if (bloc instanceof Transformateur) {
            Plat plat = bloc.getInventaire();
            if (plat != null) {
                if (plat.getRecettesComposees().getFirst().doitCuire(platBut)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean doitCuire(Plat platBut) {
        if (visitees.isEmpty()) return false;
        return visitees.getLast().doitCuire(platBut);

    }

    public boolean estSurPlancheEtDoitEtreCoupe(Plat platBut){
        Bloc[][] carte = donneesJeu.getObjetsFixes();
        Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
        if (bloc instanceof Transformateur) {
            Plat plat = bloc.getInventaire();
            if (plat != null) {
                if (plat.getRecettesComposees().getFirst().doitEtreCoupe(platBut)) {
                    return true;
                }
            }
        }
        return false;
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
}
