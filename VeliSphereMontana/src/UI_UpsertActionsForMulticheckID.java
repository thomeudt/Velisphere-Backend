import java.util.ArrayList;
import java.util.Iterator;

import org.voltdb.*;

public class UI_UpsertActionsForMulticheckID extends VoltProcedure {

	// This assumes that outboundpropertyactionid = actionid because of a 1:1 relationship for the time being
	
	
	public final SQLStmt updateAction = new SQLStmt(
			" UPDATE ACTION SET ACTIONNAME = ?, TARGETENDPOINTID = ?, MULTICHECKID =?, CHECKPATHID = ? WHERE ACTIONID = ?");
	public final SQLStmt updateOutboundPropertyAction = new SQLStmt(
			"UPDATE OUTBOUNDPROPERTYACTION SET OUTBOUNDPROPERTYID = ?, INBOUNDPROPERTYID = ?, CUSTOMPAYLOAD = ?, ACTIONID = ?, CHECKPATHID = ? WHERE OUTBOUNDPROPERTYACTIONID = ?");

	public final SQLStmt addAction = new SQLStmt(
			" INSERT INTO ACTION VALUES (?, ?, ?, '', 0, '', ?, ?)");
	public final SQLStmt addOutboundPropertyAction = new SQLStmt(
			"INSERT INTO OUTBOUNDPROPERTYACTION VALUES (?, ?, ?, '', '', ?, ?, ?)");

	public VoltTable[] run(String actionID, String actionName,
			String endpointID, String multicheckID, String checkpathID,
			String propertyID, String propertyIdIndex, String manualValue)
			throws VoltAbortException {

		voltQueueSQL(updateAction, actionName, endpointID, multicheckID,
				checkpathID, actionID);

		VoltTable results1[] = voltExecuteSQL();
		long rowsAffected1 = results1[0].asScalarLong();

		if (rowsAffected1 == 0) {
			voltQueueSQL(addAction, actionID, actionName, endpointID, multicheckID,
					checkpathID);
			voltExecuteSQL();
		}

		voltQueueSQL(updateOutboundPropertyAction, propertyID,
				propertyIdIndex, manualValue, actionID, checkpathID, actionID);

		VoltTable results2[] = voltExecuteSQL();
		long rowsAffected2 = results2[0].asScalarLong();

		if (rowsAffected2 == 0) {

			voltQueueSQL(addOutboundPropertyAction, actionID, propertyID,
					propertyIdIndex, manualValue, actionID, checkpathID);
			voltExecuteSQL();

		}

		return results1;

	}
}