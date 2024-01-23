package com.overcooked.ptut.recettes.aliment;

import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.etat.Etat;

import java.util.List;
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

	public Aliment(String nom){
		super();
		this.nom = nom;
		this.etat = 0;
	}

	public Aliment(Aliment a){
		super(a);
		this.nom = a.nom;
		this.etat = a.etat;
	}

	public Aliment(String nom, String description) {
		super();
		this.nom = nom;
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

	public boolean doitCuire(Plat platBut){
		// Aliment correspondant dans le plat but
		List<Aliment> alimentsBut = platBut.getRecettesComposees();
		Aliment alimentBut = null;
		for (Aliment a : alimentsBut) {
			if (a.getNom().equals(this.getNom())) {
				alimentBut = a;
				break;
			}
		}
		// On récupère l'état de l'aliment actuellement et on vérifie
		if(alimentBut == null){
			return false;
		}
		int etat = this.getEtat();
		int etatBut = alimentBut.getEtat();
		if(etat == 0 && etatBut == 1){
			return true;
		}
		return (etat == 2 || etat == 0) && etatBut == 3;

	}

	public boolean doitEtreCoupe(Plat platBut) {
		Aliment alimentBut = null;
		List<Aliment> alimentsBut = platBut.getRecettesComposees();
		int iterations = 0;
		for (Aliment a : alimentsBut) {
			if (a.getNom().equals(this.getNom())) {
				alimentBut = a;
				break;
			}
			iterations++;
		}
		// On récupère l'état de l'aliment actuellement et on vérifie

		if(alimentBut == null){
			return false;
		}
		int etat = this.getEtat();
		int etatBut = alimentBut.getEtat();
		if(etat == 0 && etatBut == 2){
			return true;
		}
		return (etat == 1 || etat == 0) && etatBut == 3;
	}

	public String getEtatNom() {
		return this.etat > 0 ? this.nom + this.etat : this.nom;
	}

	public String getNom() {
		return this.nom;
	}

	public String toString(){
		return this.getEtatNom();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null ) return false;
		Aliment aliment = (Aliment) o;
		return etat == aliment.etat && nom.equals(aliment.nom);
	}

	public boolean equalsType(Aliment aliment){
		return this.nom.equals(aliment.nom);
	}

	public Aliment cloneAlim(){
		return new Aliment(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}
}
