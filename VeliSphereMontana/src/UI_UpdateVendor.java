import org.voltdb.*;

public class UI_UpdateVendor extends VoltProcedure {

  public final SQLStmt updateVendor = new SQLStmt(
      " UPDATE VENDOR SET VENDORNAME = ?, VENDORIMAGEURL = ? WHERE VENDORID = ?;" );

  public VoltTable[] run(
		  String vendorID,
		  String vendorName,
		  String vendorImageurl)
      throws VoltAbortException {

          voltQueueSQL( updateVendor, vendorName, vendorImageurl, vendorID );
          return voltExecuteSQL();

      }
}