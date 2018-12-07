package helpers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ApplicationSystem.EntityEthicsApplicationPK;
import models.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Printer {

    public Printer() {
    }

    public void createPDF(EntityEthicsApplicationPK applicationPK) throws DocumentException, FileNotFoundException {

        // Check saving directory exists
        String docDirectory = App.getInstance().getTempDirectory();

        // Create temporary directory
        File dir = new File(docDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Create base document
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(applicationPK.shortName() + ".pdf"));

        // Open document for writing
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();
    }
}