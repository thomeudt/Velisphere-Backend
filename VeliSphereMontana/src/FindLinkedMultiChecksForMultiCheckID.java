import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindLinkedMultiChecksForMultiCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT MULTICHECKRID FROM MULTICHECK_MULTICHECK_LINK WHERE MULTICHECKLID = ?"
			+ " ORDER BY MULTICHECKRID;"
			);

	public VoltTable[] run(
			String multicheckLID)
					throws VoltAbortException {
		voltQueueSQL( sql , multicheckLID);
		
		return voltExecuteSQL();
	}
}




