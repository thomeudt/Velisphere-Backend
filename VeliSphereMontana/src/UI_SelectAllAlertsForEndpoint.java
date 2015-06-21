import org.voltdb.*;

public class UI_SelectAllAlertsForEndpoint extends VoltProcedure {

	
	public final SQLStmt alertsForEndpoint = new SQLStmt(" select * from alert" +
			" where endpointid = ? ORDER BY alertname;");
		
	

	
 
  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( alertsForEndpoint, endpointID );
          return voltExecuteSQL();

      }
}