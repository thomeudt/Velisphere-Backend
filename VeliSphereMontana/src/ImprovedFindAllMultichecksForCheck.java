import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class ImprovedFindAllMultichecksForCheck extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT MULTICHECKID FROM MULTICHECK_CHECK_LINK WHERE CHECKID = ? ORDER BY MULTICHECKID;"
			);
	
	public final SQLStmt sqlWipeMultiCheckState = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = 0 WHERE MULTICHECKID = ?" 
			);
	

	public VoltTable[] run(
			String checkID)
					throws VoltAbortException {
		
		voltQueueSQL( sql , checkID);

		VoltTable[] findMulticheckResults = voltExecuteSQL();
		
		VoltTable findMulticheckResult = findMulticheckResults[0]; 
		
			while (findMulticheckResult.advanceRow()) {
				voltQueueSQL( sqlWipeMultiCheckState , findMulticheckResult.getString("MULTICHECKID"));
			}
		
		return findMulticheckResults;
	}
}




