import org.voltdb.*;

public class UI_UpdateEndpointClass extends VoltProcedure {

  public final SQLStmt updateEPC = new SQLStmt(
      " UPDATE ENDPOINTCLASS SET ENDPOINTCLASSNAME = ?, ENDPOINTCLASSIMAGEURL = ?, VENDORID = ?  WHERE ENDPOINTCLASSID = ?;" );

  public VoltTable[] run(
		  String endpointClassID,
		  String endpointClassName,
		  String endpointClassImageurl, 
		  String vendorID)
      throws VoltAbortException {

          voltQueueSQL( updateEPC, endpointClassName, endpointClassImageurl, vendorID, endpointClassID );
          return voltExecuteSQL();

      }
}