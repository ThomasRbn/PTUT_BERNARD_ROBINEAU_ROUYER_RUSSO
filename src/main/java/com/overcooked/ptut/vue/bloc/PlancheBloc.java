package com.overcooked.ptut.vue.bloc;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlancheBloc extends Bloc {
    public PlancheBloc() {
        this.setStyle("-fx-background-color: #ffffff;");
        this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
}
