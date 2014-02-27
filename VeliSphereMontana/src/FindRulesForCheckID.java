import java.util.ArrayList;

import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindRulesForCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT RULEID FROM RULE WHERE CHECKID IN ? ORDER BY RULEID;"
			);

	public VoltTable[] run(
			String[] checkIDs)
					throws VoltAbortException {
		voltQueueSQL( sql , (Object) checkIDs);
		
		return voltExecuteSQL();
	}
}




