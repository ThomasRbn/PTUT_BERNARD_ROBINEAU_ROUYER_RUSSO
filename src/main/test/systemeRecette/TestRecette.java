package systemeRecette;

import com.overcooked.ptut.recettes.aliment.Pain;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Salade;
import com.overcooked.ptut.recettes.aliment.Viande;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.verificateur.VerificationAliment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
