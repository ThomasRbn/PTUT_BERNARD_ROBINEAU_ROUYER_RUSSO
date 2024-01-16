package com.overcooked.ptut.vue.bloc;

import com.overcooked.ptut.entites.Generateur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GenerateurBloc extends BlocVue {
    public GenerateurBloc(Generateur generateur, double tailleCellule) {
        Rectangle rectangle = new Rectangle(tailleCellule, tailleCellule);
        Text text = new Text(String.valueOf(generateur.getType().charAt(0)).toUpperCase());
        text.setFont(Font.font(tailleCellule / 10 * 4));

        switch (generateur.getType()) {
            case "Salade":
                rectangle.setFill(Color.GREEN);
                break;
            case "Tomate":
                rectangle.setFill(Color.RED);
                break;
            case "Pain":
                rectangle.setFill(Color.BROWN);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + generateur.getAliment());
        }
        this.getChildren().addAll(rectangle, text);
    }
}
