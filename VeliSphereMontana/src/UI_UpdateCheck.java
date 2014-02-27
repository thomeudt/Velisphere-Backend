import org.voltdb.*;

public class UI_UpdateCheck extends VoltProcedure {

  public final SQLStmt updateCheck = new SQLStmt(
      " UPDATE CHECK SET NAME = ?, CHECKVALUE = ?, OPERATOR = ?, STATE = 0 WHERE CHECKID = ?;" );

  public VoltTable[] run(
		  String checkID,
		  String name,
		  String checkValue,
		  String operator)
      throws VoltAbortException {

          voltQueueSQL( updateCheck, name, checkValue, operator,checkID );
          return voltExecuteSQL();

      }
}