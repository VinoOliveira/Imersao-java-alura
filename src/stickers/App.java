package stickers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        API api = API.NASA_APOD;

        String url = api.getUrl();
        ExtratorConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        List<Conteudo> conteudos = extrator.extrairConteudos(json);

        for (int i = 0; i < conteudos.size(); i++) {

            Conteudo conteudo = conteudos.get(i);
            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            System.out.println(conteudo.titulo());
            String texto = conteudo.titulo();

            String nomeArquivo = conteudo.titulo();
            var stickerMaker = new StickerMaker();
            stickerMaker.criar(inputStream, nomeArquivo, texto);
        }

    }

}
