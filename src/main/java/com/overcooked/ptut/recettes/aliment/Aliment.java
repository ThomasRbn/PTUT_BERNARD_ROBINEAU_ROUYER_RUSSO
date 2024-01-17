package com.overcooked.ptut.recettes.aliment;

import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.etat.Etat;

import java.util.Objects;

/**
 * Classe abstraite representant un aliment, avec un nom et une description
 * 
 */
public class Aliment extends Mouvable {

	/**
	 * Etat de l'aliment
	 * 0 : cru
	 * 1 : cuit
	 * 2 : coupe
	 * 3 : cuit et coupe
	 */
	int etat;

	protected String nom = "Aliment inconnu";

	/**
	 * Description de l'aliment
	 * 
	 */
	protected String description = "Aliment inconnu";

	public Aliment(int[] coordonnees) {
		super(coordonnees);
	}

	public Aliment() {
		super();
		etat = 0;
	}

	public Aliment(int etat){
		super();
		this.etat = etat;
	}

	public Aliment(Aliment a){
		super(a);
		this.nom = a.nom;
		this.description = a.description;
		this.etat = a.etat;
	}

	public Aliment(String nom, String description) {
		super();
		this.nom = nom;
		this.description = description;
		this.etat = 0;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		if(etat < 0 || etat > 3){
			throw new IllegalArgumentException("L'etat de l'aliment doit etre compris entre 0 et 3");
		}
		this.etat = Etat.transformEtat(this.etat, etat);
	}

	public void decouper(){
		this.setEtat(Etat.COUPE);
	}

	public void cuire(){
		this.setEtat(Etat.CUIT);
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
		return this.getNom() + " " + this.getEtat();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Aliment aliment = (Aliment) o;
		return etat == aliment.etat && Objects.equals(nom, aliment.nom) && Objects.equals(description, aliment.description);
	}

	public boolean equalsType(Aliment aliment){
		return this.nom.equals(aliment.nom);
	}

	public Aliment cloneAlim(){
		return new Aliment(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom, description);
	}
}
