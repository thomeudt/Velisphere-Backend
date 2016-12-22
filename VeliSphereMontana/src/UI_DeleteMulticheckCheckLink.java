import org.voltdb.*;

public class UI_DeleteMulticheckCheckLink extends VoltProcedure {

	
	public final SQLStmt deleteCheck = new SQLStmt(" DELETE FROM MULTICHECK_CHECK_LINK WHERE " +
			"LINKID = ?;");
		

  public VoltTable[] run(
		  String multicheckID)
      throws VoltAbortException {

          voltQueueSQL( deleteCheck, multicheckID );
          return voltExecuteSQL();
          
      }
}