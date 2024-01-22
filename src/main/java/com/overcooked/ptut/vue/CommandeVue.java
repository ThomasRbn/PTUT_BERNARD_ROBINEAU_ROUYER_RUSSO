package com.overcooked.ptut.vue;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.vue.aliment.AlimentVue;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

import static com.overcooked.ptut.recettes.etat.Etat.*;

public class CommandeVue extends VBox {

    private List<Plat> plats;

    public CommandeVue(List<Plat> plats) {
        this.plats = plats;
        initMenu();
    }

    public void initMenu() {
        for (Plat plat : plats) {
            VBox root = new VBox();
            VBox ingredients = new VBox();
            for (int i = 0; i < plat.getRecettesComposees().size(); i++) {
                Aliment ingredientCourant = plat.getRecettesComposees().get(i);
                HBox ingredient = new HBox();
                ingredient.setSpacing(3);

                Text tabulation = new Text("\t");
                Text nomIngredient = new Text( ingredientCourant.getNom() + " " + getEtatAliment(ingredientCourant));
                AlimentVue cercle = AfficheurInfobulle.afficherEtatCercle(ingredientCourant, 10);
                ingredient.getChildren().addAll(tabulation, cercle, nomIngredient);
                ingredients.getChildren().add(ingredient);
            }
            root.getChildren().add(ingredients);
            root.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, null, null)));

            root.setSpacing(5);
            ingredients.setSpacing(5);
            root.setPadding(new Insets(5));

            this.setSpacing(10);
            this.setPadding(new Insets(10));
            this.getChildren().add(root);
        }
    }

    public static String getEtatAliment(Aliment aliment) {
        return switch (aliment.getEtat()) {
            case COUPE -> "coupé(e)";
            case CUIT -> "cuit(e)";
            case CUIT_ET_COUPE -> "cuit(e) et coupé(e)";
            default -> "";
        };
    }


}
