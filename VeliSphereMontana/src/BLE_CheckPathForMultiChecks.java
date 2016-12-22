import java.util.HashSet;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;


public class BLE_CheckPathForMultiChecks extends VoltProcedure {


	public final SQLStmt sqlMultiChecksInCheckpath = new SQLStmt(
			"SELECT CHECKPATHID, MULTICHECKID FROM MULTICHECK WHERE CHECKPATHID = ? ORDER BY CHECKPATHID, MULTICHECKID;"
			);
	public final SQLStmt sqlResetMultiChecksInCheckpath = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = 0 WHERE MULTICHECKID = ? AND CHECKPATHID = ?;"
			);


	public VoltTable[] run( 	
			String checkPathID
			)
					throws VoltAbortException {
				
		voltQueueSQL( sqlMultiChecksInCheckpath, checkPathID);
		
		VoltTable[] multiChecksInCheckpathResults = voltExecuteSQL();

		if (multiChecksInCheckpathResults.length != 0){

			HashSet<String> mC = new HashSet<String>(); 
			VoltTable multiChecksInCheckpath = multiChecksInCheckpathResults[0];
			while (multiChecksInCheckpath.advanceRow()){
				mC.add(multiChecksInCheckpath.getString("MULTICHECKID"));
			}
			
			// resetting multichecks
			
			for (String sTR : mC){
				// System.out.println(sTR);
				voltQueueSQL( sqlResetMultiChecksInCheckpath, sTR, checkPathID);	
			}
		}

		return multiChecksInCheckpathResults;
	}
}


