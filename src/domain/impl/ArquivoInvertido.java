package domain.impl;

import java.io.*;
import java.text.Normalizer;
import java.util.*;


public class ArquivoInvertido {
    static Lexemas lexemas = new Lexemas();
    static TabelaSimbolos ts = new TabelaSimbolos();
    static KeyWord keyWord = new KeyWord();
    static Token token = new Token();

    Map<String,List<Index>> arqInvertido;

    public ArquivoInvertido(){
        arqInvertido = new HashMap<>();
    }

    public void povoar (StopWords sw) throws IOException {

        File arquivo = new File("respostas.txt");
        Scanner inArchive = new Scanner(arquivo);
        Map<Integer,String> resposta = new HashMap<>();
        int position = 0;
        for (Scanner temp=inArchive; temp.hasNext() ;) {

            String texto = temp.nextLine();
            resposta.put(position, texto);
            String[] words = textFormat(texto);

            for (String word : words) {
                if (sw.analisar(word) == null) {
                    if (keyWord.analisar(word) != null && sw.analisar(word) == null) {
                        token.analisar(lexemas.analisar(keyWord.analisar(word))); // tirar lexemas se nao quiser que aplique a funcao nas keywords
                    } else if (ts.analisar(lexemas.analisar(word)) == null && ts.analisar(word) == null && sw.analisar(word) == null) {
                        token.analisar(lexemas.analisar(word));
                    }

               // System.out.println(token.tokens.toString());
                for (String key : token.tokens) {
                    addKey(key, position);
                }
                token.tokens.clear();
            }
            }
            position++;
        }
                dataWriter();
                dataReader();
                imprimir(arqInvertido);
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

    public void imprimir(Map<String,List<Index>> arqInvertido){
        arqInvertido.entrySet().forEach(key ->{
            System.out.println(key.getKey() + " = " + key.getValue().toString());
        });
    }

    public void dataReader()throws IOException{
        arqInvertido.clear();

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
}
