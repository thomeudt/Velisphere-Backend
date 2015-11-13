import org.voltdb.*;

public class UI_SelectAllEndpointClasses extends VoltProcedure {

  public final SQLStmt selectAllEndpointClasses = new SQLStmt(
      " SELECT ENDPOINTCLASSID, ENDPOINTCLASSNAME, ENDPOINTCLASSIMAGEURL, VENDORID FROM ENDPOINTCLASS "
      + "ORDER BY ENDPOINTCLASSNAME, ENDPOINTCLASSID, ENDPOINTCLASSIMAGEURL, VENDORID;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllEndpointClasses );
          return voltExecuteSQL();

      }
}