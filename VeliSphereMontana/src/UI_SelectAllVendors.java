import org.voltdb.*;

public class UI_SelectAllVendors extends VoltProcedure {

  public final SQLStmt selectAllVendors = new SQLStmt(
      " SELECT VENDORID, VENDORNAME, VENDORIMAGEURL FROM VENDOR ORDER BY VENDORNAME, VENDORIMAGEURL, VENDORID;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllVendors );
          return voltExecuteSQL();

      }
}