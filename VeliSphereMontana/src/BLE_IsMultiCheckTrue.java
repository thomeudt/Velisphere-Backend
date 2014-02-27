import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltTableRow;
import org.voltdb.VoltType;


public class BLE_IsMultiCheckTrue extends VoltProcedure {


	public final SQLStmt sqlFindLinkedChecks = new SQLStmt(
			"SELECT CHECKID FROM MULTICHECK_CHECK_LINK WHERE MULTICHECKID = ? ORDER BY CHECKID;"
			);

	public final SQLStmt sqlEvaluateLinkedChecks = new SQLStmt(
			"SELECT CHECKID, STATE FROM CHECKSTATE WHERE CHECKID = ? ORDER BY CHECKID"
			);
	
	public final SQLStmt sqlFindOperatorForMultiCheck = new SQLStmt(
			"SELECT OPERATOR FROM MULTICHECK WHERE MULTICHECKID = ? ORDER BY OPERATOR;"
			);
			
	public final SQLStmt sqlUpdateTrueMultiCheck = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = ? WHERE MULTICHECKID = ?;"
			);

	public final SQLStmt sqlFindTrueMultiCheck = new SQLStmt(
			"SELECT MULTICHECKID FROM MULTICHECK WHERE MULTICHECKID = ? AND STATE = ? ORDER BY MULTICHECKID;"
			);
		
	public VoltTable[] run( 	
			String checkpathID,
			String multiCheckID
			)
					throws VoltAbortException {
		
		// find linked checks
		
		voltQueueSQL( sqlFindLinkedChecks, multiCheckID);
		
		HashSet<String> linkedChecksList = new HashSet<String>();
		VoltTable[] linkedChecksResults = voltExecuteSQL();
		
		if (linkedChecksResults.length != 0){
				
		VoltTable linkedChecks = linkedChecksResults[0];
		while (linkedChecks.advanceRow()){
			linkedChecksList.add(linkedChecks.getString("CHECKID"));
		}
		}
	
		
		
	
				
		// evaluate linked checks
		
		HashMap<String, Byte> evalChecksList = new HashMap<String, Byte>();
		

		Iterator<String> itLCL = linkedChecksList.iterator();
		
		
			while (itLCL.hasNext()){
				String sTR = itLCL.next();	
		
				// System.out.println("Evaling check: " + sTR);
				voltQueueSQL( sqlEvaluateLinkedChecks, sTR);

				VoltTable[] evaluateChecksResults = voltExecuteSQL();
				VoltTable evaluateChecks = evaluateChecksResults[0];
				VoltTableRow row = evaluateChecks.fetchRow(0);
				
				
				
				
				evalChecksList.put(row.getString("CHECKID"), (Byte) row.get("STATE", VoltType.TINYINT));
				
				/*
				while (evaluateChecks.advanceRow()){
					
					evalChecksList.put(evaluateChecks.getString("CHECKID"), (Byte) evaluateChecks.get("STATE", VoltType.TINYINT));
				}
					*/		
				}
			
		
		
		
		
		
		
				
		// check if multicheck is true

		Byte state = 0;
		
		if (evalChecksList.isEmpty() == false)
		{						
			voltQueueSQL( sqlFindOperatorForMultiCheck, multiCheckID);
		
			VoltTable[] operatorForMultiCheckResults = voltExecuteSQL();
			VoltTable operatorForMultiCheck = operatorForMultiCheckResults[0];
			operatorForMultiCheck.advanceRow();
			String operator = operatorForMultiCheck.getString("OPERATOR");
			
				
				if (operator.equals("AND")){
					if (evalChecksList.containsValue((byte)0)) {	
						state = 0;
						
					}
					
					// if (evalChecksList.containsValue((byte)0) && evalChecksList.containsValue((byte)1)==false){
					//	state = 0;
						
					//}
					
					if (evalChecksList.containsValue((byte)1) && evalChecksList.containsValue((byte)0)==false){
						state = 1;
					}
				}
				
				if (operator.equals("OR")){
					if (evalChecksList.containsValue((byte)1)){
						state = 1;
					}
				}
				
				
			}
		voltQueueSQL( sqlUpdateTrueMultiCheck, state, multiCheckID);
		voltExecuteSQL();
		// System.out.println("State: " + state);
		voltQueueSQL( sqlFindTrueMultiCheck, multiCheckID, 1);
						
		
		return voltExecuteSQL();
	}
}
