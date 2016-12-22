import org.voltdb.*;

public class UI_UpdateCheckpath extends VoltProcedure {

  public final SQLStmt updateCheck = new SQLStmt(
      " UPDATE CHECKPATH SET UIOBJECT = ? WHERE CHECKPATHID = ?;" );

  public VoltTable[] run(
		  String checkpathID,
		  String uiobject)
      throws VoltAbortException {

          voltQueueSQL( updateCheck, uiobject, checkpathID );
          return voltExecuteSQL();

      }
}