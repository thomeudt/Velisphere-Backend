import org.voltdb.*;

public class UI_SelectAllAlertsForEndpoint extends VoltProcedure {

	
	public final SQLStmt alertsForEndpoint = new SQLStmt(" select "
			+ "alertid, userid, endpointid, alertname, property, operator,threshold, type, recipient, text, checkpathid from alert" +
			" where endpointid = ? ORDER BY alertname, alertid, userid, endpointid, property, operator,threshold, type, recipient, text, checkpathid;");
		
	

	
 
  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( alertsForEndpoint, endpointID );
          return voltExecuteSQL();

      }
}