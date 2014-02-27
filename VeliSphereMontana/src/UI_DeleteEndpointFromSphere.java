import org.voltdb.*;

public class UI_DeleteEndpointFromSphere extends VoltProcedure {

	
	public final SQLStmt deleteEndpointsForSphereID = new SQLStmt(" DELETE FROM ENDPOINT_SPHERE_LINK WHERE " +
			"ENDPOINTID = ? AND SPHEREID = ?;");
		

  public VoltTable[] run(
		  String endpointID,
		  String sphereID)
      throws VoltAbortException {

          voltQueueSQL( deleteEndpointsForSphereID, endpointID, sphereID );
          return voltExecuteSQL();
          
      }
}