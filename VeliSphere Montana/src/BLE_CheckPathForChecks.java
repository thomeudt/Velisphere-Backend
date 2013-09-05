import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_CheckPathForChecks extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT CHECKPATHID, CHECKID FROM CHECKPATH_CHECK_LINK WHERE CHECKID = ?;"
			);

	public VoltTable[] run( 	
			String checkID
			)
					throws VoltAbortException {
		voltQueueSQL( sql, checkID);
		// voltExecuteSQL();
		return voltExecuteSQL();
	}
}


