import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_CheckPathForMultiChecks extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT CHECKPATHID, MULTICHECKID FROM CHECKPATH_MULTICHECK_LINK WHERE CHECKPATHID = ?;"
			);

	public VoltTable[] run( 	
			String checkPathID
			)
					throws VoltAbortException {
		voltQueueSQL( sql, checkPathID);
		// voltExecuteSQL();
		return voltExecuteSQL();
	}
}


