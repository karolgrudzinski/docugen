package com.grudzinski.docugen.services;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.lowagie.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
@Primary
public class WeddingCeremonyFlyingSaucerRenderer implements WeddingCeremonyRendererService {

    private TemplateEngine templateEngine;
    private SpringResourceTemplateResolver templateResolver;

    public WeddingCeremonyFlyingSaucerRenderer(TemplateEngine templateEngine, SpringResourceTemplateResolver templateResolver) {
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

//        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        this.templateEngine = templateEngine;
        this.templateResolver = templateResolver;
    }

    @Override
    public void generatePDF(WeddingCeremony weddingCeremony, OutputStream outputStream) throws IOException {
        Context context = new Context();
        context.setVariable("wedding", weddingCeremony);

        String html = templateEngine.process("document/wedding/pdf", context);

        ITextRenderer renderer = new ITextRenderer();

        ITextFontResolver fontResolver = renderer.getFontResolver();



//        ClassPathResource regular = new ClassPathResource("fonts/Arimo-Regular.ttf");
//        fontResolver.addFont(regular.getURL().toString(), BaseFont.IDENTITY_H, true);

        List<String> fontNames = List.of("Arimo", "Tinos", "Caladea", "Carlito");
        List<String> fontVariants = List.of("Regular", "Italic", "Bold", "BoldItalic");
        fontNames.forEach(fontName -> fontVariants.forEach(fontVariant -> {
            try {
                fontResolver.addFont("static/fonts/" + fontName + "-" + fontVariant + ".ttf", BaseFont.IDENTITY_H, true);
//                log.debug("static/fonts/" + fontName + "-" + fontVariant + ".ttf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
//        fontResolver.addFont("fonts/Arimo-Regular.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Arimo-Italic.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Arimo-Bold.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Arimo-BoldItalic.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Tinos-Regular.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Tinos-Italic.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Tinos-Bold.ttf", BaseFont.IDENTITY_H, true);
//        fontResolver.addFont("fonts/Tinos-BoldItalic.ttf", BaseFont.IDENTITY_H, true);

        String xHtml = convertToXhtml(html);
//        renderer.setDocumentFromString(xHtml,"static/");
        renderer.setDocumentFromString(xHtml,"templates/document/wedding/pdf.html");
//        renderer.setDocumentFromString(html, "templates/document/wedding/pdf.html");

        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(String.valueOf(UTF_8));
        tidy.setOutputEncoding(String.valueOf(UTF_8));
        tidy.setDocType("HTML");
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }
}
