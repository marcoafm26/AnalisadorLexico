package domain.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class StopWords implements Analisar {



    private List<String> stopWords;

    public StopWords(){
        stopWords = new LinkedList<>();
    }
    @Override
    public String analisar(String texto) {
        if(stopWords.contains(texto)) {
            return texto;
        }
       return null;
    }


    public void init() throws FileNotFoundException {
        File arquivo = new File("stopwords.txt");
        Scanner ler = new Scanner(arquivo);
        for (Scanner it = ler; it.hasNext(); ) {
            String word = it.next();
            stopWords.add(word.toLowerCase());
        }
    }


    public void setStopWords(List<String> stopWords){
        this.stopWords = stopWords;
    }

    public List<String> getStopWords() {
        return stopWords;
    }

}
