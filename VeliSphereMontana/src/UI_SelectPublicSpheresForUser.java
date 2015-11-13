import org.voltdb.*;

public class UI_SelectPublicSpheresForUser extends VoltProcedure {

	
	public final SQLStmt findSpheresForUserID = new SQLStmt(" SELECT SPHEREID, SPHERENAME, PUBLIC " +
			"FROM SPHERE " +
			"JOIN SPHERE_USER_LINK ON SPHERE.SPHEREID = SPHERE_USER_LINK.SPHEREID " +
			"WHERE SPHERE_USER_LINK.USERID = ? AND SPHERE.PUBLIC = 1 ORDER BY SPHERENAME, PUBLIC, SPHEREID;");
		

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( findSpheresForUserID, userID );
          return voltExecuteSQL();
          
      }
}