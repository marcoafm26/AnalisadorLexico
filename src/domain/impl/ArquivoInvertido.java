package domain.impl;

import java.io.File;
import java.io.FileNotFoundException;
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

    public void povoar (StopWords sw) throws FileNotFoundException {

        File arquivo = new File("arquivo.txt");
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
                        token.analisar(lexemas.analisar(keyWord.analisar(word)));
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
            gravarArq(arqInvertido);
    }
    private class Index{
        int index;
        int usage=0;

        private Index(int index,int usage){
            this.index = index;
            this.usage = usage;
        }

        @Override
        public String toString(){
            return "Indice :" + this.index + "\t" + "Usages: " + this.usage;
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

    public void gravarArq(Map<String,List<Index>> arqInvertido){
        arqInvertido.entrySet().forEach(key ->{
            System.out.println(key.getKey() + " = " + key.getValue().toString());
        });
    }
}
