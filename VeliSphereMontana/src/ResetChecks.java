import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class ResetChecks extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"UPDATE CHECK SET STATE = 0 WHERE ENDPOINTID = ?" 
			);

	public VoltTable[] run( 	
			String endpointID)
			throws VoltAbortException {
		voltQueueSQL( sql, endpointID );
		return voltExecuteSQL();
	}
}




