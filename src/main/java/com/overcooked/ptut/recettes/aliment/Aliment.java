package com.overcooked.ptut.recettes.aliment;

/**
 * Classe abstraite representant une boisson 
 * 
 */
public abstract class Aliment {

	protected String nom = "Aliment inconnu";

	/**
	 * Description de la  boisson 
	 * 
	 */
	protected String description = "Aliment inconnu";
	
	
	/**
	 * @return la description de la boisson  
	 */
	public String getDescription(){
		return description;
	}

	public String getNom() {
		return nom;
	}

	public String toString(){
		return this.getNom() + " " + this.getDescription();
	}

}
