//package br.com.alura.screenmatch;
//
//import br.com.alura.screenmatch.Principal.Main;
//import br.com.alura.screenmatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationSemWeb implements CommandLineRunner { // implements CommandLineRunner serve para aplicações para linha de comando
//	@Autowired
//	private SerieRepository repositorio;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationSemWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Main principal = new Main(repositorio);
//		principal.exibeMenu();
//	}
//}