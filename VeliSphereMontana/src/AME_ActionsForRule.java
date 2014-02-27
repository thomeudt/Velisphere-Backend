import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class AME_ActionsForRule extends VoltProcedure {

	public final SQLStmt sqlFindAllActions = new SQLStmt(
			"SELECT ACTIONID FROM RULE_ACTION_LINK WHERE RULEID = ? ORDER BY ACTIONID;");

	public VoltTable[] run(String ruleID)
			throws VoltAbortException {
		voltQueueSQL(sqlFindAllActions, ruleID);
		// voltExecuteSQL();
		return voltExecuteSQL();
	}
}
