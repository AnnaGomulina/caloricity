package ru.caloricity.probe;

import com.itextpdf.html2pdf.HtmlConverter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

public class Protocol {
    private final Probe probe;
    private final TemplateEngine templateEngine;

    public Protocol(Probe probe, TemplateEngine templateEngine) {
        this.probe = probe;
        this.templateEngine = templateEngine;
    }

    public InputStream create() throws IOException {
        Context context = new Context();
        context.setVariable("probe", probe);
        String htmlContent = templateEngine.process("protocol.html", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    public String filename() {
        return "protocol_" + probe.getCode() + "_" + probe.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".pdf";
    }
}
