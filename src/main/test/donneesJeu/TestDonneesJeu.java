package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.joueurs.utilitaire.Action.BAS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestDonneesJeu {

    DonneesJeu donneesJeu;

    @Test
    public void testDeplacementCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        joueur.deplacer(BAS);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        int [] posJ1 = {donneesJeu.getJoueurs().get(0).getPosition()[0], donneesJeu.getJoueurs().get(0).getPosition()[1]};
        int [] posJ2 = {clone.getJoueurs().get(0).getPosition()[0], clone.getJoueurs().get(0).getPosition()[1]};
        assertNotEquals(posJ1, posJ2);
    }
}
