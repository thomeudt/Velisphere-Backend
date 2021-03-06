import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;


public class BLE_MultiCheckParentForMultiCheck extends VoltProcedure {

	public final SQLStmt sqlFindLinkedMultiChecks = new SQLStmt(
			"SELECT MULTICHECKLID FROM MULTICHECK_MULTICHECK_LINK WHERE MULTICHECKRID = ? ORDER BY MULTICHECKLID;"
			);
			
	public VoltTable[] run( 
			String checkpathID,
			String multiCheckID
			)
					throws VoltAbortException {
		
		
		// find linked multichecks
		
		voltQueueSQL( sqlFindLinkedMultiChecks, multiCheckID);
		
		
		return voltExecuteSQL();
	}
}


