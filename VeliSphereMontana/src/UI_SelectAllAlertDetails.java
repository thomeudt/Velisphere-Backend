import org.voltdb.*;

public class UI_SelectAllAlertDetails extends VoltProcedure {

	
	public final SQLStmt alertsForEndpoint = new SQLStmt(" select * from alert" +
			" where alertid = ? ORDER BY alertname;");
		
	

	
 
  public VoltTable[] run(
		  String alertID)
      throws VoltAbortException {

          voltQueueSQL( alertsForEndpoint, alertID );
          return voltExecuteSQL();

      }
}