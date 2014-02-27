import org.voltdb.*;

public class UI_UpdateCheckpathName extends VoltProcedure {

  public final SQLStmt updateCheck = new SQLStmt(
      " UPDATE CHECKPATH SET CHECKPATHNAME = ? WHERE CHECKPATHID = ?;" );

  public VoltTable[] run(
		  String checkpathID,
		  String checkpathName)
      throws VoltAbortException {

          voltQueueSQL( updateCheck, checkpathName, checkpathID );
          return voltExecuteSQL();

      }
}