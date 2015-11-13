import org.voltdb.*;

public class SelectAllUsers extends VoltProcedure {

  public final SQLStmt selectAllUsers = new SQLStmt(
      " SELECT USERID, USERNAME, USEREMAIL, USERPWHASH, PLANID FROM USER ORDER BY USERNAME, USERID, USEREMAIL, USERPWHASH, PLANID;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllUsers );
          return voltExecuteSQL();

      }
}