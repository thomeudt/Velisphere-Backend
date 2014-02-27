import org.voltdb.*;

public class UI_DeleteMulticheckMulticheckLink extends VoltProcedure {

	
	public final SQLStmt deleteCheck = new SQLStmt(" DELETE FROM MULTICHECK_MULTICHECK_LINK WHERE " +
			"MULTICHECKLID = ?;");
		

  public VoltTable[] run(
		  String multicheckID)
      throws VoltAbortException {

          voltQueueSQL( deleteCheck, multicheckID );
          return voltExecuteSQL();
          
      }
}