import java.util.HashMap;
import java.util.Map;
import java.io.*;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import org.chai.chwcf.utils.Utils


File excelFile = new File('import_script/fop_functions.xls');
Workbook w;

def outPut= new File('import_script/data.txt');
if(outPut.exists())
	outPut.delete();
PrintStream prt;
try {
	def functions = new ArrayList();
	//def serviceAreas = new ArrayList();
	prt = new PrintStream(outPut);
	prt.println('INSERT INTO `dhsst_enum_option` (`id`, `jsonDescriptions`, `jsonNames`, `value`, `enume`, `ordering`, `inactive`) VALUES ');
	if(excelFile){
		w= Workbook.getWorkbook(excelFile);
		Sheet sheet = w.getSheet(0);
		for (int j=0; j<sheet.getRows();j++){
			String function
			//String serviceArea
			for (int i=0;i<sheet.getColumns();i++){
				Cell cell = sheet.getCell(i,j);
				if(i==0)
					function = Utils.prepareStringForSQL(cell.getContents())
				//if(i==1)
					//serviceArea = Utils.prepareStringForSQL(cell.getContents())
			}
			if(!functions.contains(function))
				functions.add(function);
			//if(!serviceAreas.contains(serviceArea))
				//serviceAreas.add(serviceArea);
		}	
	}
	
	for(def function: functions)
		prt.println("(null, null, '{\"fr\":\""+function+"\",\"en\":\""+function+"\"}', '"+function+"', 129, '{\"en\":0,\"fr\":0}', b'0'),");
	//for(def serviceArea : serviceAreas)
		//prt.println("(null, null, '{\"fr\":\""+serviceArea+"\",\"en\":\""+serviceArea+"\"}', '"+serviceArea+"', 119, '{\"en\":0,\"fr\":0}', b'0'),");
	
	prt.close();
} catch(Exception e) {
	System.out.println("Write error");
}




