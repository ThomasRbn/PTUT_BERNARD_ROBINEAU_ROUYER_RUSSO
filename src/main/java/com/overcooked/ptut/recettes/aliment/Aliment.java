package com.overcooked.ptut.recettes.aliment;

import com.overcooked.ptut.objet.Movable;

import java.util.Objects;

/**
 * Classe abstraite representant une boisson 
 * 
 */
public abstract class Aliment extends Movable {

	protected String nom = "Aliment inconnu";

	/**
	 * Description de l'aliment
	 * 
	 */
	protected String description = "Aliment inconnu";

	public Aliment(int[][] coordonnees) {
		super(coordonnees);
	}

	public Aliment() {
		super();
	}


	/**
	 * @return la description de la boisson  
	 */
	public String getDescription(){
		return description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString(){
		return this.getNom();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Aliment aliment = (Aliment) o;
		return aliment.nom.equals(nom) && aliment.description.equals(description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom, description);
	}
}
