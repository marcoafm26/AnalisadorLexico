package domain.impl;

import java.util.LinkedList;
import java.util.List;

public class Token implements Analisar{
    public List<String> tokens;

    public Token(){
        tokens = new LinkedList<>();
    }
    @Override
    public String analisar(String texto) {
        if(!tokens.contains(texto))
            tokens.add(texto);
        return null;
    }
}
