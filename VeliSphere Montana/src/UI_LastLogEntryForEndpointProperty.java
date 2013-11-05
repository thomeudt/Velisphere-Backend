import org.voltdb.*;

public class UI_LastLogEntryForEndpointProperty extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      " SELECT TOP 1 ENDPOINTID, PROPERTYID, PROPERTYENTRY, TIME_STAMP FROM ENDPOINTPROPERTYLOG WHERE ENDPOINTID = ? AND " +
      " PROPERTYID = ? ORDER BY TIME_STAMP DESC;" );

  public VoltTable[] run(
		  String endpointID,
		  String propertyID)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointID, propertyID );
          return voltExecuteSQL();

      }
}