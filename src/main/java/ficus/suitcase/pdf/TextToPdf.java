package ficus.suitcase.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by 01333346 on 2018/10/12.
 * @author DamonFicus
 */
public class TextToPdf {
    private static final Logger logger = LoggerFactory.getLogger(TextToPdf.class);

    public Boolean htmlToPDF(String htmlFilePath,String pdfFilePath) throws IOException, DocumentException {
        logger.info("start createPdf from [] to []",htmlFilePath,pdfFilePath);
        Boolean createStatus=false;
        Document document = new Document();
        PdfWriter writer = null;
        writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,new FileInputStream(htmlFilePath));
        document.close();
        logger.info("create pdf success");
        return createStatus;
    }


    public static void main(String[] args) throws IOException, DocumentException {
        String htmlFilePath = "E:\\draft\\PDF\\index.html";
        String pdfFilePath = "E:\\draft\\PDF\\htmlToPDF.pdf";
        TextToPdf textToPdf = new TextToPdf();
        textToPdf.htmlToPDF(htmlFilePath,pdfFilePath);
    }

}
