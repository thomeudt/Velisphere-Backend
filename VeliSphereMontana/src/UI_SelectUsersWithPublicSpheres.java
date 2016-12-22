import org.voltdb.*;
import org.voltdb.VoltProcedure.VoltAbortException;

public class UI_SelectUsersWithPublicSpheres extends VoltProcedure {

	
	public final SQLStmt findUsersWithPubSpheres = new SQLStmt(" SELECT USERID "
			+ "FROM SPHERE_USER_LINK " +
			"JOIN SPHERE ON SPHERE_USER_LINK.SPHEREID = SPHERE.SPHEREID " +
			"WHERE SPHERE.PUBLIC = 1 ORDER BY USERID;");
		

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( findUsersWithPubSpheres );
          return voltExecuteSQL();
          
      }
}