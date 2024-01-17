package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu.ComparerDonneesJeu;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testCloneNiveau {

    DonneesJeu donneesJeu;

    @Test
    public void testCloneNiveau0(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCloneNiveau1(){
        donneesJeu = new DonneesJeu("niveaux/niveau1.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }
}
