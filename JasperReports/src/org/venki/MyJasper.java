package org.venki;

import java.util.HashMap;
import net.sf.jasperreports.engine.*;

public class MyJasper {

	public static void main(String[] args) {

		try {
			System.out.println("Generating PDF...");
			JasperReport jasperReport = JasperCompileManager.compileReport("MYReport.jrxml");
			@SuppressWarnings("rawtypes")
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
					new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "HelloJasper.pdf");

			System.out.println("HelloJasper.pdf has been generated!");
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

}