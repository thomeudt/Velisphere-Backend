import org.voltdb.*;
import org.voltdb.VoltProcedure.VoltAbortException;

public class UI_SelectUserForUserID extends VoltProcedure {

	
	public final SQLStmt findUserForUserID = new SQLStmt(" SELECT USERID, USERNAME, USEREMAIL "
			+ "FROM USER " +
			"WHERE USERID = ? ORDER BY USERNAME, USERID, USEREMAIL;");
		

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( findUserForUserID, userID );
          return voltExecuteSQL();
          
      }
}