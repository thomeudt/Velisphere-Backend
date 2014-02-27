import org.voltdb.*;

public class UI_SelectPropertyClassForPropertyClassID extends VoltProcedure {

	
	public final SQLStmt PropertyClassForPropertyClassID = new SQLStmt(" select propertyclassid, propertyclassdatatype, propertyclassname, propertyclassunit from propertyclass " +
			"where propertyclassid = ?;");
		

  public VoltTable[] run(
		  String propertyClassID)
      throws VoltAbortException {

          voltQueueSQL( PropertyClassForPropertyClassID, propertyClassID );
          return voltExecuteSQL();
          
      }
}