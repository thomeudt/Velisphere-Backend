import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;
import org.voltdb.VoltType;


public class BLE_IsCycleMultiCheckTrue extends VoltProcedure {

	public final SQLStmt sqlFindLinkedMultiChecks = new SQLStmt(
			"SELECT MULTICHECKLID FROM MULTICHECK_MULTICHECK_LINK WHERE MULTICHECKRID = ?;"
			);

	public final SQLStmt sqlEvaluateLinkedMultiChecks = new SQLStmt(
			"SELECT MULTICHECKID, STATE FROM MULTICHECK WHERE MULTICHECKID = ?"
			);
	
	public final SQLStmt sqlFindOperatorForMultiCheck = new SQLStmt(
			"SELECT OPERATOR FROM MULTICHECK WHERE MULTICHECKID = ?;"
			);
			
	public final SQLStmt sqlUpdateTrueMultiCheck = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = ? WHERE MULTICHECKID = ?;"
			);

	public final SQLStmt sqlFindTrueMultiCheck = new SQLStmt(
			"SELECT MULTICHECKID FROM MULTICHECK WHERE MULTICHECKID = ? AND STATE = ?;"
			);
		
	public VoltTable[] run( 	
			String multiCheckID
			)
					throws VoltAbortException {
		
		
		// find linked multichecks
		
		voltQueueSQL( sqlFindLinkedMultiChecks, multiCheckID);
		
		List<String> linkedMultiChecksList = new ArrayList<String>();
		VoltTable[] linkedMultiChecksResults = voltExecuteSQL();
		
		if (linkedMultiChecksResults.length != 0){
				
		VoltTable linkedMultiChecks = linkedMultiChecksResults[0];
		while (linkedMultiChecks.advanceRow()){
			linkedMultiChecksList.add(linkedMultiChecks.getString("MULTICHECKLID"));
		}
		}
		
		
		
		// evaluate linked multichecks
		
		HashMap<String, Byte> evalMultiChecksList = new HashMap<String, Byte>();
		

		Iterator<String> itLCL = linkedMultiChecksList.iterator();
		
		if (linkedMultiChecksList.isEmpty() == false)
		{

			while (itLCL.hasNext()){
				String sTR = itLCL.next();	
				voltQueueSQL( sqlEvaluateLinkedMultiChecks, sTR);
				
				VoltTable[] evaluateMultiChecksResults = voltExecuteSQL();
				VoltTable evaluateMultiChecks = evaluateMultiChecksResults[0];
				while (evaluateMultiChecks.advanceRow()){
					evalMultiChecksList.put(evaluateMultiChecks.getString("MULTICHECKID"), (Byte) evaluateMultiChecks.get("STATE", VoltType.TINYINT));
				}
			}
		}

		
		
		// check if multicheck is true

		Byte state = 0;
		
		if (evalMultiChecksList.isEmpty() == false)
		{						
			voltQueueSQL( sqlFindOperatorForMultiCheck, multiCheckID);
		
			VoltTable[] operatorForMultiCheckResults = voltExecuteSQL();
			VoltTable operatorForMultiCheck = operatorForMultiCheckResults[0];
			operatorForMultiCheck.advanceRow();
			String operator = operatorForMultiCheck.getString("OPERATOR");
			
				
				if (operator.equals("AND")){
					if (evalMultiChecksList.containsValue((byte)1) && evalMultiChecksList.containsValue((byte)0)){
						state = 0;
					}
					
				}
				if (operator.equals("AND")){
					if (evalMultiChecksList.containsValue((byte)1)){
						state = 1;
						
					}
					
				}
				
				if (operator.equals("OR")){
					if (evalMultiChecksList.containsValue((byte)1)){
						state = 1;
					}
				}
				
				
				
				if (state == 1) {
					voltQueueSQL( sqlUpdateTrueMultiCheck, state, multiCheckID);
					voltExecuteSQL();
					
				}
				
			}
		
		
		voltQueueSQL( sqlFindTrueMultiCheck, multiCheckID, 1);
						
		
		return voltExecuteSQL();
	}
}


