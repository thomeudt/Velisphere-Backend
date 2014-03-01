import org.voltdb.*;
import org.voltdb.types.TimestampType;

public class LGE_InsertEndpointPropertyLog extends VoltProcedure {

  public final SQLStmt sql = new SQLStmt(
      "INSERT INTO ENDPOINTPROPERTYLOG VALUES (?, ?, ?, ?, ?);"
  );

  
  
  public VoltTable[] run( 	 
			  	String entryID, 
				String endpointID, 
				String propertyID,
				String propertyEntry,
				TimestampType timeStamp)
      throws VoltAbortException {
          voltQueueSQL( sql, entryID, endpointID, propertyID, propertyEntry, timeStamp );
          voltExecuteSQL();
          return voltExecuteSQL();
      }
}
