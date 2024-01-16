package donneesJeu;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import org.junit.jupiter.api.Test;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;
import static com.overcooked.ptut.joueurs.utilitaire.Action.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestActionsAlimentsDonneesJeu {

    DonneesJeu donneesJeu;

    @Test
    public void testGetCoordonneesGenerateur() {
        donneesJeu = new DonneesJeu("niveaux/niveauTestSmall.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(GAUCHE, 0, donneesJeu);
        faireAction(POSER, 0, donneesJeu);
        faireAction(BAS, 0, donneesJeu);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(GAUCHE, 0, donneesJeu);
        faireAction(PRENDRE, 0, donneesJeu);
        Joueur joueur = donneesJeu.getJoueur(0);
        assertThrows(IndexOutOfBoundsException.class, () -> donneesJeu.getCoordonneesElement("Tomate").get(1));
    }

    @Test
    public void testGetCoordonneesCuisson() {
        donneesJeu = new DonneesJeu("niveaux/niveauTestSmall.txt", true);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(GAUCHE, 0, donneesJeu);
        faireAction(POSER, 0, donneesJeu);
        faireAction(BAS, 0, donneesJeu);
        faireAction(PRENDRE, 0, donneesJeu);
        faireAction(GAUCHE, 0, donneesJeu);
        faireAction(PRENDRE, 0, donneesJeu);
        Joueur joueur = donneesJeu.getJoueur(0);
        assertNotNull(joueur.getInventaire());
    }

}
