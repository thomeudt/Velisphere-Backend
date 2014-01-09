import java.util.HashSet;
import java.util.Iterator;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class BLE_ChecksForExpression extends VoltProcedure {

	public final SQLStmt sqlFindTrueChecks = new SQLStmt(
			"SELECT CHECKID, OPERATOR, CHECKVALUE, CHECKPATHID FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ? AND EXPIRED = ?;");

	public final SQLStmt sqlUpdateTrueChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = 1 WHERE CHECKID = ?;");
	
	public final SQLStmt sqlUpdateFalseChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = 0 WHERE CHECKID = ?;");
	
	public VoltTable[] run(String endpointID, String propertyID,
			String checkValue, byte expired)
			throws VoltAbortException {
		voltQueueSQL(sqlFindTrueChecks, endpointID, propertyID, expired);
		// voltExecuteSQL();

		VoltTable[] findTrueChecksResults = voltExecuteSQL();

		if (findTrueChecksResults.length != 0) {

			HashSet<String> trueChecksList = new HashSet<String>();
			
					

			VoltTable findTrueChecks = findTrueChecksResults[0];
			while (findTrueChecks.advanceRow()) {
				String expressionCheckValue = findTrueChecks.getString("CHECKVALUE");
				
				
				// reset check to false first, then evaluate
				
				voltQueueSQL(sqlUpdateFalseChecks, findTrueChecks.getString("CHECKID"));
				voltExecuteSQL();
				
				if (findTrueChecks.getString("OPERATOR").equals("=")
						&& expressionCheckValue.equals(
								checkValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals("!=")
						&& expressionCheckValue.equals(checkValue)==false) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals("<")
						&&  Float.parseFloat(checkValue) < Float.parseFloat(expressionCheckValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				
				} else if (findTrueChecks.getString("OPERATOR").equals(">")
						&& Float.parseFloat(checkValue) > Float.parseFloat(expressionCheckValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals(">=")
						&& Float.parseFloat(checkValue) >= Float.parseFloat(expressionCheckValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals("<=")
						&& Float.parseFloat(checkValue) <= Float.parseFloat(expressionCheckValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} 
				
			}

			Iterator<String> itTCL = trueChecksList.iterator();
			while (itTCL.hasNext()) {
				String sTR = itTCL.next();
				voltQueueSQL(sqlUpdateTrueChecks, sTR);
				voltExecuteSQL();
			}

		}

		return findTrueChecksResults;
	}
}
