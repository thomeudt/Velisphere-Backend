import org.voltdb.*;

public class UI_SelectChecksForEndpoint extends VoltProcedure {

	
	public final SQLStmt checksForEndpoint = 
			new SQLStmt(" select checkid, endpointid, propertyid, checkvalue, operator, state, expired, name, checkpathid from check join " +
			"property on check.propertyid = property.propertyid " +
			"and check.endpointid = ? ORDER BY name, checkid, endpointid, propertyid, checkvalue, operator, state, expired, checkpathid;");
		

  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( checksForEndpoint, endpointID );
          return voltExecuteSQL();
          
      }
}