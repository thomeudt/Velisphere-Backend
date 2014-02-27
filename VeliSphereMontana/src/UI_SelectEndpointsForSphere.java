import org.voltdb.*;

public class UI_SelectEndpointsForSphere extends VoltProcedure {

	
	public final SQLStmt findEndpointsForSphereID = new SQLStmt(" SELECT * " +
			"FROM ENDPOINT " +
			"JOIN ENDPOINT_SPHERE_LINK ON ENDPOINT.ENDPOINTID = ENDPOINT_SPHERE_LINK.ENDPOINTID " +
			"AND ENDPOINT_SPHERE_LINK.SPHEREID = ?;");
		

  public VoltTable[] run(
		  String sphereID)
      throws VoltAbortException {

          voltQueueSQL( findEndpointsForSphereID, sphereID );
          return voltExecuteSQL();
          
      }
}