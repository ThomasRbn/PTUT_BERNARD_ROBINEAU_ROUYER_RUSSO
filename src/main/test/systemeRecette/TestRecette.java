package systemeRecette;

import com.overcooked.ptut.recettes.aliment.*;
import com.overcooked.ptut.recettes.etat.Coupe;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.verificateur.VerificationAliment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecette {
    Plat painSalade;
    Plat burger;
    VerificationAliment vf;
    @BeforeEach
    public void setUp() {
        painSalade = new Plat("PainSalade", new Pain(), new Salade());
        burger = new Plat("Burger", new Pain(), new Cuisson(new Viande()));
        vf = new VerificationAliment(List.of(painSalade, burger));
    }

    /**
     * Test de recette fidèle
     */
    @Test
    public void testRecetteReussie() {
        assertEquals(vf.verifiercompatibilite(List.of(new Pain(), new Cuisson(new Viande()))), burger);
    }

    /**
     * Test de recette non fidèle, non compatible, où il manque des ingrédients
     */
    @Test
    public void testRecetteEchouee() {
        assertNull(vf.verifiercompatibilite(List.of(new Pain())));
    }

    /**
     * Test de recette non fidèle, non compatible, où il y a trop d'ingrédients
     */
    @Test
    public void testRecetteEchouee2() {
        assertNull(vf.verifiercompatibilite(List.of(new Pain(), new Cuisson(new Viande()), new Salade())));
    }

    /**
     * Test de recette non fidèle, non compatible, où il y a tous les ingrédients mais certains sont en double
     */
    @Test
    public void testRecetteEchouee3() {
        assertNull(vf.verifiercompatibilite(List.of(new Pain(), new Cuisson(new Viande()), new Pain())));
    }

    @Test
    public void testRecetteEquals() {
        assertEquals(painSalade, new Plat("PainSalade", new Pain(), new Salade()));
    }

    @Test
    public void testRecetteEquals2() {
        Plat plat1 = new Plat("PainSalade", new Pain(), new Salade());
        Plat plat2 = new Plat("PainSalade",  new Salade(), new Pain());
        assertEquals(plat1, plat2);
    }

    @Test
    public void testRecetteEquals3() {
        Plat plat1 = new Plat("PainSalade", new Pain(), new Salade());
        Plat plat2 = new Plat("PainSalade",  new Salade(), new Pain());
        assertTrue(plat1.equals(plat2));
    }

    @Test
    public void testRecetteEquals4() {
        Plat plat1 = new Plat("TomateSalade", new Tomate(), new Salade());
        Plat plat2 = new Plat("PainSalade",  new Salade(), new Pain());
        assertFalse(plat1.equals(plat2));
    }

    @Test
    public void testRecetteEquals5() {
        Plat plat1 = new Plat("PainSalade", new Pain(), new Salade());
        Plat plat2 = plat1;
        assertTrue(plat1.equals(plat2));
    }

    @Test
    public void testRecetteEquals6() {
        Plat plat1 = new Plat("PainSalade", new Pain(), new Salade());
        assertFalse( plat1.equals(null));
    }

    @Test
    public void testRecetteDoublonNotEquals(){
        Plat plat1 = new Plat("PainSalade", new Pain(), new Salade());
        Plat plat2 = new Plat("PainSalade", new Pain(), new Salade());
        plat2.ajouterAliment(new Pain());
        assertFalse(plat1.equals(plat2));
    }
}
