package prova.api;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import prova.modelo.DNA;
import prova.modelo.Stats;
import prova.service.DatabaseService;
import prova.service.SimianService;

@RestController
public class SimianController {

  @Autowired
  private SimianService simianService;

  @Autowired
  private DatabaseService databaseService;

  @PostMapping(value = "/simian", produces = { "application/json" })
	public ResponseEntity<String> simian(@RequestBody Map<String, Object> payload) {     

      JsonObject jsonRetorno = new JsonObject();
      ResponseEntity<String> retorno;
      try {               
        @SuppressWarnings("unchecked")
        List<String> lista = (List<String>)payload.get("dna");
        String[] dna = lista.toArray(new String[lista.size()]);

        // Verifica se a matriz e valida
        if(!simianService.verificarMatrizValida(dna)) {
          jsonRetorno.addProperty("error", "Matriz Inválida");
          retorno = new ResponseEntity<>(jsonRetorno.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

        } else {
          databaseService.gravarDNA(dna);
          boolean result = simianService.isSimian(dna);
          
          if(result) {
            jsonRetorno.addProperty("mensagem", "É Símio");
            retorno =  new ResponseEntity<>(jsonRetorno.toString(), HttpStatus.OK);
          } else {
            jsonRetorno.addProperty("mensagem", "Não é símio");
            retorno =  new ResponseEntity<>(jsonRetorno.toString(), HttpStatus.FORBIDDEN);
          }
        }
      } catch (Exception e) {
        jsonRetorno.addProperty("error", "Erro Interno - Parâmetro inválido");
        retorno =  new ResponseEntity<>(jsonRetorno.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return retorno;
  }

  @GetMapping(value = "/stats", produces = { "application/json" })
	public ResponseEntity<String> stats() {    
    try {       
      List<DNA> lista = databaseService.recuperarDNAs();
      Stats stats = simianService.calcularStats(lista);      
      return new ResponseEntity<>(new Gson().toJson(stats), HttpStatus.OK);

    } catch (Exception e) {
      JsonObject json = new JsonObject();
      json.addProperty("error", "Erro Interno");
      return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/clear", produces = { "application/json" })
	public ResponseEntity<String> clear() {    
    JsonObject json = new JsonObject();
    try {       
      databaseService.limparDatabase();  
      json.addProperty("mensagem", "Database cleared!");
      return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    } catch (Exception e) {
      json.addProperty("error", "Erro Interno");
      return new ResponseEntity<>(json.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}