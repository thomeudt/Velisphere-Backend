import org.voltdb.*;

public class UI_SelectPropertyClassForPropertyID extends VoltProcedure {

	
	public final SQLStmt PropertyClassForPropertyID = new SQLStmt(" select propertyclassid from property " +
			"where propertyid = ?;");
		

  public VoltTable[] run(
		  String propertyID)
      throws VoltAbortException {

          voltQueueSQL( PropertyClassForPropertyID, propertyID );
          return voltExecuteSQL();
          
      }
}