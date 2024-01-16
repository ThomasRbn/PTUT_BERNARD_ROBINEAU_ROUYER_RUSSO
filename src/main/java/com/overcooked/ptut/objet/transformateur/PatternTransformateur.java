package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.vue.aliment.AlimentVue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PatternTransformateur {


    public static AlimentVue afficherCuisson(AlimentVue circle) {
        circle.setFill(((Color) circle.getFill()).darker());
        return circle;
    }

    public static AlimentVue afficherCoupe(AlimentVue circle) {
        Canvas canvas = new Canvas(circle.getRadius() * 2, circle.getRadius() * 2);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(circle.getFill());
        gc.fillOval(0, 0, circle.getRadius() * 2, circle.getRadius() * 2);

        Image image = canvas.snapshot(null, null);
        circle.setFill(new ImagePattern(image, 0, 0, circle.getRadius() * 2, circle.getRadius() * 2, false));
        return circle;
    }
}

