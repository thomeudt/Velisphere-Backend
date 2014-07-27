import org.voltdb.*;

public class UI_UpdateEndpointClass extends VoltProcedure {

  public final SQLStmt updateEPC = new SQLStmt(
      " UPDATE ENDPOINTCLASS SET ENDPOINTCLASSNAME = ?, ENDPOINTCLASSIMAGEURL = ? WHERE ENDPOINTCLASSID = ?;" );

  public VoltTable[] run(
		  String endpointClassID,
		  String endpointClassName,
		  String endpointClassImageurl)
      throws VoltAbortException {

          voltQueueSQL( updateEPC, endpointClassName, endpointClassImageurl, endpointClassID );
          return voltExecuteSQL();

      }
}