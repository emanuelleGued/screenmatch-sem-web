package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) //ignorar as propriedades que nao foram mensionadas abaixo
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao )// ele só le o json (nome imdbRating) e na hora de escrever o json ele vai usar o nome do atributo (avaliacao)
                       /*  @JsonProperty("imdbVotes") String votes) */  { // ele ler e escreve o json (nome imdbVotes)
}
