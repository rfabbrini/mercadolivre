package prova.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

import prova.modelo.DNA;

@Component
public class DatabaseService {

	@Autowired
	private JdbcTemplate jtm;
	
	/** Grava um dna na base */
	public synchronized void gravarDNA(String[] dna) {
		try {
			String dnaString = Arrays.stream(dna).collect(Collectors.joining(DNA.separador));
			String sql = "INSERT INTO tDNA (dna) VALUES (?)";

			PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(sql);
			creatorFactory.addParameter(new SqlParameter(java.sql.Types.VARCHAR));

			PreparedStatementCreator creator = creatorFactory.newPreparedStatementCreator(new Object[]{dnaString});

			jtm.update(creator);			
		} catch(DuplicateKeyException e) {
			//Nao grava dnas duplicados
		}
	}
	
	/** Remove todos os DNAs da Database */
	public synchronized void limparDatabase() {
		String sql = "DELETE FROM tDNA";
		jtm.update(sql);			
	}

	public List<DNA> recuperarDNAs() {
		String sql = "SELECT * FROM tDNA";
		return jtm.query(sql, new BeanPropertyRowMapper<>(DNA.class));
	}
}
