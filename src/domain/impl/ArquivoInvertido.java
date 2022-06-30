package domain.impl;

import java.io.*;
import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.*;


public class ArquivoInvertido {
    static Lexemas lexemas = new Lexemas();
    static TabelaSimbolos ts = new TabelaSimbolos();
    static KeyWord keyWord = new KeyWord();
    static Token token = new Token();
    static StopWords sw = new StopWords();

    Map<String,List<Index>> arqInvertido;
    Map<Integer,String> respostas = new HashMap<>();

    public ArquivoInvertido(){
        arqInvertido = new HashMap<>();
    }
    private class Index implements Comparable<Index>{
        int index;
        int usage=0;

        private Index(int index,int usage){
            this.index = index;
            this.usage = usage;
        }

        @Override
        public String toString(){
            return "Index:" + " "+ this.index + " Usages: "+ this.usage;
        }

        @Override
        public int compareTo(Index o) {
            if(this.index > o.index)
                return -1;
            else if(this.index < o.index)
                return 1;
            return 0;
        }
    }

    public void povoar () throws IOException {
        sw.init();
        File arquivo = new File("respostas.txt");
        Scanner inArchive = new Scanner(arquivo);

        int position = 0;
        for (Scanner temp=inArchive; temp.hasNext() ;) {

            String texto = temp.nextLine();
            fillResp("respostas.txt");
            String[] words = textFormat(texto);

            for (String word : words) {
                if (sw.analisar(word) == null) {
                    if (keyWord.analisar(word) != null && sw.analisar(word) == null) {
                        token.analisar(lexemas.analisar(keyWord.analisar(word))); // tirar lexemas se nao quiser que aplique a funcao nas keywords
                    } else if (ts.analisar(lexemas.analisar(word)) == null && ts.analisar(word) == null && sw.analisar(word) == null) {
                        token.analisar(lexemas.analisar(word));
                    }

                for (String key : token.tokens) {
                    addKey(key, position);
                }
                token.tokens.clear();
                }
            }
            position++;
        }
                dataWriter();
               // imprimir(arqInvertido);
    }
    public String tfidf(List<String> tokens){
        List<String> localTokens = new LinkedList<>(tokens);
        List<Integer> usages = new LinkedList<>(Collections.nCopies(respostas.size(),0));
        for (String token: localTokens) {
            if(arqInvertido.get(token) != null) {
                for (Index ind : arqInvertido.get(token)) {
                    usages.set(ind.index, usages.get(ind.index) + ind.usage);
                }
            }
        }
        int position = 0;
        for (int i = 0; i < usages.size()-1; i++) {
            if (usages.get(position) < usages.get(i+1))
                position = i+1;
            else if(usages.get(position) == usages.get(i+1)){
                Random random = new Random();
                int ran = random.nextInt(0,2);
                if (ran != 1)
                  position = i+1;
            }
        }
        int i = 0;
        return respostas.get(position);
    }


    public void addKey(String word,int index){
        if (arqInvertido.containsKey(word)){
            for (Index key: arqInvertido.get(word)) {
                    if(key.index == index) {
                        key.usage++;
                        return;
                    }
            }
            arqInvertido.get(word).add(new Index(index,1));

        }else{
            Index key = new Index(index,1);
            List<Index> list = new LinkedList<>();
            list.add(key);
            arqInvertido.put(word,list);
        }
    }
    public String[] textFormat(String texto){
        texto = texto.toLowerCase();
        texto = texto.replaceAll("\\p{Punct}", "");
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        String[] words = texto.trim().split("[,.!?'@_] *| +");
        return words;
    }
    public void fillResp(String path) throws FileNotFoundException {
        File resposta = new File("respostas.txt");
        Scanner inArchive = new Scanner(resposta);
        int position = 0;
        for (Scanner temp=inArchive; temp.hasNext() ;) {
            respostas.put(position,temp.nextLine());
            position++;
        }

    }
    public void dataReader()throws IOException{
        arqInvertido.clear();
        fillResp("respostas.txt");
        File arquivo = new File("arquivo.txt");
        Scanner inArchive = new Scanner(arquivo);


        for (Scanner temp=inArchive; temp.hasNext() ;) {

            String key = temp.next();
            String texto = temp.nextLine();
            texto = texto.replaceAll("[a-zA-Z,:\\]\\[]","");
            String[] words = texto.trim().split("[,.!?'@_] *| +");
            List<Index> list = new LinkedList<>();

            for (int i = 0; i < words.length; i=i+2) {
                list.add(new Index(Integer.parseInt(words[i]),Integer.parseInt(words[i+1])));
            }
            arqInvertido.put(key,list);
        }
    }
    public void dataWriter() throws IOException {
        FileWriter fw = new FileWriter("arquivo.txt");
        PrintWriter printWriter = new PrintWriter(fw);
        arqInvertido.entrySet().forEach(key->{
            key.getValue().stream().sorted();
            printWriter.println(key.getKey() +" "+key.getValue().toString());
        });
        printWriter.close();
    }
    public void imprimir(Map<String,List<Index>> arqInvertido){
        arqInvertido.entrySet().forEach(key ->{
            System.out.println(key.getKey() + " = " + key.getValue().toString());
        });
    }
}
