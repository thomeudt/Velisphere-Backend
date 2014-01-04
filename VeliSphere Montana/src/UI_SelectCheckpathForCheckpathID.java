import org.voltdb.*;

public class UI_SelectCheckpathForCheckpathID extends VoltProcedure {

	
	public final SQLStmt CheckpathForCheckpathID = new SQLStmt(" select checkpathid, checkpathname, uiobject from checkpath " +
			"where checkpathid = ?;");
		

  public VoltTable[] run(
		  String checkpathID)
      throws VoltAbortException {

          voltQueueSQL( CheckpathForCheckpathID, checkpathID );
          return voltExecuteSQL();
          
      }
}