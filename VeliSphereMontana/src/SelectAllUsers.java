import org.voltdb.*;

public class SelectAllUsers extends VoltProcedure {

  public final SQLStmt selectAllUsers = new SQLStmt(
      " SELECT * FROM USER ORDER BY USERNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllUsers );
          return voltExecuteSQL();

      }
}