package com.overcooked.ptut.joueurs.ia.problemes.experience;

import com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu;
import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
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

public class AlgoPlanificationLargeEtat extends State {

    List<Aliment> visitees;

    DonneesJeu donneesJeu;
    int[] coordonneesActuelles;

    int coutTot;

    boolean doitEtreTransformer;
    int numJoueur;

    public AlgoPlanificationLargeEtat(DonneesJeu donneesJeu2, int numJoueur) {
        this.numJoueur = numJoueur;
        this.doitEtreTransformer = false;
        this.coordonneesActuelles = donneesJeu2.getJoueur(numJoueur).getPositionCible();

        this.donneesJeu = new DonneesJeu(donneesJeu2);
        if (donneesJeu.getJoueur(numJoueur).getInventaire() != null) {
            // Le plat dans l'inventaire doit être considéré comme visité
            Plat platPose = donneesJeu.getJoueur(numJoueur).poser();
            this.visitees = new ArrayList<>(platPose.getRecettesComposees());
        } else {
            this.visitees = new ArrayList<>();

            Bloc[][] carte = donneesJeu.getObjetsFixes();
            Bloc bloc = carte[coordonneesActuelles[0]][coordonneesActuelles[1]];
            // Si on est face à un transformateur (avec plat non-transfo) : on considère que l'on a visité l'aliment
            // et que l'on doit le transformer
            // Pb: si on est face a un transformateur avec aliment à transformer sans que cela nous interesse
            if (bloc instanceof Transformateur transformateur) {
                Plat plat = bloc.getInventaire();
                if (plat != null && !transformateur.estTransforme()) {
                    Aliment a = transformateur.getInventaire().getRecettesComposees().getFirst();
                    a.setCoordonnees(coordonneesActuelles);
                    this.visitees.add(a);
                    this.doitEtreTransformer = true;
                }
            }
        }
        this.coordonneesActuelles = donneesJeu.getJoueur(numJoueur).getPosition();

        coutTot = 0;
    }

    public AlgoPlanificationLargeEtat(DonneesJeu donneesJeu, List<Aliment> visiteesAncien, int[] coordonneesActuelles, int coutTot, boolean doitEtreTransformer) {
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
        return new AlgoPlanificationLargeEtat(donneesJeu, visitees, coordonneesActuelles, coutTot, doitEtreTransformer);
    }

    @Override
    protected boolean equalsState(State o) {
        //on vérifie les coordonnees actuelle et les aliments visités
        AlgoPlanificationLargeEtat etat = (AlgoPlanificationLargeEtat) o;
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
        //on parcourt visite et on vérifie s'il y a un nom en commun
        for (Aliment aliment : visitees) {
            if (Objects.equals(aliment.getNom(), a.getAliment().getNom())) return false;
        }
        return true;
    }

    public void deplacement(int[] nouvelleCo, Aliment a) {
        Bloc[][] carte = donneesJeu.getObjetsFixes();
        Bloc bloc = carte[nouvelleCo[0]][nouvelleCo[1]];
        coordonneesActuelles = nouvelleCo;

        if (Objects.equals(a.getNom(), "Decoupe") || Objects.equals(a.getNom(), "Cuisson")) {
            if (doitEtreTransformer) { // On regarde la variable indiquant si on doit transformer un transformateur
                // Si c'est un transformateur, on le vide (on fera la transformation dans les visitee)
                if (bloc instanceof Transformateur transformateur) transformateur.retirerElem();
                doitEtreTransformer = false;
            }
            // Transformation
            if (Objects.equals(a.getNom(), "Decoupe")) {
                visitees.getLast().decouper();
            } else {
                visitees.getLast().cuire();
            }
        } else if (Objects.equals(a.getNom(), "pdt")) {
            // On pose le plat visite sur le plan de travail
            ((PlanDeTravail) bloc).poserDessus(new Plat(visitees.getLast()));
            visitees.clear();
        } else if (Objects.equals(a.getNom(), "j")) {
            Joueur j = donneesJeu.getJoueur(numJoueur == 0? 1 : 0);
            visitees.add(j.getInventaire().getRecettesComposees().getFirst());
        } else {
            // Si l'action est lié à un plan de travail ou un transformateur, on vide ce dernier
            if (bloc instanceof PlanDeTravail planDeTravail) planDeTravail.prendre();
            else if (bloc instanceof Transformateur transformateur) transformateur.retirerElem();

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
        return !visitees.isEmpty() && visitees.getLast().doitCuire(platBut);
    }

    public boolean doitEtreCoupe(Plat platBut) {
        return !visitees.isEmpty() && visitees.getLast().doitEtreCoupe(platBut);
    }

    public boolean aPoser() {
        return visitees.isEmpty() || Objects.equals(visitees.getLast().getNom(), "pdt");
    }


    /**
     * Méthode permettant de savoir si l'aliment en cours doit être transformé maintenant (sans déplacement)
     *
     * @return true si l'aliment doit être transformé maintenant, false sinon
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

    public Aliment getDernierAliment() {
        return visitees.isEmpty() ? null : visitees.getLast();
    }

    @Override
    public String toString() {
        return "CalculHeuristiqueTestState{" +
                "visitees=" + visitees +
                ", donneesJeu=" + donneesJeu +
                ", coordonneesActuelles=" + coordonneesActuelles[0] + " " + coordonneesActuelles[1] +
                ", coutTot=" + coutTot +
                ", doitEtreTransformer=" + doitEtreTransformer +
                '}';
    }
}
