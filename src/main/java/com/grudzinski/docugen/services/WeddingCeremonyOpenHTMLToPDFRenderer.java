package com.grudzinski.docugen.services;

import com.grudzinski.docugen.model.document.WeddingCeremony;
import com.openhtmltopdf.DOMBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
//@Primary
public class WeddingCeremonyOpenHTMLToPDFRenderer implements WeddingCeremonyRendererService {

    private TemplateEngine templateEngine;
    private SpringResourceTemplateResolver templateResolver;

    public WeddingCeremonyOpenHTMLToPDFRenderer(TemplateEngine templateEngine, SpringResourceTemplateResolver templateResolver) {
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
    public void generatePDF(WeddingCeremony weddingCeremony, OutputStream outputStream) throws Exception {
        Context context = new Context();
        context.setVariable("wedding", weddingCeremony);

        String html = templateEngine.process("document/wedding/view", context);

        org.jsoup.nodes.Document doc;

        doc = Jsoup.parse(html);

        org.w3c.dom.Document w3cdoc = DOMBuilder.jsoup2DOM(doc);

        PdfRendererBuilder builder = new PdfRendererBuilder();
//        builder.useFont(new File("fonts/Tinos-Regular.ttf"), "Times");
//        builder.useFont(new File("fonts/Arimo-Regular.ttf"), "Arimo");
//        builder.useFont(new File("fonts/Arimo-Bold.ttf"), "Arimo", 700, BaseRendererBuilder.FontStyle.OBLIQUE, true);
//        builder.useFont(new File("fonts/Arimo-Italic.ttf"), "Arimo-Italic", 400, BaseRendererBuilder.FontStyle.ITALIC, true);
////        builder.useFont(new File("fonts/Arimo-BoldItalic.ttf"), "Arimo-BoldItalic");
//        builder.useFont(new File("fonts/Tinos-Regular.ttf"), "Tinos");
//        builder.withUri("file:///Users/me/Documents/pdf/in.htm");

//        builder.withHtmlContent(html, "");
        builder.withW3cDocument(w3cdoc, "templates/document/wedding/view.html");

//        builder.withHtmlContent("<html><head><style type=\"text/css\">\n" +
//                "        body {\n" +
//                "            font-family: Arimo;\n" +
//                "        }\n" +
//                "    </style></head><body>zażółć gęślą jaźń</body></html>", "");
        builder.toStream(outputStream);
        builder.run();

//        ITextRenderer renderer = new ITextRenderer();

//        ITextFontResolver fontResolver = renderer.getFontResolver();

//        ClassPathResource regular = new ClassPathResource("fonts/Arimo-Regular.ttf");
//        fontResolver.addFont(regular.getURL().toString(), BaseFont.IDENTITY_H, true);

//        String xHtml = convertToXhtml(html);
//        renderer.setDocumentFromString(xHtml);
//        renderer.setDocumentFromString(html);
//        renderer.layout();
//        renderer.createPDF(outputStream);
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
