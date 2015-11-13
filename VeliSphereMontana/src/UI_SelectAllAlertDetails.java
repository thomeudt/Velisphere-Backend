import org.voltdb.*;

public class UI_SelectAllAlertDetails extends VoltProcedure {

	
	public final SQLStmt alertsForEndpoint = new SQLStmt(" select alertid, userid, endpointid, alertname, property, operator,threshold, type, recipient, text, checkpathid"
			+ " from alert" +
			" where alertid = ? ORDER BY alertname, alertid, userid, endpointid, property, operator,threshold, type, recipient, text, checkpathid;");
		
	

	
 
  public VoltTable[] run(
		  String alertID)
      throws VoltAbortException {

          voltQueueSQL( alertsForEndpoint, alertID );
          return voltExecuteSQL();

      }
}