import org.voltdb.*;

public class UI_SelectEndpointClassForEndpointClassID extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      " SELECT ENDPOINTCLASSID, ENDPOINTCLASSNAME, ENDPOINTCLASSIMAGEURL, VENDORID "
      + "FROM ENDPOINTCLASS WHERE ENDPOINTCLASSID = ? "
      + "ORDER BY ENDPOINTCLASSNAME, ENDPOINTCLASSID,  ENDPOINTCLASSIMAGEURL, VENDORID;" );

  public VoltTable[] run(
		  String endpointClassID)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointClassID );
          return voltExecuteSQL();

      }
}