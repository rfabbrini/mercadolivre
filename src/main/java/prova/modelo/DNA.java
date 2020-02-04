package prova.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DNA {
  private String dna;

	//Separador
  public static String separador = ";";
  
  public DNA() {
  }  
  
  public DNA(String dna) {
    this.dna = dna;
  }  

  //Transforma a string em um array
  public String[] getDnaArray() {
    return dna.split(DNA.separador);
  }

  public String getDna() {
    return dna;
  }

  public void setDna(String dna) {
    this.dna = dna;
  }
}