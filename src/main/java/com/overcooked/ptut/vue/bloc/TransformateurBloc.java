package com.overcooked.ptut.vue.bloc;

import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Circle;

public class TransformateurBloc extends BlocVue {

    private Circle aliment;
    private ProgressBar progressBar;

    public TransformateurBloc(Circle aliment, ProgressBar progressBar) {
        this.aliment = aliment;
        this.progressBar = progressBar;
        this.getChildren().addAll(aliment, progressBar);
    }

    public Circle getAliment() {
        return aliment;
    }

    public void setAliment(Circle aliment) {
        this.aliment = aliment;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
