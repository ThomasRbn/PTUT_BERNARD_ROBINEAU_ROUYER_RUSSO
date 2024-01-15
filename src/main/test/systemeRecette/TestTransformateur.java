package systemeRecette;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Tomate;
import com.overcooked.ptut.recettes.etat.Coupe;
import com.overcooked.ptut.recettes.etat.Cuisson;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTransformateur {

    DonneesJeu donneesJeu;

    @Test
    public void testDecouperPlat() {
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(PRENDRE, 0);
        DonneesJeu clone2 = new DonneesJeu(clone);
        clone2.faireAction(DROITE, 0);
        DonneesJeu clone3 = new DonneesJeu(clone2);
        clone3.faireAction(DROITE, 0);
        DonneesJeu clone4 = new DonneesJeu(clone3);
        clone4.faireAction(HAUT, 0);
        DonneesJeu clone5 = new DonneesJeu(clone4);
        System.out.println(clone5.getJoueurs().get(0).getInventaire().getNom());
        clone5.faireAction(POSER, 0);
        Transformateur t = (Transformateur) clone5.getObjetsFixes()[0][3];
        t.ajouterElem(clone4.getJoueurs().get(0).getInventaire());
        assertTrue(t.transform().equals(new Plat(new Coupe(new Tomate()))));
    }

    @Test
    public void testCuirePlat(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(PRENDRE, 0);
        DonneesJeu clone2 = new DonneesJeu(clone);
        clone2.faireAction(GAUCHE, 0);
        DonneesJeu clone5 = new DonneesJeu(clone);
        clone5.faireAction(POSER, 0);
        Transformateur t = (Transformateur) clone5.getObjetsFixes()[1][0];
        t.ajouterElem(clone.getJoueurs().get(0).getInventaire());
        Plat plat = t.transform();
        System.out.println(plat.getRecettesComposees());
        assertTrue(plat.equals(new Plat(new Cuisson(new Tomate()))));
    }

    @Test
    public void testCouperPuisCuirePlat(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        Joueur joueur = donneesJeu.getJoueurs().get(0);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        clone.faireAction(PRENDRE, 0);
        DonneesJeu clone2 = new DonneesJeu(clone);
        clone2.faireAction(DROITE, 0);
        DonneesJeu clone3 = new DonneesJeu(clone2);
        clone3.faireAction(DROITE, 0);
        DonneesJeu clone4 = new DonneesJeu(clone3);
        clone4.faireAction(HAUT, 0);
        DonneesJeu clone5 = new DonneesJeu(clone4);
        clone5.faireAction(POSER, 0);
        Transformateur t = (Transformateur) clone5.getObjetsFixes()[0][3];
        t.ajouterElem(clone4.getJoueurs().get(0).getInventaire());
        Plat plat = t.transform();
        DonneesJeu clone6 = new DonneesJeu(clone5);
        clone6.faireAction(PRENDRE, 0);
        System.out.println(clone6.getJoueurs().get(0).getInventaire().getNom());
        DonneesJeu clone7 = new DonneesJeu(clone6);
        clone7.faireAction(BAS, 0);
        DonneesJeu clone8 = new DonneesJeu(clone7);
        clone8.faireAction(POSER, 0);
        Transformateur t2 = (Transformateur) clone8.getObjetsFixes()[2][3];
        t2.ajouterElem(clone7.getJoueurs().get(0).getInventaire());
        assertTrue(t2.transform().equals(new Plat(new Cuisson(new Coupe(new Tomate())))));
    }
}
