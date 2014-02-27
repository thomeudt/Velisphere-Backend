import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class UpdateMultiChecks extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"UPDATE MULTICHECK SET STATE = ? WHERE MULTICHECKID = ?" 
			);

	public VoltTable[] run( 	
			byte state,
			String multicheckID)
			throws VoltAbortException {
		voltQueueSQL( sql, state, multicheckID );
		return voltExecuteSQL();
	}
}




