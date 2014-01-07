import org.voltdb.*;

public class UI_DeleteMulticheck extends VoltProcedure {

	
	public final SQLStmt deleteCheck = new SQLStmt(" DELETE FROM MULTICHECK WHERE " +
			"MULTICHECKID = ?;");
		

  public VoltTable[] run(
		  String multicheckID)
      throws VoltAbortException {

          voltQueueSQL( deleteCheck, multicheckID );
          return voltExecuteSQL();
          
      }
}