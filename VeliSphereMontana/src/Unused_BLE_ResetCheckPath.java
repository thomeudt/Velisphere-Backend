import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltType;
import org.voltdb.VoltProcedure.VoltAbortException;


public class Unused_BLE_ResetCheckPath extends VoltProcedure {
	
	
	public final SQLStmt sqlFindAllChecks = new SQLStmt(
			"SELECT CHECKID FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ?;"
			);
	
	public final SQLStmt sqlFindCheckPath = new SQLStmt(
			"SELECT CHECKPATHID FROM CHECKPATH_CHECK_LINK WHERE CHECKID = ?;"
			);
	
	
	public final SQLStmt sqlUpdateTrueChecks = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = 0 WHERE CHECKID = ?;"
			);

	public VoltTable[] run( 	
			String endpointID, 
			String propertyID, 
			String checkValue, 
			String operator,
			byte expired)
					throws VoltAbortException {
		
		
		voltQueueSQL( sqlFindAllChecks, endpointID, propertyID );
		VoltTable[] findAllChecksResults = voltExecuteSQL();
				
		// voltExecuteSQL();

		
		if (findAllChecksResults.length != 0){

			List<String> findAllChecksList = new ArrayList<String>();			

			VoltTable findAllChecks = findAllChecksResults[0];
			while (findAllChecks.advanceRow()){
				findAllChecksList.add(findAllChecks.getString("CHECKID"));
			}

			
			Iterator<String> itACL = findAllChecksList.iterator();
			while (itACL.hasNext()){
				String sTR = itACL.next();
				voltQueueSQL( sqlFindCheckPath, sTR );
				VoltTable[] findCheckPaths = voltExecuteSQL();

				List<String> findCheckPathList = new ArrayList<String>();			
				VoltTable findCheckPath = findAllChecksResults[0];
				while (findCheckPath.advanceRow()){
					findCheckPathList.add(findCheckPath.getString("CHECKID"));				
			}
				
				
				
		}
		}

		return null;
	}
	}


