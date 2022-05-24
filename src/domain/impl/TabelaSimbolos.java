package domain.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TabelaSimbolos implements Analisar{

    public List<String> table;

    public TabelaSimbolos(){
        table = new LinkedList<>();
    }
    @Override
    public String analisar(String texto) {
        if(table.contains(texto))
            return texto;
        return null;
    }

    public void addSymbolTable(String word){
        if (word != null) {
            table.add(word);
        }
    }
}
