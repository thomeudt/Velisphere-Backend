import org.voltdb.*;

public class UI_SelectEndpointClassForEndpointClassID extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      " SELECT ENDPOINTCLASSID, ENDPOINTCLASSNAME, ENDPOINTCLASSIMAGEURL FROM ENDPOINTCLASS WHERE ENDPOINTCLASSID = ? ORDER BY ENDPOINTCLASSNAME;" );

  public VoltTable[] run(
		  String endpointClassID)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointClassID );
          return voltExecuteSQL();

      }
}