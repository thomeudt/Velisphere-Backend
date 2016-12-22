import java.util.ArrayList;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_FindActionsForCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT ACTIONID FROM ACTION WHERE CHECKID = ? ORDER BY ACTIONID;"
			);

	public VoltTable[] run(
			String checkpathID,
			String checkID)
					throws VoltAbortException {
		voltQueueSQL( sql , checkID);
		
		return voltExecuteSQL();
	}
}




