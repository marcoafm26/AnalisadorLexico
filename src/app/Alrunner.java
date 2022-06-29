package app;

import domain.impl.*;

import java.io.FileNotFoundException;
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

   //Foi utilizada a biblioteca snowball para o tratamento dos lexemas
   //Pode ser feito o download no dominio http://snowball.tartarus.org/download.html
   // Repare que na saída da frase processada tem valores repetidos mas na listas de tokens não
    public static void main(String[] args) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        //Frase padrao do chatbot
        System.out.println("Sou a assistente virtual da Logitech, em que posso ajudar? ");


        //Exemplo de frase inserida pelo usuario, pode ser qualquer uma
        String texto = ("Qual mouse, : têm um bom custo-benefício beneficio custo qual? ª ");
        //texto = ler.nextLine();
        texto="Qual mouse, : têm um bom custo-benefício beneficio custo qual? ª ";
        while(sintaxe.analisar(texto) == null){
            System.out.println(sintaxe.analisar(texto));
            texto = ler.next();
        }
        try {
            sw.init();
            ai.povoar(sw);
            executar(texto);
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("Arquivo não encontrado.");
        }

    }

    public static void executar(String texto) {
        texto = texto.replaceAll("\\p{Punct}", " ");
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        StringBuilder sb = new StringBuilder();
        Scanner ler = new Scanner(texto);
        for (Scanner it = ler; it.hasNext(); ) {
            String word = it.next().toLowerCase();

            if (keyWord.analisar(word) != null && sw.analisar(word) == null) {
                    sb.append(keyWord.analisar(word)).append(" ");
                    token.analisar(keyWord.analisar(word));
            }else if(ts.analisar(lexemas.analisar(word))== null && ts.analisar(word)== null && sw.analisar(word) == null){
                    ts.addSymbolTable(lexemas.analisar(word));
                    sb.append(lexemas.analisar(word)).append(" ");
                token.analisar(lexemas.analisar(word));
            } else if (lexemas.analisar(word) != null && sw.analisar(word) == null) {
                sb.append(lexemas.analisar(word)).append(" ");
                lexemas.analisar(lexemas.analisar(word));
            }

        }
       // imprimir(sb);
    }

    public static void imprimir(StringBuilder sb){
        if(!sb.isEmpty()) {
            System.out.println("Frase processada: " + sb.toString());
            System.out.println("Tabela de simbolos: " + ts.table.toString());
            System.out.println("Tokens: " + token.tokens.toString());
        }
    }


}
