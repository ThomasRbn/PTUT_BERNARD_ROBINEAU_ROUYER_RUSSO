package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Salade;
import com.overcooked.ptut.recettes.aliment.Tomate;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu.ComparerDonneesJeu;
import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestEquals {

    DonneesJeu donneesJeu;

    @Test
    public void testEqualsSansToucher() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCopieNiveau() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu donneesJeu2 = new DonneesJeu("niveaux/niveau0.txt", true);
        assertTrue(ComparerDonneesJeu(donneesJeu,donneesJeu2));
    }

    @Test
    public void testEqualsAvecDeplacement() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(BAS, 0,clone);
        assertFalse(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testEqualsAvecDeplacementImpossible() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(GAUCHE, 0,clone);
        faireAction(HAUT, 0, clone);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testAvecChangementInventaire() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Joueur joueurClone = clone.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        Plat tomatePlat = new Plat(tomate);
        joueurClone.prendre(tomatePlat);
        assertFalse(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testMemeObject() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        assertTrue(ComparerDonneesJeu(donneesJeu,donneesJeu));
    }

    @Test
    public void testChangementOrientation() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(GAUCHE, 0, clone);
        assertFalse(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testAvecNull() {
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        assertThrows(IllegalArgumentException.class, () -> ComparerDonneesJeu(donneesJeu,null));
    }

    @Test
    public void testEqualsJoueurAvecInventaireDifferent() {
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(DROITE, 0, donneesJeu);
        faireAction(HAUT, 0, donneesJeu);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(PRENDRE, 0, clone);
        assertFalse(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testEqualsTransformateur(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(GAUCHE, 0, donneesJeu);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(POSER, 0, clone);
        assertFalse(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testEqualsTransformateur2(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(GAUCHE, 0, donneesJeu);
        faireAction(POSER, 0, donneesJeu);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(UTILISER, 0, clone);
        assertFalse(ComparerDonneesJeu(donneesJeu,clone));
    }

}
