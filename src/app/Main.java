package app;

import domain.impl.Sintaxe;
import domain.impl.StopWords;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner ler = new Scanner(System.in);
        System.out.println("Sou a assistente virtual da Logitech, em que posso ajudar? ");
        //String texto = ("Qual mouse tem um bom custo-benefício?");
        String texto = ("Qual mouse tem um bom custo-beffa1321898789a7d d///dasd ´´´´´´nefícioadasdasda?");
        Sintaxe sintaxe = new Sintaxe();
        StopWords sw = new StopWords();
        try {
            sw.init();
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("Arquivo não encontrado.");
        }
    }

}
