package ficus.suitcase.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * 将html|text转化为PDF格式工具类
 * 2018/10/12.
 * @author DamonFicus
 * 另外一种方式：wkhtmltopdf 第三方客户端实现
 * http://wkhtmltopdf.org/
 * https://github.com/wkhtmltopdf/wkhtmltopdf
 */
public class TextToPdf {
    private static final Logger logger = LoggerFactory.getLogger(TextToPdf.class);
    private static final String FONT = "C:\\Windows\\Fonts\\simhei.ttf";
    /**
     * 根据HTML页面生成PDF文件
     * @param htmlFilePath  html全路径(包括文件名)
     * @param pdfFilePath   pdf 保存全路径(包括文件名)
     * @throws IOException
     * @throws DocumentException
     */
    public static void html2Pdf(String htmlFilePath,String pdfFilePath) throws IOException, DocumentException {
        logger.info("start createPdf from [] to []",htmlFilePath,pdfFilePath);
        Boolean createStatus=false;
        Document document = new Document();
        PdfWriter writer = null;
        writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,new FileInputStream(htmlFilePath));
        document.close();
        logger.info("create pdf success");
    }


    /**
     * 根据文本生成PDF文件
     * @param text  路径加文件名全称
     * @param pdf   路径加文件名全称
     * @throws DocumentException
     * @throws IOException
     */
    public static void text2Pdf(String text, String pdf) throws DocumentException, IOException {
        logger.info("start createPdf from [] to []",text,pdf);
        Document document = new Document();
        OutputStream os = new FileOutputStream(new File(pdf));
        PdfWriter.getInstance(document, os);
        document.open();
        //方法一：使用Windows系统字体(TrueType)或者将字体文件放到代码的相对路径下
        BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(baseFont);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(text)), "GBK");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            document.add(new Paragraph(str, font));
        }
        document.close();
    }


    public static void main(String[] args) throws IOException, DocumentException {
        String htmlFilePath = "E:\\draft\\PDF\\index.html";
        String pdfFilePath  = "E:\\draft\\PDF\\html2pdf.pdf";
        String textFilePath = "E:\\draft\\PDF\\document.txt";
        String pdfFilePath2 = "E:\\draft\\PDF\\text2pdf.pdf";
        TextToPdf.html2Pdf(htmlFilePath,pdfFilePath);
        TextToPdf.text2Pdf(textFilePath,pdfFilePath2);
    }

}
