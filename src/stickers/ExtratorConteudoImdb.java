package stickers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorConteudoImdb implements ExtratorConteudo {

    public List<Conteudo> extrairConteudos(String json) {
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parser(json);

        return listaDeAtributos.stream()
                .map(atributos -> new Conteudo(atributos.get("title"), atributos.get("image").replaceAll("(@+)(.*).jpg$", "$1.jpg")))
                .toList();

    }

}
