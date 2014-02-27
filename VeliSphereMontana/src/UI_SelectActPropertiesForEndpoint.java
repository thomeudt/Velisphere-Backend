import org.voltdb.*;

public class UI_SelectActPropertiesForEndpoint extends VoltProcedure {

	
	public final SQLStmt propsForEndpoint = new SQLStmt(" select * from endpoint, property, endpointclass " +
			"where endpoint.endpointclassid = property.endpointclassid " +
			"and endpointclass.endpointclassid = property.endpointclassid " +
			"and endpoint.endpointid = ? " +
			"and property.act = 1 " +
			"ORDER BY endpoint.endpointname;");
		

  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( propsForEndpoint, endpointID );
          return voltExecuteSQL();
          
      }
}