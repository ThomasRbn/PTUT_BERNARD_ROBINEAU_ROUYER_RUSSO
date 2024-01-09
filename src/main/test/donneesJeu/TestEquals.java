package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.recettes.aliment.Tomate;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEquals {

    DonneesJeu donneesJeu;

    @Test
    public void testEqualsSansToucher(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(donneesJeu.equals(clone));
    }

    @Test
    public void testCopieNiveau(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu donneesJeu2 = new DonneesJeu("niveaux/niveau0.txt");
        assertTrue(donneesJeu.equals(donneesJeu2));
    }

    @Test
    public void testEqualsAvecDeplacement(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(BAS, 0);
        assertFalse(donneesJeu.equals(clone));
    }

    @Test
    public void testEqualsAvecDeplacementImpossible(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(GAUCHE, 0);
        clone.faireAction(HAUT, 0);
        assertTrue(donneesJeu.equals(clone));
    }

    @Test
    public void testAvecChangementInventaire(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Joueur joueurClone = clone.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        joueurClone.prendre(tomate);
        assertFalse(donneesJeu.equals(clone));
    }

    @Test
    public void testMemeObject(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        assertTrue(donneesJeu.equals(donneesJeu));
    }

    @Test
    public void testChangementOrientation(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(GAUCHE, 0);
        assertFalse(donneesJeu.equals(clone));
    }

    @Test
    public void testAvecNull(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        assertFalse(donneesJeu.equals(null));
    }

}
