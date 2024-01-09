package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.recettes.aliment.Tomate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestDonneesJeu {

    DonneesJeu donneesJeu;

    @Test
    public void testDeplacementCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau1.txt");
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(BAS, 0);
        Joueur joueurClone = clone.getJoueurs().get(0);
        assertNotEquals(Arrays.toString(joueur.getPosition()), Arrays.toString(joueurClone.getPosition()));
    }

    @Test
    public void testPrendreCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Joueur joueurClone = clone.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        joueur.prendre(tomate);
        assertNotEquals(joueur.getInventaire(), joueurClone.getInventaire());
    }

    @Test
    public void testGenerateurObjectCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau1.txt");
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(DROITE, 0);
        DonneesJeu clone2 = new DonneesJeu(clone);
        clone2.faireAction(HAUT, 0);
        System.out.println(Arrays.toString(clone2.getJoueurs().get(0).getPosition()));
        DonneesJeu clone3 = new DonneesJeu(clone2);
        clone3.faireAction(PRENDRE, 0);
        System.out.println(Arrays.toString(clone3.getJoueurs().get(0).getPosition()));
        Joueur joueurClone = clone3.getJoueurs().get(0);
        assertNotEquals(joueur.getInventaire(), joueurClone.getInventaire());
    }

    @Test
    public void testMur(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(HAUT, 0);
        Joueur joueurClone = clone.getJoueurs().get(0);
        assertEquals(Arrays.toString(joueur.getPosition()), Arrays.toString(joueurClone.getPosition()));
    }



}
