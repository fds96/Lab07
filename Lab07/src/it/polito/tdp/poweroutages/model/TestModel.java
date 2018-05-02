package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		//System.out.println(model.getNercList());
		model.worstCaseAnalysis(3, 4, 30);
		System.out.println( model.getSoluzione()+ "\n" +
				model.getSoluzione().size() + " " +model.getNumeroTotaleCoinvolti() + " " + model.getOreSoluzione());
		
		System.out.println("\n-------------------------------------\n");
	}
}
