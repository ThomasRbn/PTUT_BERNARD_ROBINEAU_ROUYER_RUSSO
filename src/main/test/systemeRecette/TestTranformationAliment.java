package systemeRecette;

import com.overcooked.ptut.objet.transformateur.Planche;
import com.overcooked.ptut.objet.transformateur.Poele;
import com.overcooked.ptut.objet.transformateur.Transformateur;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Pain;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.aliment.Salade;
import com.overcooked.ptut.recettes.etat.Cuisson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTranformationAliment {
    Aliment painCuit;
    Transformateur transformateur;
    @BeforeEach
    public void setUp() {
        painCuit = new Cuisson(new Pain());
        transformateur = new Poele(0, 0);
    }

    /**
     * Test de transformation fidèle
     */
    @Test
    public void testTransformationReussie() {
        transformateur.ajouterElem(new Pain());
        assertEquals(transformateur.transform(), painCuit);
    }

    /**
     * Test de transformation non fidèle, où il n'y a pas d'ingrédient
     */
    @Test
    public void testTransformationEchouee() {
        assertNull(transformateur.transform());
    }

    /**
     * Test de transformation non fidèle, où l'élément préparé n'est pas le même que celui attendu
     */
    @Test
    public void testTransformationEchouee2() {
        transformateur.ajouterElem(new Salade());
        assertNotEquals(transformateur.transform(), painCuit);
    }
}
