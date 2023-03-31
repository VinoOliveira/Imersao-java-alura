package stickers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class StickerMaker {
    String path = "save";
    File filePath = new File(path);

    private void criarPasta() {
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public void criar(InputStream url, String nomeArquivo, String textoFigurinha) {
        try {

            // receber a imagem
            BufferedImage imagemOriginal = ImageIO.read(url);

            // criar uma nova imagem transparente
            int largura = imagemOriginal.getWidth();
            int altura = imagemOriginal.getHeight();
            int novaAltura = altura + 200;
            BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

            // copiar a imagem para a nova imagem
            Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
            graphics.drawImage(imagemOriginal, 0, 0, null);

            // editar fonte
            var fonte = new Font("impact", Font.BOLD, 64);
            graphics.setColor(Color.YELLOW);
            graphics.setFont(fonte);

            // centralizar a o texto
            String texto = textoFigurinha;
            FontMetrics fontMetrics = graphics.getFontMetrics();
            Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
            int larguraTexto = (int) retangulo.getWidth();
            int posicaoTextoX = (largura - larguraTexto) / 2;
            int posicaoTextoY = novaAltura - 100;

            graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

            FontRenderContext fontRenderContext = graphics.getFontRenderContext();
            TextLayout textLayout = new TextLayout(texto, fonte, fontRenderContext);

            Shape outline = textLayout.getOutline(null);
            AffineTransform transform = graphics.getTransform();
            transform.translate(posicaoTextoX, posicaoTextoY);
            graphics.setTransform(transform);

            var outlineStroke = new BasicStroke(largura * 0.004f);
            graphics.setStroke(outlineStroke);

            graphics.setColor(Color.BLACK);
            graphics.draw(outline);
            graphics.setClip(outline);

            // salvar a nova imagem
            criarPasta();
            ImageIO.write(novaImagem, "png", new File(path + "/" + nomeArquivo + ".png"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
