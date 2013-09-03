import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindChecksForMultiCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT CHECKID FROM MULTICHECK_CHECK_LINK WHERE MULTICHECKID = ?;"
			);

	public VoltTable[] run(
			String multicheckID)
					throws VoltAbortException {
		voltQueueSQL( sql , multicheckID);
		
		return voltExecuteSQL();
	}
}




