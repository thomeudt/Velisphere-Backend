import org.voltdb.*;

public class UI_SelectPropertyDetailsForPropertyID extends VoltProcedure {

	
	public final SQLStmt PropertyDetailsForPropertyID = new SQLStmt(" select * from property " +
			"WHERE propertyid = ? ORDER BY propertyid;");
		

  public VoltTable[] run(
		  String propertyID)
      throws VoltAbortException {

          voltQueueSQL( PropertyDetailsForPropertyID, propertyID );
          return voltExecuteSQL();
          
      }
}