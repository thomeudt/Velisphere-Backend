import org.voltdb.*;

public class UI_UpdatePublicStateForSphereID extends VoltProcedure {

  public final SQLStmt updatePublicState = new SQLStmt(
      " UPDATE SPHERE SET PUBLIC = ? WHERE SPHEREID = ?;" );

  public VoltTable[] run(
		  String sphereID,
		  int publicState)
      throws VoltAbortException {

          voltQueueSQL( updatePublicState, publicState, sphereID );
          return voltExecuteSQL();

      }
}