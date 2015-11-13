import org.voltdb.*;

public class UI_SelectEndpointsForSphere extends VoltProcedure {

	
	public final SQLStmt findEndpointsForSphereID = new SQLStmt(" SELECT ENDPOINTID, ENDPOINTNAME, ENDPOINTCLASSID, ENDPOINTPROVDATE, ENDPOINTSTATE, SECRET " +
			"FROM ENDPOINT " +
			"JOIN ENDPOINT_SPHERE_LINK ON ENDPOINT.ENDPOINTID = ENDPOINT_SPHERE_LINK.ENDPOINTID " +
			"AND ENDPOINT_SPHERE_LINK.SPHEREID = ?"
			+ "ORDER BY ENDPOINTNAME, ENDPOINTID, ENDPOINTCLASSID, ENDPOINTPROVDATE, ENDPOINTSTATE, SECRET  ;");
		

  public VoltTable[] run(
		  String sphereID)
      throws VoltAbortException {

          voltQueueSQL( findEndpointsForSphereID, sphereID );
          return voltExecuteSQL();
          
      }
}