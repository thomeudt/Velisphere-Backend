import org.voltdb.*;

public class UI_SelectCheckForCheckID extends VoltProcedure {

	
	public final SQLStmt CheckForCheckID = new SQLStmt(" select checkid, name from check " +
			"where checkid = ? order by checkid, name;");
		

  public VoltTable[] run(
		  String checkID)
      throws VoltAbortException {

          voltQueueSQL( CheckForCheckID, checkID );
          return voltExecuteSQL();
          
      }
}