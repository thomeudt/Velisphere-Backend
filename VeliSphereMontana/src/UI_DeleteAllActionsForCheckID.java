import java.util.ArrayList;
import java.util.Iterator;

import org.voltdb.*;

public class UI_DeleteAllActionsForCheckID extends VoltProcedure {

	
	public final SQLStmt getActionIDs = new SQLStmt(" SELECT ACTIONID FROM ACTION WHERE CHECKID = ? ORDER BY ACTIONID;");
	
	public final SQLStmt deleteAction = new SQLStmt(" DELETE FROM ACTION WHERE " +
			"ACTIONID = ?;");

	public final SQLStmt deleteOutboundPropertyAction = new SQLStmt(" DELETE FROM OUTBOUNDPROPERTYACTION WHERE " +
			"ACTIONID = ?;");


  public VoltTable[] run(
		  String checkID)
      throws VoltAbortException {

          voltQueueSQL( getActionIDs, checkID );
          
          VoltTable[] actionsSet = voltExecuteSQL();
          VoltTable actions = actionsSet[0];
          
          ArrayList<String> actionIds = new ArrayList<String>();
          
          while (actions.advanceRow()) {
        	  actionIds.add(actions.getString("ACTIONID"));
          }
          
          Iterator<String> it = actionIds.iterator();
          
          while (it.hasNext()){
        	  String actionId = it.next();
        	  voltQueueSQL( deleteAction, actionId );
        	  voltQueueSQL( deleteOutboundPropertyAction, actionId );
          }
          
          
          return voltExecuteSQL();
          
      }
}