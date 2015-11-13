import org.voltdb.*;

public class UI_SelectAllSpheresForUserID extends VoltProcedure {

  public final SQLStmt selectAllSpheres = new SQLStmt(
      " SELECT SPHEREID, SPHERENAME, PUBLIC FROM SPHERE "
      + "JOIN SPHERE_USER_LINK ON SPHERE.SPHEREID = SPHERE_USER_LINK.SPHEREID "
      + "AND SPHERE_USER_LINK.USERID = ? ORDER BY SPHERENAME, SPHEREID, PUBLIC;" );
     

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( selectAllSpheres, userID );
          return voltExecuteSQL();

      }
}