import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_FindRulesForMultiCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT RULEID FROM RULE WHERE MULTICHECKID = ? ORDER BY RULEID;"
			);

	public VoltTable[] run(
			String checkpathID,
			String checkID)
					throws VoltAbortException {
		voltQueueSQL( sql , checkID);
		
		return voltExecuteSQL();
	}
}




