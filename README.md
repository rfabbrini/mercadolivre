# Teste Símios - Mercado Livre

Por: Rodrigo Fabbrini

A aplicação foi desenvolvida conforme as recomendações.
Todos os fontes e compilados encontram-se neste repositório

## Testes
A aplicação foi desenvolvida utilizando TDD. 
Todos os testes utilizados para o desenvolvimento estão nos fontes na pasta "src/test/java/prova/".

## Aplicação
A aplicação foi criada utilizando Java, Spring boot e Docker. Todos os fontes e arquivos de configuração para execução local estão disponibilizados. A aplicação está configurada para receber requests na porta 8080.


## Endpoints
A aplicação está instalada e disponpivel no AWS.
São 3 enpoints disponíveis:

```rest
POST http://18.219.29.113/simian
```
A aplicação espera receber uma matriz NxN, contendo uma sequência de DNA. A aplicação irá avaliar se o DNA é de um símio e gravar na database, case seja uma sequencia de dna única. Existem 3 possíveis retornos:
  - 200 - OK - É Símio
  - 403 - Forbidden - Não é símio
  - 500 - Erro Interno - Parâmetro inválido / Matriz Inválida


```rest
GET http://18.219.29.113/stats
```
A aplicação retornada as estastísticas para os DNA cadastrados
  - countMutantDNA - Número de DNAs de símios
  - countHumanDNA - Número de DNAs de humanos
  - ratio - Proporção de símios para humanos. 
  
  OBS: Caso não haja humanos cadastrados, não é possível calcular o ratio (divisão por zero). Neste caso é retornado a quantidade de símios cadastrados.


```rest
GET http://18.219.29.113/clear
```
Para fins de testes, esse endpoint irá remover todos os dados cadastrados na database.

### Conclusão

Agradeço o tempo e a oportunidade.
Obrigado!
