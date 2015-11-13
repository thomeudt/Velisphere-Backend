import org.voltdb.*;

public class UI_SelectAllSpheres extends VoltProcedure {

  public final SQLStmt selectAllSpheres = new SQLStmt(
      " SELECT SPHEREID, SPHERENAME, PUBLIC FROM SPHERE ORDER BY SPHERENAME, SPHEREID, PUBLIC;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllSpheres );
          return voltExecuteSQL();

      }
}