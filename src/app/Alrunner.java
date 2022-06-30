package app;

import domain.impl.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.Scanner;

public class Alrunner {
   static Lexemas lexemas = new Lexemas();
   static Sintaxe sintaxe = new Sintaxe();
   static StopWords sw = new StopWords();
   static TabelaSimbolos ts = new TabelaSimbolos();
   static KeyWord keyWord = new KeyWord();
   static Token token = new Token();
   static ArquivoInvertido ai = new ArquivoInvertido();

   // variavel que controla se o arquivo mudou ou nao, true para mudou e false para não mudou
   static boolean change = true;

   //Foi utilizada a biblioteca snowball para o tratamento dos lexemas
   //Pode ser feito o download no dominio http://snowball.tartarus.org/download.html
   // Repare que na saída da frase processada tem valores repetidos mas na listas de tokens não
    public static void main(String[] args) throws IOException {
        Scanner ler = new Scanner(System.in);
        //Frase padrao do chatbot
        System.out.println("Sou a assistente virtual da Logitech, em que posso ajudar? ");


        //Exemplo de frase inserida pelo usuario, pode ser qualquer uma
        String texto = ("Qual mouse, : têm um bom custo-benefício beneficio custo qual? ª ");

        // se o arquivo tiver mudado ele refaz o processo de povoamento
        if (change)
            ai.povoar();
        else
            ai.dataReader();
        // inicializa as stopwords
        sw.init();

        while (!(texto = ler.nextLine()).equals("Sair") ||sintaxe.analisar(texto) == null){
                executar(texto);
        }
            ai.dataWriter();
        System.out.println("Até a próxima!!!");

    }

    public static void executar(String texto) {
       // token.tokens.clear();
        texto = texto.replaceAll("\\p{Punct}", " ");
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        StringBuilder sb = new StringBuilder();
        Scanner ler = new Scanner(texto);
        for (Scanner it = ler; it.hasNext(); ) {
            String word = it.next().toLowerCase();

            if (keyWord.analisar(word) != null && sw.analisar(word) == null) {
                    sb.append(keyWord.analisar(word)).append(" ");
                    token.analisar(lexemas.analisar(keyWord.analisar(word)));
            }else if(ts.analisar(lexemas.analisar(word))== null && ts.analisar(word)== null && sw.analisar(word) == null){
                    ts.addSymbolTable(lexemas.analisar(word));
                    sb.append(lexemas.analisar(word)).append(" ");
                    token.analisar(lexemas.analisar(word));
            } else if (lexemas.analisar(word) != null && sw.analisar(word) == null) {
                sb.append(lexemas.analisar(word)).append(" ");
                lexemas.analisar(lexemas.analisar(word));
            }

        }
        String resposta = ai.tfidf(token.tokens);
        if(resposta != null)
            System.out.println(resposta);
        else
            System.out.println("Nao entendi, refaca a pergunta de uma forma diferente");
    }

    public static void imprimir(StringBuilder sb){
        if(!sb.isEmpty()) {
            System.out.println("Frase processada: " + sb.toString());
            System.out.println("Tabela de simbolos: " + ts.table.toString());
            System.out.println("Tokens: " + token.tokens.toString());
        }
    }


}
