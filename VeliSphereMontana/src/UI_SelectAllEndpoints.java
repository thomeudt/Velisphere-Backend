import org.voltdb.*;

public class UI_SelectAllEndpoints extends VoltProcedure {

  public final SQLStmt selectAllEndpoints = new SQLStmt(
      " SELECT ENDPOINTID, ENDPOINTNAME, ENDPOINTCLASSID, ENDPOINTPROVDATE, ENDPOINTSTATE, SECRET FROM ENDPOINT "
      + "ORDER BY ENDPOINTNAME, ENDPOINTID, ENDPOINTCLASSID, ENDPOINTPROVDATE, ENDPOINTSTATE, SECRET;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllEndpoints );
          return voltExecuteSQL();

      }
}