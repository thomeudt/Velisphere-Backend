import java.util.HashSet;
import java.util.Iterator;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class BLE_ChecksForExpression extends VoltProcedure {

	public final SQLStmt sqlFindTrueChecks = new SQLStmt(
			"SELECT CHECKID, OPERATOR, CHECKVALUE FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ? AND EXPIRED = ?;");

	public final SQLStmt sqlUpdateTrueChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = 1 WHERE CHECKID = ?;");

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
				if (findTrueChecks.getString("OPERATOR").equals("=")
						&& findTrueChecks.getString("CHECKVALUE").equals(
								checkValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals("!=")
						&& findTrueChecks.getString("CHECKVALUE").equals(checkValue)==false) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals("<")
						&& Float.parseFloat(findTrueChecks
								.getString("CHECKVALUE")) < Float
								.parseFloat(checkValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals(">")
						&& Float.parseFloat(findTrueChecks
								.getString("CHECKVALUE")) > Float
								.parseFloat(checkValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals(">=")
						&& Float.parseFloat(findTrueChecks
								.getString("CHECKVALUE")) >= Float
								.parseFloat(checkValue)) {
					trueChecksList.add(findTrueChecks.getString("CHECKID"));
				} else if (findTrueChecks.getString("OPERATOR").equals("<=")
						&& Float.parseFloat(findTrueChecks
								.getString("CHECKVALUE")) <= Float
								.parseFloat(checkValue)) {
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
