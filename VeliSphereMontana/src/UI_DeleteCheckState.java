import org.voltdb.*;

public class UI_DeleteCheckState extends VoltProcedure {

	
	public final SQLStmt deleteCheck = new SQLStmt(" DELETE FROM CHECKSTATE WHERE " +
			"CHECKID = ?;");
		

  public VoltTable[] run(
		  String checkID)
      throws VoltAbortException {

          voltQueueSQL( deleteCheck, checkID );
          return voltExecuteSQL();
          
      }
}