package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

/**
 * Classe correspondant a une boisson Colombia
 * 
 */
public class Pain extends Aliment {
	
	public Pain(){
		description = " Colombia";
	}
	
	/**
	 * @return prix de la boisson
	 */
	public double cout() {
		return 1.3;
	}

}
