import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;


public class BLE_AllChecksForExpression extends VoltProcedure {
	
	public final SQLStmt sqlFindTrueChecks = new SQLStmt(
			"SELECT CHECKID FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ? ORDER BY CHECKID;"
			);


	public VoltTable[] run( 	
			String endpointID, 
			String propertyID)
					throws VoltAbortException {
		voltQueueSQL( sqlFindTrueChecks, endpointID, propertyID );
		// voltExecuteSQL();

		VoltTable[] findTrueChecksResults = voltExecuteSQL();
	

		return findTrueChecksResults;
	}
}


