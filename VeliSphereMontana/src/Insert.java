import org.voltdb.*;

public class Insert extends VoltProcedure {

  public final SQLStmt sql = new SQLStmt(
      "INSERT INTO LOGQUEUE VALUES (?, ?, ?, ?, ?);"
  );

  public VoltTable[] run( 	String exchangeName, 
			  	String message, 
				String queueName, 
				String routingKey,
				String uuid)
      throws VoltAbortException {
          voltQueueSQL( sql, exchangeName, message, queueName, routingKey, uuid );
          voltExecuteSQL();
          return null;
      }
}
