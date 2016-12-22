import org.voltdb.*;

public class UI_SelectPropertyNameForPropertyID extends VoltProcedure {

	
	public final SQLStmt PropertyNameForPropertyID = new SQLStmt(" select propertyname from property " +
			"where propertyid = ?;");
		

  public VoltTable[] run(
		  String propertyID)
      throws VoltAbortException {

          voltQueueSQL( PropertyNameForPropertyID, propertyID );
          return voltExecuteSQL();
          
      }
}