import org.voltdb.*;

public class UI_SelectAllEndpoints extends VoltProcedure {

  public final SQLStmt selectAllEndpoints = new SQLStmt(
      " SELECT * FROM ENDPOINT ORDER BY ENDPOINTNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllEndpoints );
          return voltExecuteSQL();

      }
}