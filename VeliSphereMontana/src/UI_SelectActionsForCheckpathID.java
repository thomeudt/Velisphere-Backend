import java.util.ArrayList;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class UI_SelectActionsForCheckpathID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT ACTIONID, TARGETENDPOINTID, CHECKPATHNAME FROM ACTION "
			+ "JOIN CHECKPATH ON CHECKPATH.CHECKPATHID = ACTION.CHECKPATHID "
			+ "WHERE ACTION.CHECKPATHID = ? ORDER BY ACTIONID;"
			);

	public VoltTable[] run(
			String checkpathID)
					throws VoltAbortException {
		voltQueueSQL( sql , checkpathID);
		
		return voltExecuteSQL();
	}
}




