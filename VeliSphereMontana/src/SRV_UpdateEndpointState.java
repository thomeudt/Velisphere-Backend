import org.voltdb.*;

public class SRV_UpdateEndpointState extends VoltProcedure {

  public final SQLStmt updateEndpointState = new SQLStmt(
      " UPDATE ENDPOINT SET ENDPOINTSTATE = ? WHERE ENDPOINTID = ?;" );

  public VoltTable[] run(
		  String endpointID,
		  String state)
      throws VoltAbortException {

          voltQueueSQL( updateEndpointState, state, endpointID );
          return voltExecuteSQL();

      }
}