import org.voltdb.*;

public class UI_SelectAllProperties extends VoltProcedure {

  public final SQLStmt selectAllProperties = new SQLStmt(
      " SELECT PROPERTYID, PROPERTYNAME, PROPERTYCLASSID, ENDPOINTCLASSID, ACT, SENSE, STATUS, CONFIGURABLE FROM PROPERTY "
      + "ORDER BY PROPERTYNAME, PROPERTYID, PROPERTYCLASSID, ENDPOINTCLASSID, ACT, SENSE, STATUS, CONFIGURABLE;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllProperties );
          return voltExecuteSQL();

      }
}