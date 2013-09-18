import org.voltdb.*;

public class UI_SelectAllProperties extends VoltProcedure {

  public final SQLStmt selectAllProperties = new SQLStmt(
      " SELECT * FROM PROPERTY ORDER BY PROPERTYNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllProperties );
          return voltExecuteSQL();

      }
}