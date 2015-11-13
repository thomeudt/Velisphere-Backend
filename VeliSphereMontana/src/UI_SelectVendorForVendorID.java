import org.voltdb.*;

public class UI_SelectVendorForVendorID extends VoltProcedure {

  public final SQLStmt selectVendor = new SQLStmt(
      " SELECT VENDORID, VENDORNAME, VENDORIMAGEURL FROM VENDOR WHERE VENDORID = ? ORDER BY VENDORNAME, VENDORIMAGEURL,VENDORID;" );

  public VoltTable[] run(
		  String vendorID)
      throws VoltAbortException {

          voltQueueSQL( selectVendor, vendorID );
          return voltExecuteSQL();

      }
}