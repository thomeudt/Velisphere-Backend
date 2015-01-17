import org.voltdb.*;

public class UI_SelectSpheresForUser extends VoltProcedure {

	
	public final SQLStmt findSpheresForUserID = new SQLStmt(" SELECT * " +
			"FROM SPHERE " +
			"JOIN SPHERE_USER_LINK ON SPHERE.SPHEREID = SPHERE_USER_LINK.SPHEREID " +
			"AND SPHERE_USER_LINK.USERID = ? ORDER BY SPHERENAME;");
		

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( findSpheresForUserID, userID );
          return voltExecuteSQL();
          
      }
}