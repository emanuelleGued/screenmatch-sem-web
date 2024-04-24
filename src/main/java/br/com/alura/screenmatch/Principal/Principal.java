package br.com.alura.screenmatch.Principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String ENDERECO = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=7db19434";
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();


    public void exibirMenu() {
        System.out.println("Digite o nome da serie: ");
        var nomeSerie = sc.nextLine();

        var json = consumoApi.obterDados( ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        System.out.println(dados);
        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (DadosEpisodio dadosEpisodio : episodiosTemporada) {
//                System.out.println(dadosEpisodio.titulo());
//
//            }
//        }
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
//        temporadas.forEach(System.out::println);

        // Um stream em Java é um fluxo de dados que permite realizar operações encadeadas em coleções.
        // map transforma o programa(faz uma alteração)
        //O método flatMap é utilizado para transformar e aglutinar os elementos de uma coleção em uma única lista
        // filter filtra uma parte do programa(retorna aquilo que vc quer filtrar)

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
                //.toList(); // vai gerar uma lista imutavel

//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e -> System.out.println("ordenação " + e))
//                .limit(5)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios =  temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                .map(d -> new Episodio(t.numero(), d)))
                        .collect(Collectors.toList()); // usa o map para transformar os dados epsodios em novos epsodios

        episodios.forEach(System.out::println);


        // findAny:(resposta mais rapida e nao o mesmo resultado) o que ele achar primeiro ele retorna, nao garante que sempre vai vir aquele "episodio"
        //findFirst: ele sempre usa a mesma ordem, sempre volta o mesmo resultado

//        System.out.println("Digite um trecho do titulo do episodio: ");
//        var trechoTitulo = sc.nextLine();
//        // Optional é um conteiner que pode ou nao conter um valor não nulo
//        // serve para tratar a presença ou ausência de um valor de maneira mais segura.
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado!");
//            System.out.println("Temporada: " + episodioBuscado.get());
//        }else{
//            System.out.println("Episodio não encontrado!");
//        }

//        System.out.println("A partir de que ano você deseja ve os epsódios? ");
//        var ano = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e ->e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Epsodio: " + e.getTemporada() +
//                                " Data lançamento" + e.getDataLancamento().format(formatador)
//                ));


        // cria um mapa que associa cada temporada de uma série com a média das avaliações dos episódios dessa temporada. Isso é feito utilizando a função groupingBy para agrupar os episódios por temporada e, em seguida, a função averagingDouble para calcular a média das avaliações de cada temporada.
        // O resultado é um mapa onde a chave representa a temporada e o valor representa a média das avaliações. Dessa forma, podemos visualizar de maneira organizada e eficiente as avaliações por temporada.
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        //gerando estatisticas relevantes:
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Media: " + est.getAverage());
        System.out.println("Melhor episodio: " + est.getMax());
        System.out.println("Pior episodio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
