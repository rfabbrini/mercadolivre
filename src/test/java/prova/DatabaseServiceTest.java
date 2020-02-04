package prova;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;

import prova.modelo.DNA;
import prova.service.DatabaseService;

@SpringBootTest
class DatabaseServiceTest {

	@Autowired
	private JdbcTemplate jtm;
	@Autowired
	private DatabaseService databaseService;

	private String[] dnaSimio1 = {"CTGAGA", "CTAAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTA"};
	private String[] dnaSimio2 = {"CTGAGA", "CTGAGC", "TATCTT", "AGATTG", "CACCTA", "GGTTTT"};

	@AfterEach
	void limparDatabase() {
		removerTestes();
	}

	@Test
	void testInjection() {
		assertThat(jtm).isNotNull();
		assertThat(databaseService).isNotNull();
	}

	@Test
	void testGravarDNA() {
		List<DNA> list = readDatabase();
		int initSize = list.size();

		databaseService.gravarDNA(dnaSimio1);

		list = readDatabase();
		assertThat(list.size() - initSize).isEqualTo(1);
	}

	@Test
	void testGravarDNADuplicado() {
		List<DNA> list = readDatabase();
		int initSize = list.size();

		databaseService.gravarDNA(dnaSimio1);
		databaseService.gravarDNA(dnaSimio1);
		databaseService.gravarDNA(dnaSimio2);

		list = readDatabase();
		assertThat(list.size() - initSize).isEqualTo(2);
	}
	
	@Test
	void testLerDNAs() {
		List<DNA> list = readDatabase();
		int initSize = list.size();

		writeMockDatabase(dnaSimio1);

		list = databaseService.recuperarDNAs();
		assertThat(list.size() - initSize).isEqualTo(1);
	}
	
	@Test
	void testLimparDatabase() {
		writeMockDatabase(dnaSimio1);

		List<DNA> list = readDatabase();
		assertThat(list.size()).isEqualTo(1);

		databaseService.limparDatabase();

		list = readDatabase();
		assertThat(list.size()).isEqualTo(0);
	}

	private void writeMockDatabase(String[] dna) {
		String dnaString = Arrays.stream(dna).collect(Collectors.joining(";"));
		String sql = "INSERT INTO tDNA (dna) VALUES (?)";

		PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(sql);
		creatorFactory.addParameter(new SqlParameter(java.sql.Types.VARCHAR));

		PreparedStatementCreator creator = creatorFactory.newPreparedStatementCreator(new Object[]{dnaString});

		jtm.update(creator);
	}

	private List<DNA> readDatabase() {
		String sql = String.format("SELECT * FROM tDNA");
		return jtm.query(sql, new BeanPropertyRowMapper<>(DNA.class));
	}

	private void removerTestes() {
		String sql1 = String.format("DELETE FROM tDNA WHERE dna ='%s'", 
			Arrays.stream(dnaSimio1).collect(Collectors.joining(";")));
		jtm.update(sql1);

		String sql2 = String.format("DELETE FROM tDNA WHERE dna ='%s'", 
			Arrays.stream(dnaSimio2).collect(Collectors.joining(";")));
		jtm.update(sql2);
	}
}
