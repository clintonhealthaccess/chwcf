import java.util.HashMap;
import java.util.Map;
import java.io.*;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import org.chai.chwcf.utils.Utils


File excelFile = new File('import_script/transactions.xls');
Workbook w;

def outPut= new File('import_script/tansactions.txt');
if(outPut.exists())
	outPut.delete();
PrintStream prt;
try {
	prt = new PrintStream(outPut);
	if(excelFile){
		w= Workbook.getWorkbook(excelFile);
		Sheet sheet = w.getSheet(0);

		for (int j=0; j<sheet.getRows();j++){
			int id
			String transDate
			int cooperative
			int category
			String description
			int amount
			String recordedDate

			for (int i=0;i<sheet.getColumns();i++){
				Cell cell = sheet.getCell(i,j);
				if(i==0)
					id = Integer.parseInt(cell.getContents())
				if(i==1)
					transDate = cell.getContents()
				if(i==2)
					cooperative = Utils.getCooperativeIdMap().get(Integer.parseInt(cell.getContents()))
				if(i==3)
					category = Integer.parseInt(cell.getContents())
				if(i==4)
					description = Utils.prepareStringForSQL(cell.getContents())
				if(i==5)
					amount = Integer.parseInt(cell.getContents())
				if(i==6)
					recordedDate  = cell.getContents()
			}

			prt.println('INSERT INTO  `chwcf`.`chwcf_transaction` VALUES (null,'+amount+',TRUE,"'+recordedDate+'","'+description+'",1,"'+recordedDate+'","'+transDate+'",1,'+category+','+cooperative+');');
		}	
	}
	prt.close();
} catch(Exception e) {
	System.out.println("Write error");
}




