package domain.impl;
import org.tartarus.snowball.ext.portugueseStemmer;

public class Lexemas implements Analisar{

    @Override
    public String analisar(String texto) {
        portugueseStemmer stemmer = new portugueseStemmer();
        stemmer.setCurrent(texto);
        if (stemmer.stem()){
            return stemmer.getCurrent();
        }else
            return null;
    }
}
