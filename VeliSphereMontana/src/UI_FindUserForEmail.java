import org.voltdb.*;

public class UI_FindUserForEmail extends VoltProcedure {

  public final SQLStmt findUser = new SQLStmt(
      " SELECT * FROM USER WHERE USEREMAIL = ? ORDER BY USERID;" );

  public VoltTable[] run(
		  String userEmail)
      throws VoltAbortException {

          voltQueueSQL( findUser, userEmail );
          return voltExecuteSQL();

      }
}