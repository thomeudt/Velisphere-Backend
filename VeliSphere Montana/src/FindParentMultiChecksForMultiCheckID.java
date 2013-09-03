import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindParentMultiChecksForMultiCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT MULTICHECKLID FROM MULTICHECK_MULTICHECK_LINK WHERE MULTICHECKRID = ?;"
			);

	public VoltTable[] run(
			String multicheckLID)
					throws VoltAbortException {
		voltQueueSQL( sql , multicheckLID);
		
		return voltExecuteSQL();
	}
}




