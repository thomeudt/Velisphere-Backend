import org.voltdb.*;

public class UI_SelectAllCheckpaths extends VoltProcedure {

  public final SQLStmt selectAllCheckpaths = new SQLStmt(
      " SELECT CHECKPATHID, CHECKPATHNAME FROM CHECKPATH WHERE OWNERID = ? AND UIOBJECT != 'ALERT' ORDER BY CHECKPATHNAME;" );

  public VoltTable[] run(
		  String userID)
      throws VoltAbortException {

          voltQueueSQL( selectAllCheckpaths, userID );
          return voltExecuteSQL();

      }
}