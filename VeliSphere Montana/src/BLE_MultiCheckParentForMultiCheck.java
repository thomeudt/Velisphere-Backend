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


public class BLE_MultiCheckParentForMultiCheck extends VoltProcedure {

	public final SQLStmt sqlFindLinkedMultiChecks = new SQLStmt(
			"SELECT MULTICHECKLID FROM MULTICHECK_MULTICHECK_LINK WHERE MULTICHECKRID = ?;"
			);
			
	public VoltTable[] run( 	
			String multiCheckID
			)
					throws VoltAbortException {
		
		
		// find linked multichecks
		
		voltQueueSQL( sqlFindLinkedMultiChecks, multiCheckID);
		
		
		return voltExecuteSQL();
	}
}


