import org.voltdb.*;

public class SEC_SelectSecretForEndpointID extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      " SELECT SECRET FROM ENDPOINT WHERE ENDPOINTID = ? ORDER BY ENDPOINTNAME;" );

  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointID );
          return voltExecuteSQL();

      }
}