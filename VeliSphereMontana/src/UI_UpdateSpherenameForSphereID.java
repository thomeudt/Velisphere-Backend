import org.voltdb.*;

public class UI_UpdateSpherenameForSphereID extends VoltProcedure {

  public final SQLStmt updateSphereName = new SQLStmt(
      " UPDATE SPHERE SET SPHERENAME = ? WHERE SPHEREID = ?;" );

  public VoltTable[] run(
		  String sphereName,
		  String sphereID)
      throws VoltAbortException {

          voltQueueSQL( updateSphereName, sphereID, sphereName );
          return voltExecuteSQL();

      }
}