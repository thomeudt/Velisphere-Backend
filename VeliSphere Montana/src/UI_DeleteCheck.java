import org.voltdb.*;

public class UI_DeleteCheck extends VoltProcedure {

	
	public final SQLStmt deleteCheck = new SQLStmt(" DELETE FROM CHECK WHERE " +
			"CHECKID = ?;");
		

  public VoltTable[] run(
		  String checkID)
      throws VoltAbortException {

          voltQueueSQL( deleteCheck, checkID );
          return voltExecuteSQL();
          
      }
}