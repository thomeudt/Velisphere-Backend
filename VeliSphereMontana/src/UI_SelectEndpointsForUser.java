import org.voltdb.*;

public class UI_SelectEndpointsForUser extends VoltProcedure {

	
	public final SQLStmt findEndpointsForUserID = new SQLStmt(" SELECT ENDPOINTID, ENDPOINTNAME, ENDPOINTCLASSID, ENDPOINTPROVDATE, ENDPOINTSTATE, SECRET " +
			"FROM ENDPOINT " +
			"JOIN ENDPOINT_USER_LINK ON ENDPOINT.ENDPOINTID = ENDPOINT_USER_LINK.ENDPOINTID " +
			"AND ENDPOINT_USER_LINK.USERID = ?"
			+ "ORDER BY ENDPOINTNAME, ENDPOINTID, ENDPOINTCLASSID, ENDPOINTPROVDATE, ENDPOINTSTATE, SECRET;");
		

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( findEndpointsForUserID, userID );
          return voltExecuteSQL();
          
      }
}