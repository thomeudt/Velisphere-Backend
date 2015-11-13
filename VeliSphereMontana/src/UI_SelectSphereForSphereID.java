import org.voltdb.*;

public class UI_SelectSphereForSphereID extends VoltProcedure {

  public final SQLStmt selectAllSpheres = new SQLStmt(
      " SELECT SPHERENAME, PUBLIC FROM SPHERE WHERE SPHEREID = ? ORDER BY SPHERENAME, PUBLIC;" );

  public VoltTable[] run(
		  String sphereID)
      throws VoltAbortException {

          voltQueueSQL( selectAllSpheres, sphereID );
          return voltExecuteSQL();

      }
}