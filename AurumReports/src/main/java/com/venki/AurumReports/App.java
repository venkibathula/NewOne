package com.venki.AurumReports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hsqldb.Database;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class App {

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static void main(String[] args) throws ClassNotFoundException, SQLException, JRException {

		JasperPrint jasperPrint = null;

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/comm", "root", "root");
		Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = st.executeQuery("Select * from comm.sms_data");
		List<String> data = new ArrayList<String>();
		String d = "";
		String v = "";
		while (rs.next()) {
			d = rs.getString("message");
			d = rs.getString("status");
			data.add(d);
		}

		// First, load JasperDesign from XML and compile it into JasperReport
		JasperDesign jd  = JRXmlLoader.load(getClass().getResource("/reports/teacherPay.jrxml"));
		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
		// Second, create a map of parameters to pass to the report.
		Map parameters = new HashMap();
		parameters.put("ReportTitle", "Basic JasperReport");
		parameters.put("MaxSalary", new Double(25000.00));
		// Third, get a database connection

		// Fourth, create JasperPrint using fillReport() method
		JasperPrint jp = JasperManager.fillReport(jasperReport, parameters, conn);
		// You can use JasperPrint to create PDF
		JasperManager.printReportToPdfFile(jasperPrint, "BasicReport.pdf");
		// Or to view report in the JasperViewer
		JasperViewer.viewReport(jasperPrint);

	}
}