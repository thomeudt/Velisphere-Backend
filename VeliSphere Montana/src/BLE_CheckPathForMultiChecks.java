import java.util.HashSet;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class BLE_CheckPathForMultiChecks extends VoltProcedure {


	public final SQLStmt sqlMultiChecksInCheckpath = new SQLStmt(
			"SELECT CHECKPATHID, MULTICHECKID FROM CHECKPATH_MULTICHECK_LINK WHERE CHECKPATHID = ?;"
			);
	public final SQLStmt sqlResetMultiChecksInCheckpath = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = 0 WHERE MULTICHECKID = ?;"
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
			
			for (String sTR : mC){
				System.out.println(sTR);
				voltQueueSQL( sqlResetMultiChecksInCheckpath, sTR);	
			}
		}

		return multiChecksInCheckpathResults;
	}
}


