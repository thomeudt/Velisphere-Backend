import org.voltdb.*;

public class Select extends VoltProcedure {

  public final SQLStmt sql = new SQLStmt(
      "SELECT * FROM LOGQUEUE "
  );

  public VoltTable[] run( String language)
      throws VoltAbortException {
          voltQueueSQL( sql, language );
          return voltExecuteSQL();
      }
}
