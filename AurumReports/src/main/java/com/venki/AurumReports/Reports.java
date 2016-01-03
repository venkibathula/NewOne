package com.venki.AurumReports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class Reports {

	@SuppressWarnings("unchecked")
	public static void main(String[] rags)
			throws JRException, NamingException, SQLException, IOException, ClassNotFoundException {

		JasperDesign design = JRXmlLoader.load("C:/Users/Swapna/Desktop/New folder (2)/catalog.xml");

		JasperReport report = JasperCompileManager.compileReport(design);
		@SuppressWarnings("rawtypes")
		Map parameters = new HashMap();
		parameters.put("ReportTitle", "Excel JasperReport");

		@SuppressWarnings("unused")
		InitialContext initialContext = new InitialContext();

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/comm", "root", "root");
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = st.executeQuery("Select * from comm.sms_data");

		JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);
		OutputStream ouputStream = new FileOutputStream(new File("c:/JasperReports/catalog.xls"));
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		JRXlsExporter exporterXLS = new JRXlsExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);

		exporterXLS.exportReport();
		ouputStream.write(byteArrayOutputStream.toByteArray());
		ouputStream.flush();
		ouputStream.close();
		
		
		
		
	}
}
