import org.voltdb.*;

public class UI_SelectUnprovisionedEPforIdentifier extends VoltProcedure {

  public final SQLStmt selectEndpoint = new SQLStmt(
      "select UEPID, IDENTIFIER, ENDPOINTCLASSNAME, ENDPOINTCLASSID, TIME_STAMP from UNPROVISIONED_ENDPOINT "
      + "JOIN ENDPOINTCLASS ON UNPROVISIONED_ENDPOINT.ENDPOINTCLASSID = ENDPOINTCLASS.ENDPOINTCLASSID WHERE IDENTIFIER = ?;" );

  public VoltTable[] run(
		  String endpointIdentifier)
      throws VoltAbortException {

          voltQueueSQL( selectEndpoint, endpointIdentifier );
          return voltExecuteSQL();

      }
}