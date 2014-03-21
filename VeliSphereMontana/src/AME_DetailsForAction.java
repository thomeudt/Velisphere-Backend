import org.voltdb.SQLStmt;
import org.voltdb.VoltProcedure;
import org.voltdb.VoltTable;

public class AME_DetailsForAction extends VoltProcedure {

	public final SQLStmt sqlFindAllDetails = new SQLStmt(
			"SELECT ACTION.ACTIONID, ACTION.TARGETENDPOINTID, ACTION.TGTEPIDFROMINBOUNDPROP, OUTBOUNDPROPERTYACTION.OUTBOUNDPROPERTYID, " +
			"OUTBOUNDPROPERTYACTION.INBOUNDPROPERTYID, OUTBOUNDPROPERTYACTION.CURRENTSTATEPROPERTYID, " +
			"OUTBOUNDPROPERTYACTION.CUSTOMPAYLOAD FROM ACTION INNER JOIN OUTBOUNDPROPERTYACTION ON " +
			"ACTION.ACTIONID = OUTBOUNDPROPERTYACTION.ACTIONID AND OUTBOUNDPROPERTYACTION.ACTIONID = ? ORDER BY ACTION.ACTIONID;");

	public VoltTable[] run(
			String checkpathID,
			String actionID)
			throws VoltAbortException {
		voltQueueSQL(sqlFindAllDetails, actionID);
		// voltExecuteSQL();
		return voltExecuteSQL();
	}
}
