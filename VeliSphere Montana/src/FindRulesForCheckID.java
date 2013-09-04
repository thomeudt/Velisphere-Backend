import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindRulesForCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT RULEID FROM RULE WHERE CHECKID = ?;"
			);

	public VoltTable[] run(
			String checkID)
					throws VoltAbortException {
		voltQueueSQL( sql , checkID);
		
		return voltExecuteSQL();
	}
}




