import org.voltdb.*;

public class UI_UpdateMulticheck extends VoltProcedure {

  public final SQLStmt updateCheck = new SQLStmt(
      " UPDATE MULTICHECK SET MULTICHECKNAME = ?, OPERATOR = ? WHERE MULTICHECKID = ?;" );

  public VoltTable[] run(
		  String multicheckID,
		  String multicheckname,
		  String operator)
      throws VoltAbortException {

          voltQueueSQL( updateCheck, multicheckname, operator, multicheckID );
          return voltExecuteSQL();

      }
}