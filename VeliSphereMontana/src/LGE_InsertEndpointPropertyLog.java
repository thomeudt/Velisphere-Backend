import org.voltdb.*;
import org.voltdb.types.TimestampType;

public class LGE_InsertEndpointPropertyLog extends VoltProcedure {

  public final SQLStmt sql = new SQLStmt(
      "INSERT INTO ENDPOINTPROPERTYLOG VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP);"
  );
  
  public final SQLStmt delete = new SQLStmt ("DELETE FROM ENDPOINTPROPERTYLOG WHERE "
  		+ "TIME_STAMP < TO_TIMESTAMP(Second, SINCE_EPOCH(Second,CURRENT_TIMESTAMP) - 127800) AND PROPERTYID = ?;");

  public VoltTable[] run( 	  
				String endpointID,
				String entryID,
				String propertyID,
				String propertyEntry)
      throws VoltAbortException {
	      voltQueueSQL( delete, propertyID );
	      voltQueueSQL( sql, entryID, endpointID, propertyID, propertyEntry);
          voltExecuteSQL();
          return voltExecuteSQL();
      }
}
