import org.voltdb.*;

public class UI_UpdateEndpointnameForEndpointID extends VoltProcedure {

  public final SQLStmt updateEndpointName = new SQLStmt(
      " UPDATE ENDPOINT SET ENDPOINTNAME = ? WHERE ENDPOINTID = ?;" );

  public VoltTable[] run(
		  String endpointName,
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( updateEndpointName, endpointID, endpointName );
          return voltExecuteSQL();

      }
}