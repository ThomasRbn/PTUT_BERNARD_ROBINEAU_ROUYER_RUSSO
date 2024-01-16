package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.objet.Depot;
import com.overcooked.ptut.objet.PlanDeTravail;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Tomate;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestDonneesJeu {

    DonneesJeu donneesJeu;

    @Test
    public void testDeplacementCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau3.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(BAS, 0, clone);
        Joueur joueurClone = clone.getJoueurs().get(0);
        assertNotEquals(Arrays.toString(joueur.getPosition()), Arrays.toString(joueurClone.getPosition()));
    }

    @Test
    public void testDeposerDepot(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Bloc[][] blocs = clone.getObjetsFixes();
        ((Depot)blocs[4][2]).deposerPlat(new Plat(new Tomate()));
        assertNotEquals(((Depot)blocs[4][2]).getPlatsDeposes().size(), donneesJeu.getObjetsFixes()[4][2]);
    }


    @Test
    public void testPrendreCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Joueur joueurClone = clone.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        Plat tomatePlat = new Plat(tomate);
        joueurClone.prendre(tomatePlat);
        assertNotEquals(joueur.getInventaire(), joueurClone.getInventaire());
    }

    @Test
    public void testPoserCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        Joueur joueurClone = clone.getJoueurs().get(0);
        Tomate tomate = new Tomate();
        Plat tomatePlat = new Plat(tomate);
        joueurClone.prendre(tomatePlat);
        DonneesJeu clone2 = new DonneesJeu(clone);
        Joueur joueurClone2 = clone2.getJoueurs().get(0);
        joueurClone2.poser();
        assertEquals(joueur.getInventaire(), joueurClone2.getInventaire());
    }

    @Test
    public void testGenerateurObjectCloneDonneesJeu(){
        donneesJeu = new DonneesJeu("niveaux/niveau1.txt" ,true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(DROITE, 0, clone);
        DonneesJeu clone2 = new DonneesJeu(clone);
        faireAction(HAUT, 0, clone2);
        DonneesJeu clone3 = new DonneesJeu(clone2);
        faireAction(PRENDRE, 0, clone3);
        Joueur joueurClone = clone3.getJoueurs().get(0);
        assertNotEquals(joueurClone.getInventaire(), joueur.getInventaire());
    }

    @Test
    public void testMur(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        InputStream in = new ByteArrayInputStream("HUMAIN".getBytes());
        System.setIn(in);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(HAUT, 0, clone);
        Joueur joueurClone = clone.getJoueurs().get(0);
        assertEquals(Arrays.toString(joueur.getPosition()), Arrays.toString(joueurClone.getPosition()));
    }

    @Test
    public void testInventaireMur(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        InputStream in = new ByteArrayInputStream("HUMAIN".getBytes());
        System.setIn(in);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        faireAction(PRENDRE, 0, clone);
        DonneesJeu clone2 = new DonneesJeu(clone);
        faireAction(BAS, 0, clone2);
        DonneesJeu clone3 = new DonneesJeu(clone2);
        faireAction(POSER, 0, clone3);
        DonneesJeu clone4 = new DonneesJeu(clone3);
        Bloc planDeTravail = clone4.getObjetsFixes()[2][1];
        assertNotNull(((PlanDeTravail) planDeTravail).getInventaire());
    }

    @Test
    public void testInventaireMurSansClone(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        InputStream in = new ByteArrayInputStream("HUMAIN".getBytes());
        System.setIn(in);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(BAS, 0, donneesJeu);
        faireAction(POSER, 0, donneesJeu);
        Bloc planDeTravail = donneesJeu.getObjetsFixes()[2][1];
        assertNotNull(((PlanDeTravail) planDeTravail).getInventaire());
    }

}
