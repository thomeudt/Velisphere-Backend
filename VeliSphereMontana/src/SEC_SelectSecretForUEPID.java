import org.voltdb.*;

public class SEC_SelectSecretForUEPID extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      " SELECT SECRET FROM UNPROVISIONED_ENDPOINT WHERE UEPID = ? ORDER BY UEPID;" );

  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointID );
          return voltExecuteSQL();

      }
}