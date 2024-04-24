package br.com.alura.screenmatch;

import br.com.alura.screenmatch.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	// ctrl + alt + o >> limpa todos os importes que nao estao sendo utilizados na calsse
	// uma aplicação de linha de comando
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	// 7db19434
	//https://www.omdbapi.com/?t=gilmore+girls&apikey=7db19434

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibirMenu();


	}
}
