package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Tomate;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCoordonnees {

    DonneesJeu donneesJeu;

    @Test
    public void testGetCoordonneesGenerateur(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        int[] coordonneesGenerateur= donneesJeu.getCoordonneesElement("Generateur").getFirst();
        assertEquals(Arrays.toString(coordonneesGenerateur), Arrays.toString(new int[]{0, 3}));
    }

    @Test
    public void testGetCoordonneesPlanDeTravail(){
        donneesJeu = new DonneesJeu("niveaux/niveau0.txt", true);
        int[] coordonneesPlanDeTravail= donneesJeu.getCoordonneesElement("Plan de travail").getFirst();
        int[] coordonneesPlanDeTravail2= donneesJeu.getCoordonneesElement("Plan de travail").get(1);
        int[] coordonneesPlanDeTravail3= donneesJeu.getCoordonneesElement("Plan de travail").get(2);
        assertEquals(Arrays.toString(coordonneesPlanDeTravail), Arrays.toString(new int[]{0, 0}));
        assertEquals(Arrays.toString(coordonneesPlanDeTravail2), Arrays.toString(new int[]{0, 1}));
        assertEquals(Arrays.toString(coordonneesPlanDeTravail3), Arrays.toString(new int[]{0, 2}));
    }

    @Test
    public void testGetCoordonneesDecoupe(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        int[] coordonneesDecoupe= donneesJeu.getCoordonneesElement("Planche").getFirst();
        assertEquals(Arrays.toString(coordonneesDecoupe), Arrays.toString(new int[]{0, 3}));
    }

    @Test
    public void testGetCoordonneesCuisson(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        int[] coordonneesPoele= donneesJeu.getCoordonneesElement("Cuisson").getFirst();
        int[] coordonneesPoele2= donneesJeu.getCoordonneesElement("Cuisson").get(1);
        assertEquals(Arrays.toString(coordonneesPoele), Arrays.toString(new int[]{1, 0}));
        assertEquals(Arrays.toString(coordonneesPoele2), Arrays.toString(new int[]{2, 3}));
    }

    @Test
    public void testGetCoordonneesTomate(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(BAS, 0, donneesJeu);
        faireAction(POSER, 0, donneesJeu);
        int[] coordonneesTomate= donneesJeu.getCoordonneesElement("Tomate").getFirst();
        int[] coordonneesTomate2= donneesJeu.getCoordonneesElement("Tomate").get(1);
        assertEquals(Arrays.toString(coordonneesTomate), Arrays.toString(new int[]{0, 1}));
        assertEquals(Arrays.toString(coordonneesTomate2), Arrays.toString(new int[]{2, 1}));
    }

    @Test
    public void testGetCoordonneesSaladeTomate(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(DROITE, 0, donneesJeu);
        faireAction(HAUT, 0, donneesJeu);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(BAS, 0, donneesJeu);
        faireAction(POSER, 0, donneesJeu);
        System.out.println(donneesJeu.getCoordonneesElement("SaladeTomate"));
        int[] coordonneesSaladeTomate= donneesJeu.getCoordonneesElement("SaladeTomate").getFirst();
        assertEquals(Arrays.toString(coordonneesSaladeTomate), Arrays.toString(new int[]{2, 2}));
    }

}
