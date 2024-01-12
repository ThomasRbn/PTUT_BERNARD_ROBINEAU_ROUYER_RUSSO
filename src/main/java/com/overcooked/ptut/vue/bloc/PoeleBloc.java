package com.overcooked.ptut.vue.bloc;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PoeleBloc extends Bloc {
    public PoeleBloc() {
        this.setStyle("-fx-background-color: #ffe728;");
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
}
