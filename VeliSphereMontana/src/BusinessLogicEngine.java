import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltTableRow;
import org.voltdb.VoltType;


public class BusinessLogicEngine extends VoltProcedure {
	
	HashSet<String> trueChecks = new HashSet<String>();
	
	public final SQLStmt sqlFindChecks = new SQLStmt(
			"SELECT CHECKID, CHECKVALUE, OPERATOR FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ?;"
			);
	
	public final SQLStmt sqlUpdateChecks = new SQLStmt(
			"UPDATE CHECK SET STATE = 1 WHERE CHECKID = ?;");
	
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
	
		//System.out.println("BLE------------------");
		
		
		HashSet<String> relevantCheckPathIDs = new HashSet<String>();
		while (findChecksResults[0].advanceRow()) {
			if(markTrueChecks(findChecksResults[0], checkValue) == 1)
			{				
				String checkId = findChecksResults[0].getString("CHECKID");
				trueChecks.add(checkId);
			}
		}
		
		Iterator<String> git = trueChecks.iterator();
		//System.out.println("GITTER " + trueChecks);
		while (git.hasNext()){
			
			HashSet<String> checkPathCurrentCheck = checkPathsForCheckID(git.next());
			relevantCheckPathIDs.addAll(checkPathCurrentCheck);			
		}
		
		
		HashSet<String> parentMultiChecks = new HashSet<String>();
	
		/*
		if(relevantCheckPathIDs.isEmpty() == false){
			Iterator<String> it = relevantCheckPathIDs.iterator();
			while(it.hasNext()){

				// evaluate first level multichecks and get parent multichecks
				parentMultiChecks.addAll(evalMultiChecksForCheckPathID(it.next()));	
				
			}
			
			// evaluate all other multichecks

			HashSet<String> highLevelMultiChecks = new HashSet<String>();
			HashSet<String> moreHighLevelMultiChecks = new HashSet<String>();
			

			highLevelMultiChecks = parentMultiChecks;

			while (highLevelMultiChecks.isEmpty() == false) {
				moreHighLevelMultiChecks.clear();
				for (String sTR : highLevelMultiChecks) {
					voltQueueSQL( sqlFindOperatorForMultiCheck, sTR);
					VoltTable[] findOperatorResults = voltExecuteSQL();

					//System.out.println("LEN " + findOperatorResults[0].getRowCount());
					if (findOperatorResults[0].getRowCount() > 0) {
						findOperatorResults[0].advanceRow(); 
						String operator = findOperatorResults[0].getString("OPERATOR");
						
						moreHighLevelMultiChecks.add(evaluateMultiCheck(sTR, operator));	
					}
					
				}
											
				highLevelMultiChecks.clear();
				highLevelMultiChecks.addAll(moreHighLevelMultiChecks);
				
			}
			
		}
	
	*/
		Iterator<String> rIT = trueChecks.iterator();
		
		
		
		while (rIT.hasNext()){
			voltQueueSQL( sqlRules , rIT.next());
			
			//vT.addRow(vA);
			
		}
			
		VoltTable[] vA = voltExecuteSQL();
		
		return vA;
		
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
		
		voltQueueSQL(sqlUpdateChecks, check.getString("CHECKID"));
		//System.out.println("CHECK: " + check.getString("CHECKID") + " marked " + trueCheck);
		voltExecuteSQL();
		
		return trueCheck;
		
	}
	
	private HashSet<String> checkPathsForCheckID(String checkId){
		
		voltQueueSQL( sqlCheckPathForChecks, checkId );
		VoltTable[] findCheckPathResults = voltExecuteSQL();
		VoltTable findCheckPathResult = findCheckPathResults[0];
		HashSet<String> checkPathIds = new HashSet<String>();
		
		
		
		if (findCheckPathResults.length > 0){
			while (findCheckPathResult.advanceRow()) {
				//System.out.println("CPR: " + findCheckPathResult);
				checkPathIds.add(findCheckPathResult.getString("CHECKPATHID"));
		    }
		}
					
		return checkPathIds;
	
	}
	
	private HashSet<String> evalMultiChecksForCheckPathID(String checkPathId){
		voltQueueSQL( sqlCheckpathForMultiChecks, checkPathId );
		VoltTable[] findMultiCheckResults = voltExecuteSQL();
		HashSet<String> parentMultiCheckIds = new HashSet<String> ();
		while (findMultiCheckResults[0].advanceRow()) {
			voltQueueSQL( sqlResetMultiChecksInCheckpath, findMultiCheckResults[0].getString("MULTICHECKID") );
			voltExecuteSQL();
			voltQueueSQL( sqlFindOperatorForMultiCheck, findMultiCheckResults[0].getString("MULTICHECKID"));
			VoltTable[] findOperatorResults = voltExecuteSQL();
			findOperatorResults[0].advanceRow(); 
			String operator = findOperatorResults[0].getString("OPERATOR");
			evaluateMultiCheck(findMultiCheckResults[0].getString("MULTICHECKID"), operator);
			voltQueueSQL( sqlFindLinkedMultiChecks, findMultiCheckResults[0].getString("MULTICHECKID"));
			VoltTable[] findMultiCheckParentResults = voltExecuteSQL();
			
			while (findMultiCheckParentResults[0].advanceRow()) {
				parentMultiCheckIds.add(findMultiCheckParentResults[0].getString("MULTICHECKLID"));
			}
			
		}		
		return parentMultiCheckIds;
	}
	
	
	private String evaluateMultiCheck(String multiCheckID, String operator){

		HashSet<Byte> childChecksState = new HashSet<Byte>();
		Byte state = 0;
		voltQueueSQL( sqlFindChildChecks, multiCheckID);
		VoltTable[] linkedChecksResults = voltExecuteSQL();
		
		if (linkedChecksResults.length != 0){
				
			VoltTable linkedChecks = linkedChecksResults[0];
			while (linkedChecks.advanceRow()){
								
				voltQueueSQL( sqlEvaluateChildChecks, linkedChecks.getString("CHECKID"));
				VoltTable[] linkedChecksEvalResults = voltExecuteSQL();
				VoltTable linkedChecksEval = linkedChecksEvalResults[0];
				while (linkedChecksEval.advanceRow()){
					VoltTableRow row = linkedChecksEval.fetchRow(0);
					childChecksState.add((Byte) row.get("STATE", VoltType.TINYINT));
				}
			}
		
			
			
			if (operator.equals("AND")){
				if (childChecksState.contains((byte)0)) {	
					state = 0;
					
				}
				
				if (childChecksState.contains((byte)1) && childChecksState.contains((byte)0)==false){
					state = 1;
					
				}
			}
			
			if (operator.equals("OR")){
				if (childChecksState.contains((byte)1)){
					state = 1;
		
				}
			}
	
			
			voltQueueSQL( sqlUpdateMultiCheck, state, multiCheckID);
			voltExecuteSQL();
			// System.out.println("State: " + state);
					
		}
		String returnTrueMultiCheck = new String();
		
		if (state == 1) {
			returnTrueMultiCheck = multiCheckID;
			trueChecks.add(multiCheckID);
		}
		
		return returnTrueMultiCheck;
		
	}
	

	
		
}
	



