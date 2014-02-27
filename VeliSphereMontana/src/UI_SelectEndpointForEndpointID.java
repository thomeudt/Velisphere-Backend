import org.voltdb.*;

public class UI_SelectEndpointForEndpointID extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      " SELECT ENDPOINTID, ENDPOINTNAME, ENDPOINTCLASSID FROM ENDPOINT WHERE ENDPOINTID = ? ORDER BY ENDPOINTNAME;" );

  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointID );
          return voltExecuteSQL();

      }
}