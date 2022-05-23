package domain.impl;

public class Sintaxe implements Analisar {

    public String verificaAlfabeto(String texto){
//        Pattern caracteres = Pattern.compile ("[\\w\\d*.+():_=-]+");
//        Matcher pesquisa = caracteres.matcher(texto);
        for (char caracter:texto.toCharArray()) {
            int valTable = (int)caracter;
            if(!(valTable == 32||(valTable >= 33 && valTable <= 126) || valTable == 128 || valTable == 128 || valTable == 130|| valTable == 131
                    || valTable == 133 || valTable == 135 || valTable == 136 || valTable == 144 || valTable == 147
                    || valTable == 160 || valTable == 162 || valTable == 163 || valTable == 166 || valTable == 167
                    || valTable == 180|| valTable == 181 || valTable == 182 || valTable == 183 || valTable == 189 || valTable == 196
                    || valTable == 198 || valTable == 199 || valTable == 205 || valTable == 210 || valTable == 214
                    || valTable == 224 || valTable == 226 || valTable == 229 || valTable == 233 || valTable == 237 || valTable == 242
                    || valTable == 246 || valTable == 248 || valTable == 250 || valTable == 251 || valTable == 252
                    || valTable == 253)  )
                return "Frase incorreta, verifique a sintaxe.";
        }
        return null;

    }

    public String identificaErro(int nErro){

        return null;
    }

    @Override
    public String analisar(String texto) {
        //        Pattern caracteres = Pattern.compile ("[\\w\\d*.+():_=-]+");
//        Matcher pesquisa = caracteres.matcher(texto);
        for (char caracter:texto.toCharArray()) {
            int valTable = (int)caracter;
            System.out.println(valTable + "Caracter: "+ caracter);

            if(!(valTable == 32||(valTable >= 33 && valTable <= 126) || valTable == 128 || valTable == 128 || valTable == 130|| valTable == 131
                    || valTable == 133 || valTable == 135 || valTable == 136 || valTable == 144 || valTable == 147
                    || valTable == 160 || valTable == 162 || valTable == 163 || valTable == 166 || valTable == 167
                    || valTable == 180|| valTable == 181 || valTable == 182 || valTable == 183 || valTable == 189 || valTable == 196
                    || valTable == 198 || valTable == 199 || valTable == 205 || valTable == 210 || valTable == 214
                    || valTable == 224 || valTable == 226 || valTable == 229 || valTable == 233 || valTable == 237 || valTable == 242
                    || valTable == 246 || valTable == 248 || valTable == 250 || valTable == 251 || valTable == 252
                    || valTable == 253)  )
                return "Frase incorreta, verifique a sintaxe.";
        }
        return "";
    }
}
