package com.venki;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class Jasper {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/comm", "root", "root");
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery("Select * from comm.sms_data");

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("AurumInfo");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Id");
			rowhead.createCell((short) 1).setCellValue("Message");
			rowhead.createCell((short) 2).setCellValue("Status");
			rowhead.createCell((short) 3).setCellValue("MessageId");
			int i = 1;

			while (rs.next()) {
				HSSFRow row = sheet.createRow((short) i);
				
				row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("id")));
				row.createCell((short) 1).setCellValue(rs.getString("message"));
				row.createCell((short) 2).setCellValue(rs.getString("status"));
				row.createCell((short) 3).setCellValue(rs.getString("messageId"));
				i++;
			}

			System.out.println(System.getProperty("user.name"));
			/*
			 * File file = new File("C:\\Downloads\\AurumInfo_Reports.xls");
			 * file.getParentFile().mkdir(); file.createNewFile();
			 */

			String yemi = "C:\\Users\\Swapna\\Desktop\\AurumInfo_Reports.xls";
			FileOutputStream fileOut;

			fileOut = new FileOutputStream(yemi);
			workbook.write(fileOut);
			System.out.println(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}