import java.util.ArrayList;
import java.util.List;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class ImprovedFindMatchingChecksEqual extends VoltProcedure {


	public final SQLStmt sqlUpdateTrueChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = 1 WHERE ENDPOINTID = ? AND PROPERTYID = ? AND CHECKVALUE = ? AND OPERATOR = ? AND EXPIRED = ?;"
			);
	
	public final SQLStmt sqlSelectTrueChecks = new SQLStmt(
			"SELECT * FROM CHECK WHERE STATE = 1 AND ENDPOINTID = ?;"
			);
		
	public VoltTable[] run( 	
			String endpointID, 
			String propertyID, 
			String checkValue, 
			String operator,
			byte expired)
					throws VoltAbortException {
		
		voltQueueSQL( sqlUpdateTrueChecks, endpointID, propertyID, checkValue, operator, expired );
		
		voltQueueSQL( sqlSelectTrueChecks, endpointID);
		
		// voltExecuteSQL();
		
		VoltTable[] selectResults = voltExecuteSQL();
		return selectResults;
				
	}
}




