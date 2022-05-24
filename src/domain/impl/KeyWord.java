package domain.impl;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class KeyWord implements Analisar{

    public List<String> keywords;
    public List<Equipamento> equip;
    public List<Softwares> soft;
    public List<ComecoPergunta> comeco;
    public List<Problemas> problema;
    public List<Proposicoes> proposicoes;
    public KeyWord(){
        init();
    }
    @Override
    public String analisar(String texto) {
        if(keywords.contains(texto))
            return texto;
        else
            return null;
    }

    public void init(){
        LinkedList<KeyWords> temp= new LinkedList<>(Arrays.stream(KeyWords.values()).collect(toList()));
        keywords = new LinkedList<>();
        for (KeyWords key: temp) {
            keywords.add(key.name());
        }
        equip= new LinkedList<>(Arrays.stream(Equipamento.values()).collect(toList()));
        soft = new LinkedList<>(Arrays.stream(Softwares.values()).collect(toList()));
        comeco = new LinkedList<>(Arrays.stream(ComecoPergunta.values()).collect(toList()));
        problema = new LinkedList<>(Arrays.stream(Problemas.values()).collect(toList()));
        proposicoes = new LinkedList<>(Arrays.stream(Proposicoes.values()).collect(toList()));
    }

    public enum KeyWords{
        webcam,mouse,gamer,teclado,headset,fone,mousepad,drive,programa,como,quero,gostaria,poderia,qual,
        quanto,queria,ter,quebrar,ligar,desligar,queimar,executar,baixar,atualizar,fechar,abrir,problemas,
        funcionar,comprar,reembolsar,encontrar,trocar,garantia,suporte,barato,caro
    }
    public enum Equipamento{
        webcam,mouse,gamer,teclado,headset,fone,mousepad
    }

    public enum Softwares{
        drive,programa
    }

    public enum ComecoPergunta{
        como,quero,gostaria,poderia,qual,quanto,queria,ter
    }

    public enum Problemas{
        quebrar,ligar,desligar,queimar,executar,baixar,atualizar,fechar,abrir,problemas
    }

    public enum Proposicoes{
        funcionar,comprar,reembolsar,encontrar,trocar,garantia,suporte,barato,caro
    }
}
