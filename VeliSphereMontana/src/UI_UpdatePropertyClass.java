import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;

public class UI_UpdatePropertyClass extends VoltProcedure {

  public final SQLStmt updatePC = new SQLStmt(
      " UPDATE PROPERTYCLASS SET PROPERTYCLASSNAME = ?, PROPERTYCLASSDATATYPE = ?, PROPERTYCLASSUNIT = ? WHERE PROPERTYCLASSID = ?;" );

  public VoltTable[] run(
		  String propertyClassID,
		  String propertyClassName,
		  String propertyClassDataType,
		  String propertyClassUnit)
      throws VoltAbortException {

          voltQueueSQL( updatePC, propertyClassName, propertyClassDataType, propertyClassUnit, propertyClassID );
          return voltExecuteSQL();

      }
}