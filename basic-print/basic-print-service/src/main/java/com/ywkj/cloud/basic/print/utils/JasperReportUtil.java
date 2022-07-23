package com.ywkj.cloud.basic.print.utils;

import com.ywkj.cloud.basic.print.enums.ReportTypeEnum;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.HtmlExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Slf4j
public class JasperReportUtil {
    final static String jasperDir = "jaspers";

    private static String getContentType(ReportTypeEnum type) {
        String contentType;
        switch (type) {
            case HTML:
                contentType = "text/html;charset=utf-8";
                break;
            case PDF:
                contentType = "application/pdf";
                break;
            case XLS:
                contentType = "application/vnd.ms-excel";
                break;
            case XLSX:
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
            case XML:
                contentType = "text/xml";
                break;
            case RTF:
                contentType = "application/rtf";
                break;
            case CSV:
                contentType = "text/plain";
                break;
            case DOC:
                contentType = "application/msword";
                break;
            default:
                contentType = "text/html;charset=utf-8";
        }
        return contentType;
    }

    static JasperPrint getJasperPrint(InputStream jasperStream, Map parameters, List<?> list) throws JRException {
        JRDataSource dataSource = null;
        if (null == list || list.size() == 0) {
            dataSource = new JREmptyDataSource();
        } else {
            dataSource = new JRBeanCollectionDataSource(list);
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parameters, dataSource);
        return jasperPrint;
    }

    public static byte[] exportToPdf(String jasperPath, Map parameters, List<?> list) throws Exception {

        URL url;
        HttpURLConnection urlCon = null;
        try {
            url = new URL(jasperPath);
            urlCon = (HttpURLConnection) url.openConnection();
            InputStream jasperStream = urlCon.getInputStream();
            JasperPrint jasperPrint = getJasperPrint(jasperStream, parameters, list);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            log.error("读取报表异常", e);
        } finally {
            urlCon.disconnect();
        }

        return null;
    }


    public static void exportToXml(String jasperPath, Map parameters, List<?> list, HttpServletResponse response) throws Exception {
        OutputStream outputStream = response.getOutputStream();
        try {
            ClassPathResource resource = new ClassPathResource(jasperPath);
            response.setContentType(getContentType(ReportTypeEnum.XML));
            InputStream jasperStream = resource.getInputStream();
            JasperPrint jasperPrint = getJasperPrint(jasperStream, parameters, list);
            JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);
        } catch (Exception e) {
            log.error("读取报表异常", e);
            outputStream.write("读取报表异常".getBytes());
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }

    public static void exportToHtml(String jasperPath, Map parameters, List<?> list, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType(getContentType(ReportTypeEnum.HTML));
        PrintWriter out = response.getWriter();
        HtmlExporter exporter = new HtmlExporter();
        try {
            ClassPathResource resource = new ClassPathResource(jasperPath);
            InputStream jasperStream = resource.getInputStream();
            JasperPrint jasperPrint = getJasperPrint(jasperStream, parameters, list);


            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
            exporter.setConfiguration(configuration);

            HtmlExporterOutput outPut = new SimpleHtmlExporterOutput(out);
            exporter.setExporterOutput(outPut);

            exporter.exportReport();
        } catch (Exception e) {
            out.write("读取报表异常");
        } finally {
            out.flush();
            out.close();
        }
    }
}
