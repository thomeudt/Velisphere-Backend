import org.voltdb.*;


public class UI_SelectActionsForMulticheckID extends VoltProcedure {

	

	public final SQLStmt sqlFindAllDetails = new SQLStmt(
			"SELECT ACTION.ACTIONID, ACTION.ACTIONNAME, ACTION.TARGETENDPOINTID, OUTBOUNDPROPERTYACTION.OUTBOUNDPROPERTYID, " +
			"OUTBOUNDPROPERTYACTION.INBOUNDPROPERTYID, OUTBOUNDPROPERTYACTION.CURRENTSTATEPROPERTYID, " +
			"OUTBOUNDPROPERTYACTION.CUSTOMPAYLOAD FROM ACTION INNER JOIN OUTBOUNDPROPERTYACTION ON " +
			"ACTION.ACTIONID = OUTBOUNDPROPERTYACTION.ACTIONID AND ACTION.MULTICHECKID = ? ORDER BY ACTION.ACTIONID;");

	public VoltTable[] run(
			String checkpathID,
			String multicheckID)
			throws VoltAbortException {
		voltQueueSQL(sqlFindAllDetails, multicheckID);
		// voltExecuteSQL();
		return voltExecuteSQL();
	}
}