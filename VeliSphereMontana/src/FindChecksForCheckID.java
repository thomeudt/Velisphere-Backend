import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;
import org.voltdb.VoltProcedure.VoltAbortException;


public class FindChecksForCheckID extends VoltProcedure {


	public final SQLStmt sql = new SQLStmt(
			"SELECT CHECKID, STATE FROM CHECK WHERE CHECKID = ?;"
			);

	public VoltTable[] run(
			String checkID)
					throws VoltAbortException {
		voltQueueSQL( sql , checkID);
		
		return voltExecuteSQL();
	}
}




