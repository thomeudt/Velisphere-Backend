import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_AllChecksForEndpoint extends VoltProcedure {
	
	public final SQLStmt sqlFindTrueChecks = new SQLStmt(
			"SELECT CHECKID FROM CHECK WHERE ENDPOINTID = ?;"
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


