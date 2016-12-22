import java.util.HashSet;
import java.util.Iterator;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class BLE_UpdateCheckState extends VoltProcedure {
	
	public final SQLStmt sqlUpdateChecks = new SQLStmt(
	 	"UPDATE CHECKSTATE SET STATE = ? WHERE CHECKPATHID = ? AND CHECKID = ?;");

	
	
	public VoltTable[] run(String checkPathID, String checkID, int state)
			throws VoltAbortException {
		
		voltQueueSQL(sqlUpdateChecks, state, checkPathID, checkID);
						
		VoltTable[] sqlUpdateChecksResults = voltExecuteSQL();

		return sqlUpdateChecksResults;
	}
}
