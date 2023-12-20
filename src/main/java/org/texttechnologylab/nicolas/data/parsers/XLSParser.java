package org.texttechnologylab.nicolas.data.parsers;

import jxl.read.biff.BiffException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class containing all necessary to get the data from the Abstimmungen
 * @author Nicolas Calderon
 */
public class XLSParser {

    //TODO make basic differentiation system based on input stream
    public static void AbstimmungFromExcelParser() throws IOException {

    }

    /**
     * Retrieves the information from the Excel documents of type ".xlsx"
     * @return
     * @throws IOException
     */
    public static Map<Integer, List<String>> EndsWithXLSX(String path) throws IOException{

        FileInputStream file = new FileInputStream(new File(path));

        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;

        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {data.get(i).add(cell.getNumericCellValue() + "");}
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        data.get(i).add(cell.getCellFormula() + "");
                        break;
                    default:
                        data.get(i).add(" ");}
            }
            i++;
        }
        workbook.close();
        return data;
    }

    /**
     * Retrieves the information from the Excel documents that end with ".xsl" this was needed due to the
     * compatibility problems when using POI
     * @param path
     * @throws IOException
     * @throws BiffException
     */
    public static Map<Integer, List<String>> EndsWithXSL(File path) throws IOException, BiffException {

        jxl.Workbook workbook = jxl.Workbook.getWorkbook(path);
        jxl.Sheet sheet = workbook.getSheet(0);

        Map<Integer, List<String>> data = new HashMap<>(0);

        // Iterate through the rows and columns to read data
        int numRows = sheet.getRows();
        int numCols = sheet.getColumns();

        // Ignores the first row because is just the Names of the fields
        for (int i = 1; i < numRows; i++) {

            List<String> rData = new ArrayList<>(0);

            for (int j = 0; j < numCols; j++) {

                jxl.Cell cell = sheet.getCell(j, i);
                rData.add(cell.getContents());
            }

            data.put(i,rData);
        }
        // Close the workbook to release resources
        workbook.close();
        return data;
    }
}

