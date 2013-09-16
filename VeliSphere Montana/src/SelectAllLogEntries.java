import org.voltdb.*;

public class SelectAllLogEntries extends VoltProcedure {

  public final SQLStmt SelectAllLogEntries = new SQLStmt(
      " SELECT * FROM LOGQUEUE ORDER BY IDENTIFIER;" );

  public VoltTable[] run()
      throws VoltAbortException {
          voltQueueSQL( SelectAllLogEntries );
          return voltExecuteSQL();
  }
}