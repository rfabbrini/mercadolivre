package prova.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {
  private int countMutantDNA;
  private int countHumanDNA;
  private double ratio;

  public Stats(int countMutantDNA, int countHumanDNA) {
    this.countMutantDNA = countMutantDNA;
    this.countHumanDNA = countHumanDNA;
    getRatio();
  }

  public double getRatio() {
    if(countHumanDNA > 0) {
      ratio = 1.0 * countMutantDNA / countHumanDNA;
    } else {
      ratio = 1.0 * countMutantDNA;
    }
    return ratio;
  }

  public int getCountMutantDNA() {
    return countMutantDNA;
  }

  public void setCountMutantDNA(int countMutantDNA) {
    this.countMutantDNA = countMutantDNA;
  }

  public int getCountHumanDNA() {
    return countHumanDNA;
  }

  public void setCountHumanDNA(int countHumanDNA) {
    this.countHumanDNA = countHumanDNA;
  }
}