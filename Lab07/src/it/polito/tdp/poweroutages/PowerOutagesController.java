package it.polito.tdp.poweroutages;


	/**
	 * Sample Skeleton for 'PowerOutages.fxml' Controller Class
	 */

	import java.net.URL;
	import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;
	
	public class PowerOutagesController {
		
		private Model model;

	
	    @FXML // ResourceBundle that was given to the FXMLLoader
	    private ResourceBundle resources;

	    @FXML // URL location of the FXML file that was given to the FXMLLoader
	    private URL location;

	    @FXML // fx:id="comboNerc"
	    private ComboBox<String> comboNerc; // Value injected by FXMLLoader

	    @FXML // fx:id="txtYears"
	    private TextField txtYears; // Value injected by FXMLLoader

	    @FXML // fx:id="txtHours"
	    private TextField txtHours; // Value injected by FXMLLoader

	    @FXML // fx:id="txtResult"
	    private TextArea txtResult; // Value injected by FXMLLoader

	    @FXML
	    void doWorstCaseAnalysis(ActionEvent event) {
	    	int id = model.getNercByName(comboNerc.getValue()).getId();
	    	int anniMax = Integer.parseInt(this.txtYears.getText());
	    	int oreMax = Integer.parseInt(this.txtHours.getText());
	    	model.worstCaseAnalysis(id, anniMax, oreMax);
	    	txtResult.clear();
	    	txtResult.appendText("Le persone coinvolte sono: " + model.getNumeroTotaleCoinvolti()+ "\n");
	    	txtResult.appendText("Il numero totale di ore è: " + model.getOreSoluzione()+ "\n");
	    	for(PowerOutages p : model.getSoluzione()) {
	    		txtResult.appendText(p.toString()+ "\n");
	    	}
	    }

	    @FXML // This method is called by the FXMLLoader when initialization is complete
	    void initialize() {
	        assert comboNerc != null : "fx:id=\"comboNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
	        
	        
	    }

		public void setModel(Model model) {
			this.model=model;	
			for(Nerc n : this.model.getNercList()) {
	        	comboNerc.getItems().add(n.getValue());
	        }
		}
	

}