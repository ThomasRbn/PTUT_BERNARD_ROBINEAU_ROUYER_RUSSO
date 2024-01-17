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

    @Test
    public void testCloneNiveau2(){
        donneesJeu = new DonneesJeu("niveaux/niveau2.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCloneNiveau3(){
        donneesJeu = new DonneesJeu("niveaux/niveau3.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCloneNiveau4(){
        donneesJeu = new DonneesJeu("niveaux/niveau4.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCloneNiveauTest(){
        donneesJeu = new DonneesJeu("niveaux/niveauTest.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCloneNiveauTestSmall(){
        donneesJeu = new DonneesJeu("niveaux/niveauTestSmall.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }

    @Test
    public void testCloneNiveauTestSmall2(){
        donneesJeu = new DonneesJeu("niveaux/niveauTestSmall2.txt", true);
        DonneesJeu clone = new DonneesJeu(donneesJeu);
        assertTrue(ComparerDonneesJeu(donneesJeu,clone));
    }
}
