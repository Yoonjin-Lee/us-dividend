package com.example.usdividend.function

import android.content.Context
import com.example.usdividend.screen.DividendHistoryList
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream


// dataList = DividendHistoryList

fun excel(
    context : Context
){
    val workbook = XSSFWorkbook()

    val sheet = workbook.createSheet("My Dividend")

    val headerRow = sheet.createRow(0)
    headerRow.createCell(0, CellType.NUMERIC).setCellValue("Month")
    headerRow.createCell(1, CellType.STRING).setCellValue("Company")
    headerRow.createCell(2, CellType.NUMERIC).setCellValue("Price")

    var rowIndex = 1
    for (myData in DividendHistoryList) {
        val dataRow = sheet.createRow(rowIndex++)
        dataRow.createCell(0).setCellValue(myData.month)
        dataRow.createCell(1).setCellValue(myData.company)
        dataRow.createCell(2).setCellValue(myData.price)
    }

    val file = File(context.getExternalFilesDir(null), "my_dividend.xlsx")
    val outputStream = FileOutputStream(file)
    workbook.write(outputStream)
    outputStream.close()
}