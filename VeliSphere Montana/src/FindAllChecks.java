import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindAllChecks extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT * FROM CHECK;"
			);

	public VoltTable[] run()
					throws VoltAbortException {
		// voltQueueSQL( sql, endpointID, propertyID, checkValue, operator, expired );
		voltQueueSQL( sql );
		voltExecuteSQL();
		return null;
	}
}




