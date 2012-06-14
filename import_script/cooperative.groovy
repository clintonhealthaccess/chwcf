import java.util.HashMap;
import java.util.Map;
import java.io.*;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import org.chai.chwcf.utils.Utils


File excelFile = new File('import_script/cooperatives.xls');
Workbook w;

def outPut= new File('import_script/cooperatives.txt');
if(outPut.exists())
	outPut.delete();
PrintStream prt;
try {
	prt = new PrintStream(outPut);
	if(excelFile){
		w= Workbook.getWorkbook(excelFile);
		Sheet sheet = w.getSheet(0);

		for (int j=0; j<sheet.getRows();j++){
			int orgUnitId
			String orgUnitName
			for (int i=0;i<sheet.getColumns();i++){
				Cell cell = sheet.getCell(i,j);
				if(i==0)
					orgUnitId = Integer.parseInt(cell.getContents())
				if(i==1)
					orgUnitName = Utils.prepareStringForSQL(cell.getContents())
			}

			prt.println('INSERT INTO  `chwcf`.`chwcf_cooperative` VALUES (NULL,"2000-01-01","'+orgUnitName+'","'+orgUnitName+'",NULL,'+orgUnitId+',3);');
			
		}
	}
	prt.close();
} catch(Exception e) {
	System.out.println("Write error");
}




