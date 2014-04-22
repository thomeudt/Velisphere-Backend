import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;

public class BLE_ChecksForExpression extends VoltProcedure {

	public final SQLStmt sqlFindMatchingChecks = new SQLStmt(
			"SELECT CHECKID, OPERATOR, CHECKVALUE, CHECKPATHID FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ? AND EXPIRED = ? ORDER BY CHECKID, CHECKVALUE, CHECKPATHID;");

	
	
	
	public VoltTable[] run(String endpointID, String propertyID,
			String checkValue, byte expired)
			throws VoltAbortException {
		voltQueueSQL(sqlFindMatchingChecks, endpointID, propertyID, expired);
		// voltExecuteSQL();

		VoltTable[] findMatchingChecksResults = voltExecuteSQL();

		VoltTable trueChecks = new VoltTable(
				new VoltTable.ColumnInfo("CHECKID", VoltType.STRING),
				new VoltTable.ColumnInfo("OPERATOR", VoltType.STRING),
				new VoltTable.ColumnInfo("CHECKVALUE", VoltType.STRING),
				new VoltTable.ColumnInfo("CHECKPATHID", VoltType.STRING));
		VoltTable falseChecks = new VoltTable(
				new VoltTable.ColumnInfo("CHECKID", VoltType.STRING),
				new VoltTable.ColumnInfo("OPERATOR", VoltType.STRING),
				new VoltTable.ColumnInfo("CHECKVALUE", VoltType.STRING),
				new VoltTable.ColumnInfo("CHECKPATHID", VoltType.STRING));
		
		
		if (findMatchingChecksResults.length != 0) {

	
			VoltTable findMatchingChecks = findMatchingChecksResults[0];
			
			// build arrays of false and true checks
			
			while (findMatchingChecks.advanceRow()) {
				
				String expressionCheckValue = findMatchingChecks.getString("CHECKVALUE");
								
				if (findMatchingChecks.getString("OPERATOR").equals("=")
						&& expressionCheckValue.equals(
								checkValue)) {

					trueChecks.add(findMatchingChecks);
					
				} else if (findMatchingChecks.getString("OPERATOR").equals("!=")
						&& expressionCheckValue.equals(checkValue)==false) {
					
					trueChecks.add(findMatchingChecks);
				} else if (findMatchingChecks.getString("OPERATOR").equals("<")
						&&  Float.parseFloat(checkValue) < Float.parseFloat(expressionCheckValue)) {
					
					trueChecks.add(findMatchingChecks);
				
				} else if (findMatchingChecks.getString("OPERATOR").equals(">")
						&& Float.parseFloat(checkValue) > Float.parseFloat(expressionCheckValue)) {
					
					trueChecks.add(findMatchingChecks);
				} else if (findMatchingChecks.getString("OPERATOR").equals(">=")
						&& Float.parseFloat(checkValue) >= Float.parseFloat(expressionCheckValue)) {
					
					trueChecks.add(findMatchingChecks);
				} else if (findMatchingChecks.getString("OPERATOR").equals("<=")
						&& Float.parseFloat(checkValue) <= Float.parseFloat(expressionCheckValue)) {
					
					trueChecks.add(findMatchingChecks);
				} else
					
					falseChecks.add(findMatchingChecks);
			}
		}
	
		VoltTable returnTrueFalseChecks[] = new VoltTable[2];
		returnTrueFalseChecks[0] = trueChecks;
		returnTrueFalseChecks[1] = falseChecks;
		return returnTrueFalseChecks;
	}
}
