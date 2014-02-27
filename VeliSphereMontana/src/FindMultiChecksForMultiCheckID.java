import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindMultiChecksForMultiCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT MULTICHECKID, STATE, OPERATOR FROM MULTICHECK WHERE MULTICHECKID = ?;"
			);

	public VoltTable[] run(
			String multicheckID)
					throws VoltAbortException {
		voltQueueSQL( sql , multicheckID);
		
		return voltExecuteSQL();
	}
}




