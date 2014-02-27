import org.voltdb.*;

public class UI_SelectAllEndpointClasses extends VoltProcedure {

  public final SQLStmt selectAllEndpointClasses = new SQLStmt(
      " SELECT * FROM ENDPOINTCLASS ORDER BY ENDPOINTCLASSNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllEndpointClasses );
          return voltExecuteSQL();

      }
}