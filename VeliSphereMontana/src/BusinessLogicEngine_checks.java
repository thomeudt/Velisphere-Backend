import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;


public class BusinessLogicEngine_checks extends VoltProcedure {
	
	HashSet<String> trueChecks = new HashSet<String>();
	
	public final SQLStmt sqlFindChecks = new SQLStmt(
			"SELECT CHECKID, CHECKVALUE, OPERATOR FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ?;"
			);
	
	public final SQLStmt sqlFindChecksByID = new SQLStmt(
			"SELECT CHECKID FROM CHECK WHERE CHECKID = ?;"
			);
		
	public final SQLStmt sqlUpdateChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = ? WHERE CHECKID = ?;");
	
	public final SQLStmt sqlCheckPathForChecks = new SQLStmt(
			"SELECT * FROM CHECKPATH_CHECK_LINK WHERE CHECKID = ?;"
			);
	
	public final SQLStmt sqlCheckpathForMultiChecks = new SQLStmt(
			"SELECT CHECKPATHID, MULTICHECKID FROM CHECKPATH_MULTICHECK_LINK WHERE CHECKPATHID = ?;"
			);
	
	public final SQLStmt sqlResetMultiChecksInCheckpath = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = 0 WHERE MULTICHECKID = ?;"
			);
	
	public final SQLStmt sqlFindChildChecks = new SQLStmt(
			"SELECT CHECKID FROM MULTICHECK_CHECK_LINK WHERE MULTICHECKID = ?;"
			);

	public final SQLStmt sqlEvaluateChildChecks = new SQLStmt(
			"SELECT CHECKID, STATE FROM CHECK WHERE CHECKID = ?"
			);
	
	public final SQLStmt sqlFindOperatorForMultiCheck = new SQLStmt(
			"SELECT OPERATOR FROM MULTICHECK WHERE MULTICHECKID = ?;"
			);
			
	public final SQLStmt sqlUpdateMultiCheck = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = ? WHERE MULTICHECKID = ?;"
			);

	
	public final SQLStmt sqlFindLinkedMultiChecks = new SQLStmt(
			"SELECT MULTICHECKLID FROM MULTICHECK_MULTICHECK_LINK WHERE MULTICHECKRID = ?;"
			);
	

	public final SQLStmt sqlEvaluateLinkedMultiChecks = new SQLStmt(
			"SELECT MULTICHECKID, STATE FROM MULTICHECK WHERE MULTICHECKID = ?"
			);
	
	public final SQLStmt sqlRules = new SQLStmt(
			"SELECT RULEID FROM RULE WHERE CHECKID = ?;"
			);
	
	public VoltTable[] run( 	
			String endpointID, 
			String propertyID,
			String checkValue)
					throws VoltAbortException {
		
		
		voltQueueSQL( sqlFindChecks, endpointID, propertyID );
		VoltTable[] findChecksResults = voltExecuteSQL();
	
		System.out.println("BLE------------------");
		
		HashSet<String> relevantCheckPathIDs = new HashSet<String>();
		
		while (findChecksResults[0].advanceRow()) {
			if(markTrueChecks(findChecksResults[0], checkValue) == 1)
			{				
				String checkId = findChecksResults[0].getString("CHECKID");
				trueChecks.add(checkId);
				voltQueueSQL( sqlFindChecksByID, checkId );
				
			}
		}
			
		return voltExecuteSQL();
		
		
		
	}
	
	
	
	
	private int markTrueChecks(VoltTable check, String checkValue){

		int trueCheck = 0;
		String expressionCheckValue = check.getString("CHECKVALUE");
		
		if (check.getString("OPERATOR").equals("=")
				&& expressionCheckValue.equals(
						checkValue)) {
			trueCheck = 1;
		} else if (check.getString("OPERATOR").equals("!=")
				&& expressionCheckValue.equals(checkValue)==false) {
			trueCheck = 1;
		} else if (check.getString("OPERATOR").equals("<")
				&&  Float.parseFloat(checkValue) < Float.parseFloat(expressionCheckValue)) {
			trueCheck = 1;
		
		} else if (check.getString("OPERATOR").equals(">")
				&& Float.parseFloat(checkValue) > Float.parseFloat(expressionCheckValue)) {
			trueCheck = 1;
		} else if (check.getString("OPERATOR").equals(">=")
				&& Float.parseFloat(checkValue) >= Float.parseFloat(expressionCheckValue)) {
			trueCheck = 1;
		} else if (check.getString("OPERATOR").equals("<=")
				&& Float.parseFloat(checkValue) <= Float.parseFloat(expressionCheckValue)) {
			trueCheck = 1;
		} 
		
		voltQueueSQL(sqlUpdateChecks, trueCheck, check.getString("CHECKID"));
		
		
		return trueCheck;
		
	}
	
	

	
		
}
	



