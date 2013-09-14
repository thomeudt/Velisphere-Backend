import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_ChecksForExpression extends VoltProcedure {
	
	public final SQLStmt sqlFindTrueChecks = new SQLStmt(
			"SELECT CHECKID FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ? AND CHECKVALUE = ? AND OPERATOR = ? AND EXPIRED = ?;"
			);

	public final SQLStmt sqlUpdateTrueChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = 1 WHERE CHECKID = ?;"
			);

	public VoltTable[] run( 	
			String endpointID, 
			String propertyID, 
			String checkValue, 
			String operator,
			byte expired)
					throws VoltAbortException {
		voltQueueSQL( sqlFindTrueChecks, endpointID, propertyID, checkValue, operator, expired );
		// voltExecuteSQL();

		VoltTable[] findTrueChecksResults = voltExecuteSQL();

		if (findTrueChecksResults.length != 0){

			HashSet<String> trueChecksList = new HashSet<String>();			

			VoltTable findTrueChecks = findTrueChecksResults[0];
			while (findTrueChecks.advanceRow()){
				trueChecksList.add(findTrueChecks.getString("CHECKID"));
			}

			
			Iterator<String> itTCL = trueChecksList.iterator();
			while (itTCL.hasNext()){
				String sTR = itTCL.next();
				voltQueueSQL( sqlUpdateTrueChecks, sTR );
				voltExecuteSQL();
			}
		}

		return findTrueChecksResults;
	}
}


