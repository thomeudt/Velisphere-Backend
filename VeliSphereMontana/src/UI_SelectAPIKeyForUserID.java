import org.voltdb.*;
import org.voltdb.VoltProcedure.VoltAbortException;

public class UI_SelectAPIKeyForUserID extends VoltProcedure {

	
	public final SQLStmt findUserForUserID = new SQLStmt(" SELECT APIKEY FROM USER " +
			"WHERE USERID = ? ORDER BY APIKEY;");
		

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( findUserForUserID, userID );
          return voltExecuteSQL();
          
      }
}