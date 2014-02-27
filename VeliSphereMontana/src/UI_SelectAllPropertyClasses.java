import org.voltdb.*;

public class UI_SelectAllPropertyClasses extends VoltProcedure {

  public final SQLStmt selectAllPropertyClasses = new SQLStmt(
      " SELECT * FROM PROPERTYCLASS ORDER BY PROPERTYCLASSNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllPropertyClasses );
          return voltExecuteSQL();

      }
}