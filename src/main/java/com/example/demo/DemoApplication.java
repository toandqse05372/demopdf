package com.example.demo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        Path path = Paths.get("C:\\Users\\quang\\OneDrive\\Documents\\My Games\\test.txt");
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(path), charset);
        content = content.replaceAll("bar", "fooo");
        System.out.println(content);
        Files.write(path, content.getBytes(charset));

//        PDDocument document = new PDDocument();
//        PDPage page = new PDPage();
//        document.addPage(page);
//
//        PDPageContentStream contentStream = new PDPageContentStream(document, page);
//
//        contentStream.setFont(PDType1Font.COURIER, 12);
//        contentStream.beginText();
//        String content1 = content.replaceAll("\n","<br/>");
//        contentStream.showText(content1);
//        contentStream.endText();
//        contentStream.close();
//
//        document.save("pdfBoxHelloWorld1.pdf");
//        document.close();

        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));

        document.open();
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

        Path pathI = Paths.get(ClassLoader.getSystemResource("images.jpg").toURI());
        Image img = Image.getInstance(pathI.toAbsolutePath().toString());
        img.scaleAbsolute(200, 40);
        document.add(img);

        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk1 = new Chunk(content, font);
        document.add(new Paragraph("\n"));
        document.add(chunk1);

        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode("hsdasjashfdkfjdssggdgd");
        barcode128.setCodeType(Barcode128.CODE128);
        barcode128.setFont(null);
        Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
        code128Image.setInterpolation(true);
//        code128Image.scaleAbsolute(100, 100);
//        code128Image.setRotationDegrees((float) -45);

//        code128Image.scalePercent(100);
//        code128Image.setRotation(45);
        document.add(code128Image);

//        BarcodeEAN barcodeEAN = new BarcodeEAN();
//        barcodeEAN.setCodeType(BarcodeEAN.EAN13);
//        barcodeEAN.setCode("1234523453323");
//        Image codeEANImage = barcodeEAN.createImageWithBarcode(pdfContentByte, null, null);
//        codeEANImage.setAbsolutePosition(20, 600);
//        codeEANImage.scalePercent(100);
//        document.add(codeEANImage);
//
//        BarcodeQRCode barcodeQrcode = new BarcodeQRCode("examples.javacodegeeks.com/author/chandan-singh", 1, 1, null);
//        Image qrcodeImage = barcodeQrcode.getImage();
//        qrcodeImage.setAbsolutePosition(20, 500);
//        qrcodeImage.scalePercent(100);
//        document.add(qrcodeImage);

        document.close();
    }



}
