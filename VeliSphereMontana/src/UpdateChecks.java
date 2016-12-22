import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class UpdateChecks extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"UPDATE CHECK SET STATE = ? WHERE CHECKID = ?" 
			);

	public VoltTable[] run( 	
			byte state,
			String checkID)
			throws VoltAbortException {
		voltQueueSQL( sql, state, checkID );
		return voltExecuteSQL();
	}
}




