package prova;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import prova.modelo.DNA;
import prova.modelo.Stats;
import prova.service.SimianService;

class SimianServiceTest {

	String[] matrizInvalida1 = {};
	String[] matrizInvalida2 = {"AA", "AAA"};
	String[] matrizInvalida3 = {"123", "456", "789"};

	String[] dnaSimio1 = {"CTGAGA", "CTAAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	String[] dnaSimio2 = {"CTGAGA", "CTGAGC", "TATCTT", "AGATTG", "CACCTA", "GATTTT"};
	String[] dnaSimio3 = {"CAAAAG", "CCCAGC", "TACTGT", "AGCCGG", "CGCCTA", "TCACTG"};

	String[] dnaSimioHorizontal = {"CCCGGG", "GGGCCC", "CCCGGG", "GGCCCC", "CCCGGG", "GGGCCC"};
	String[] dnaSimioVertical = {"CCCGGG", "GCGCCC", "CCCGGG", "GCGCCC", "CCCGGG", "GGGCCC"};
	String[] dnaSimioDiagonal = {"CCCGGG", "GCGCCC", "CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC"};

	String[] dnaHumano1 = {"CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC"};
	String[] dnaHumano2 = {"CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC"};
	String[] dnaHumano3 = {"CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC"};
	String[] dnaHumano4 = {"CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC", "CCCGGG", "GGGCCC"};

	@Test
	void testVerificarMatrizValida() throws Exception {		
		SimianService simianService = new SimianService();

		boolean resultSimio;
		resultSimio = simianService.verificarMatrizValida(matrizInvalida1);
		assertThat(resultSimio).isFalse();
		resultSimio = simianService.verificarMatrizValida(matrizInvalida2);
		assertThat(resultSimio).isFalse();
		resultSimio = simianService.verificarMatrizValida(matrizInvalida3);
		assertThat(resultSimio).isFalse();

		resultSimio = simianService.verificarMatrizValida(dnaSimio1);
		assertThat(resultSimio).isTrue();
		resultSimio = simianService.verificarMatrizValida(dnaHumano1);
		assertThat(resultSimio).isTrue();
	}

	@Test
	void testVerificarSimioHorizontal() throws Exception {		
		SimianService simianService = new SimianService();
		Method method = SimianService.class.getDeclaredMethod("checkForSimianHorizontal", String[].class);
		method.setAccessible(true);	

		boolean resultSimio1 = (boolean)method.invoke(simianService, new Object[]{dnaSimio1});
		assertThat(resultSimio1).isTrue();
		boolean resultSimio2 = (boolean)method.invoke(simianService, new Object[]{dnaSimio2});
		assertThat(resultSimio2).isTrue();
		boolean resultSimio3 = (boolean)method.invoke(simianService, new Object[]{dnaSimio3});
		assertThat(resultSimio3).isTrue();
		boolean resultHorizontal = (boolean)method.invoke(simianService, new Object[]{dnaSimioHorizontal});
		assertThat(resultHorizontal).isTrue();

		boolean resultHumano = (boolean)method.invoke(simianService, new Object[]{dnaHumano1});
		assertThat(resultHumano).isFalse();
	}

	@Test
	void testVerificarSimioVertical() throws Exception {		
		SimianService simianService = new SimianService();
		Method method = SimianService.class.getDeclaredMethod("checkForSimianVertical", String[].class);
		method.setAccessible(true);	

		boolean resultSimio1 = (boolean)method.invoke(simianService, new Object[]{dnaSimio1});
		assertThat(resultSimio1).isTrue();
		boolean resultSimio2 = (boolean)method.invoke(simianService, new Object[]{dnaSimio2});
		assertThat(resultSimio2).isTrue();
		boolean resultSimio3 = (boolean)method.invoke(simianService, new Object[]{dnaSimio3});
		assertThat(resultSimio3).isTrue();
		boolean resultVertical = (boolean)method.invoke(simianService, new Object[]{dnaSimioVertical});
		assertThat(resultVertical).isTrue();

		boolean resultHumano = (boolean)method.invoke(simianService, new Object[]{dnaHumano1});
		assertThat(resultHumano).isFalse();
	}

	@Test
	void testVerificarSimioDiagonal() throws Exception {		
		SimianService simianService = new SimianService();
		Method method = SimianService.class.getDeclaredMethod("checkForSimianDiagonal", String[].class);
		method.setAccessible(true);	

		boolean resultSimio1 = (boolean)method.invoke(simianService, new Object[]{dnaSimio1});
		assertThat(resultSimio1).isTrue();
		boolean resultSimio2 = (boolean)method.invoke(simianService, new Object[]{dnaSimio2});
		assertThat(resultSimio2).isTrue();
		boolean resultSimio3 = (boolean)method.invoke(simianService, new Object[]{dnaSimio3});
		assertThat(resultSimio3).isTrue();
		boolean resultDiagonal = (boolean)method.invoke(simianService, new Object[]{dnaSimioDiagonal});
		assertThat(resultDiagonal).isTrue();

		boolean resultHumano = (boolean)method.invoke(simianService, new Object[]{dnaHumano1});
		assertThat(resultHumano).isFalse();
	}

	@Test
	void testVerificarSimio() throws Exception {		
		SimianService simianService = new SimianService();

		assertThat(simianService.isSimian(dnaSimio1)).isTrue();
		assertThat(simianService.isSimian(dnaSimio2)).isTrue();
		assertThat(simianService.isSimian(dnaSimio3)).isTrue();
		assertThat(simianService.isSimian(dnaSimioHorizontal)).isTrue();
		assertThat(simianService.isSimian(dnaSimioVertical)).isTrue();
		assertThat(simianService.isSimian(dnaSimioDiagonal)).isTrue();
		assertThat(simianService.isSimian(dnaHumano1)).isFalse();
	}

	@Test
	void testCalcularStats() throws Exception {		
		SimianService simianService = new SimianService();
		List<DNA> lista = new ArrayList<>();
		lista.add(new DNA(Arrays.stream(dnaSimio1).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaSimio2).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaSimio3).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaSimioHorizontal).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaSimioVertical).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaSimioDiagonal).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaHumano1).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaHumano2).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaHumano3).collect(Collectors.joining(DNA.separador))));
		lista.add(new DNA(Arrays.stream(dnaHumano4).collect(Collectors.joining(DNA.separador))));

		Stats stats = simianService.calcularStats(lista);

		assertThat(stats.getCountMutantDNA()).isEqualTo(6);
		assertThat(stats.getCountHumanDNA()).isEqualTo(4);
		assertThat(stats.getRatio()).isEqualTo(1.5);
	}
}
