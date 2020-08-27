package com.example.demo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException, DocumentException, URISyntaxException {
        //lấy file
        Document document = new Document();
        File file = new File("Test.pdf");
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //get template file
        String content = getContent("default.txt");
        //get image
        Image img = getImage("default.png");
        //replace template
        String fillContent = content.replace("NAMEx",
                "Vé lẻ Thiên đường bảo sơm"
                        + "[Trẻ em]")
                .replace("PRICEx",
                        "100.000 VND")
                .replace("REDEMPTION_DATEx", dateFormat.format(new Date()))
                .replace("PLACEx", "Thiên đường Bảo sơn");

        //start stream file
        document.open();

        for (int i = 0; i < 10; i++) {
            //phần cần sửa
                PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
                //enter image
                document.add(img);
                //enter text
                Chunk chunk1 = new Chunk(fillContent, getFont());
                document.add(new Paragraph("\n"));
                document.add(chunk1);
                //gen barcode
                Barcode128 barcode128 = new Barcode128();
                barcode128.setCode("1992323");
                barcode128.setCodeType(Barcode128.CODE128);
                Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
                code128Image.setInterpolation(true);
                document.add(code128Image);
        }

        document.close();
    }

    public static String getContent(String contentFile) throws IOException {
        Path path = Paths.get("src/main/resources/ticketForm/"+contentFile);
        Charset charset = StandardCharsets.UTF_8;
        //đọc file
        return new String(Files.readAllBytes(path), charset);
    }

    public static Image getImage(String imageName) throws IOException, BadElementException, URISyntaxException {
        Path pathI = Paths.get(ClassLoader.getSystemResource("image/"+imageName).toURI());
        Image img = Image.getInstance(pathI.toAbsolutePath().toString());
        img.scaleAbsolute(200, 40);
        return  img;
    }

    public static Font getFont() throws IOException, DocumentException {
        Font font = new Font(BaseFont.createFont("src/main/resources/font/vuArial.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
        return font;
    }



}
