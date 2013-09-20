import org.voltdb.*;

public class UI_SelectAllSpheres extends VoltProcedure {

  public final SQLStmt selectAllSpheres = new SQLStmt(
      " SELECT * FROM SPHERE ORDER BY SPHERENAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllSpheres );
          return voltExecuteSQL();

      }
}