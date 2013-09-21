import org.voltdb.*;

public class UI_SelectEndpointsForUser extends VoltProcedure {

	
	public final SQLStmt findEndpointsForUserID = new SQLStmt(" SELECT * " +
			"FROM ENDPOINT " +
			"JOIN ENDPOINT_USER_LINK ON ENDPOINT.ENDPOINTID = ENDPOINT_USER_LINK.ENDPOINTID " +
			"AND ENDPOINT_USER_LINK.USERID = ?;");
		

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( findEndpointsForUserID, userID );
          return voltExecuteSQL();
          
      }
}