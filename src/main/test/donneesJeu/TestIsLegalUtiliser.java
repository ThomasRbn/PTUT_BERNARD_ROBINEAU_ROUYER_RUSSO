package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Tomate;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.constructionCarte.GestionActions.isLegal;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIsLegalUtiliser {

    DonneesJeu donneesJeu;

    @Test
    public void testIsLegalSansTransformateur(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        assertFalse(isLegal( Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalAvecTransformateurVide(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(Action.DROITE, 0, donneesJeu);
        assertFalse(isLegal( Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalAvecTransformateurPlein() {
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(Action.PRENDRE, 0, donneesJeu);
        faireAction(Action.GAUCHE, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertTrue(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalJoueurAvecInventairePlein() {
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(Action.PRENDRE, 0, donneesJeu);
        faireAction(Action.GAUCHE, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        faireAction(Action.HAUT, 0, donneesJeu);
        faireAction(Action.PRENDRE, 0, donneesJeu);
        faireAction(Action.GAUCHE, 0, donneesJeu);
        assertFalse(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalCuirePlatDejaCuit(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        tomate.cuire();
        joueur.prendre(new Plat(tomate));
        faireAction(Action.GAUCHE, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertFalse(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalCuirePlatDejaCouper(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        tomate.decouper();
        joueur.prendre(new Plat(tomate));
        faireAction(Action.GAUCHE, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertTrue(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalCuirePlatDejaCouperEtCuit(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        tomate.decouper();
        tomate.cuire();
        joueur.prendre(new Plat(tomate));
        faireAction(Action.GAUCHE, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertFalse(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalDecouperPlatDejaDecouper(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        tomate.decouper();
        joueur.prendre(new Plat(tomate));
        faireAction(Action.BAS, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertFalse(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalDecouperPlatDejaCuit(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        tomate.cuire();
        joueur.prendre(new Plat(tomate));
        faireAction(Action.BAS, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertTrue(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalDecouperPlatDejaCuitEtDecouper(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        tomate.cuire();
        tomate.decouper();
        joueur.prendre(new Plat(tomate));
        faireAction(Action.BAS, 0, donneesJeu);
        faireAction(Action.POSER, 0, donneesJeu);
        assertFalse(isLegal(Action.UTILISER, 0, donneesJeu));
    }

    @Test
    public void testIsLegalPoserParTerre(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(Action.PRENDRE, 0, donneesJeu);
        faireAction(Action.DROITE, 0, donneesJeu);
        assertFalse(isLegal(Action.POSER, 0, donneesJeu));
    }
}
