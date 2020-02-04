package prova.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import prova.modelo.DNA;
import prova.modelo.Stats;

@Component
public class SimianService {

	//Numero de genes consecutivos para ser um simio
	private int qtdeGenesSimio = 4;	

	//Verifica se a matriz e valida
	public boolean verificarMatrizValida(String[] dna) {
		//Matriz nao pode ser vazia
		if(dna.length == 0) {
			return false;
		}

		//Matriz deve ser NxN
		int n = dna.length;
		for(String linha : dna) {
			if(linha.length() != n) {
				return false;
			}
		}

		//Matrix deve conter apenas A, T, C, G
		for(String linha : dna) {
			for(int i = 0; i < linha.length(); i++) {
				char c = linha.charAt(i);
				if(!(c == 'A' || c == 'T' || c == 'C' || c == 'G')) {
					return false;
				}
			}
		}

		return true;
	}

	//Verifica se o dna é de um simian
	public boolean isSimian(String[] dna) {
		return checkForSimianHorizontal(dna) || checkForSimianVertical(dna) || checkForSimianDiagonal(dna);
	}

	//Verifica as horizontais para Símio
	private boolean checkForSimianHorizontal(String[] dna) {
		boolean retorno = false;

		//Itera pelas linhas
		for(String linha : dna) {
			int count = 0;
			char ultimoGene = '0';

			//Itera pelos genes
			for(int i=0; i < linha.length(); i++) {
				char gene = linha.charAt(i);

				if(gene == ultimoGene) {
					count++;
				} else {
					ultimoGene = gene;
					count = 1;
					if(i + qtdeGenesSimio > linha.length()) { //Termina caso nao possa caber outra sequecia de genes
						break;
					}
				}

				if(count == qtdeGenesSimio) {
					retorno = true;
					break;
				}
			}

			if(retorno) { //Sucesso
				break;
			}
		}

		return retorno;
	}

	//Verifica as verticais para Símio
	private boolean checkForSimianVertical(String[] dna) {
		//Inverter a matriz
		String[] dnaInvetido = new String[dna.length];
		for(int i = 0; i < dna.length; i++) {
			StringBuffer linha = new StringBuffer();
			for(int t = 0; t < dna.length; t++) {
				linha.append(dna[t].charAt(i));
			}
			dnaInvetido[i] = linha.toString();
		}

		return checkForSimianHorizontal(dnaInvetido);
	}
	
	//Verifica as diagonais para Símio
	private boolean checkForSimianDiagonal(String[] dna) {
		//Extrai as diagonais da matriz
		List<String> dnaDiagonais = new ArrayList<>();
		extrairDiagonais(dnaDiagonais, dna);

		//Inverter matriz horizontalmente e extrair as diagonais
		String[] dnaInvetido = new String[dna.length];
		for(int i = 0; i < dna.length; i++) {
			StringBuffer linha = new StringBuffer();
			for(int t = 1; t <= dna.length; t++) {
				linha.append(dna[i].charAt(dna.length - t));
			}
			dnaInvetido[i] = linha.toString();
		}
		extrairDiagonais(dnaDiagonais, dnaInvetido);

		String[] diagonais = dnaDiagonais.toArray(new String[dnaDiagonais.size()]);
		return checkForSimianHorizontal(diagonais);
	}		

	//private extrai as diagonais da esquerda para direita
	private void extrairDiagonais(List<String> lista, String[] dna) {
		//Itera pelas linhas
		for(int i = 0; i < dna.length; i++) {
			if(i + qtdeGenesSimio > dna.length) { //Nao ha mais diagonais disponiveis
				break;
			}

			//Itera pelas colunas
			for(int t = 0; t < dna.length; t++) {		
				if(t + qtdeGenesSimio > dna.length) { //Nao ha mais diagonais disponiveis
					break;
				}

				//Itera pelas diagonais
				StringBuffer linha = new StringBuffer();
				for(int y = 0; y < qtdeGenesSimio; y++) {			
					linha.append(dna[i + y].charAt(t + y));
				}
				lista.add(linha.toString());
			}
		}
	}

	/** Calcula as Stats */
	public Stats calcularStats(List<DNA> lista) {
		int countSimios = 0;
		int countHumanos = 0;

		for(DNA dna : lista) {
			if(isSimian(dna.getDnaArray())) {
				countSimios++;
			} else {
				countHumanos++;
			}
		}

		return new Stats(countSimios, countHumanos);
	}
}
