import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindMatchingChecksEqual extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT * FROM CHECK WHERE ENDPOINTID = ? AND PROPERTYID = ? AND CHECKVALUE = ? AND OPERATOR = ? AND EXPIRED = ?;"
			);

	public VoltTable[] run( 	
			String endpointID, 
			String propertyID, 
			String checkValue, 
			String operator,
			byte expired)
					throws VoltAbortException {
		voltQueueSQL( sql, endpointID, propertyID, checkValue, operator, expired );
		// voltExecuteSQL();
		return voltExecuteSQL();
	}
}




