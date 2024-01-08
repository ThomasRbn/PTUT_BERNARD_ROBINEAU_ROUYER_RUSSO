package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import org.junit.jupiter.api.Test;

public class TestDonneesJeu {

    DonneesJeu donneesJeu;

    @Test
    public void testCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        //joueur.set

    }
}
