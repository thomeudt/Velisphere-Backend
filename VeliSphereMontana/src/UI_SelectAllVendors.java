import org.voltdb.*;

public class UI_SelectAllVendors extends VoltProcedure {

  public final SQLStmt selectAllVendors = new SQLStmt(
      " SELECT * FROM VENDOR ORDER BY VENDORNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllVendors );
          return voltExecuteSQL();

      }
}