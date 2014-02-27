import org.voltdb.*;

public class UI_SelectAllCheckpaths extends VoltProcedure {

  public final SQLStmt selectAllCheckpaths = new SQLStmt(
      " SELECT CHECKPATHID, CHECKPATHNAME FROM CHECKPATH ORDER BY CHECKPATHNAME;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllCheckpaths );
          return voltExecuteSQL();

      }
}