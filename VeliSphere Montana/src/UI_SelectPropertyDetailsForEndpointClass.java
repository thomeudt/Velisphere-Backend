import org.voltdb.*;

public class UI_SelectPropertyDetailsForEndpointClass extends VoltProcedure {

	
	public final SQLStmt PropertyDetailsForEndpointClassID = new SQLStmt(" select * from property join " +
			"propertyclass on property.propertyclassid = propertyclass.propertyclassid" +
			"and property.propertyid = ?;");
		

  public VoltTable[] run(
		  String endpointClassID)
      throws VoltAbortException {

          voltQueueSQL( PropertyDetailsForEndpointClassID, endpointClassID );
          return voltExecuteSQL();
          
      }
}