import org.voltdb.*;

public class UI_SelectVendorForVendorID extends VoltProcedure {

  public final SQLStmt selectVendor = new SQLStmt(
      " SELECT VENDORID, VENDORNAME, VENDORIMAGEURL FROM VENDOR WHERE VENDORID = ? ORDER BY VENDORNAME;" );

  public VoltTable[] run(
		  String vendorID)
      throws VoltAbortException {

          voltQueueSQL( selectVendor, vendorID );
          return voltExecuteSQL();

      }
}