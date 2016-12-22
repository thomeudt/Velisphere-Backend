import org.voltdb.*;

public class UI_SelectSensePropertiesForEndpoint extends VoltProcedure {

	
	public final SQLStmt propsForEndpoint = new SQLStmt(" select "
			+ "endpoint.endpointid, endpoint.endpointname, endpoint.endpointclassid, endpoint.endpointprovdate, endpoint.endpointstate, endpoint.secret, "
			+ "property.propertyid, property.propertyname, property.propertyclassid, property.endpointclassid, property.act, property.sense, property.status, property.configurable, "
			+ "endpointclass.endpointclassid, endpointclass.endpointclassname, endpointclass.endpointclassimageurl, endpointclass.vendorid "
			+ "from endpoint, property, endpointclass " +
			"where endpoint.endpointclassid = property.endpointclassid " +
			"and endpointclass.endpointclassid = property.endpointclassid " +
			"and endpoint.endpointid = ? " +
			"and property.sense = 1 " +
			"ORDER BY endpoint.endpointname, "
			+ "property.propertyname, endpoint.endpointid, endpoint.endpointclassid, endpoint.endpointprovdate, endpoint.endpointstate, endpoint.secret, "
			+ "property.propertyid, property.propertyclassid, property.endpointclassid, property.act, property.sense, property.status, property.configurable, "
			+ "endpointclass.endpointclassid, endpointclass.endpointclassname, endpointclass.endpointclassimageurl, endpointclass.vendorid;");
	

  public VoltTable[] run(
		  String endpointID)
      throws VoltAbortException {

          voltQueueSQL( propsForEndpoint, endpointID );
          return voltExecuteSQL();
          
      }
}