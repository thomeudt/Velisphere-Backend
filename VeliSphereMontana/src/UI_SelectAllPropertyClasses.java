import org.voltdb.*;

public class UI_SelectAllPropertyClasses extends VoltProcedure {

  public final SQLStmt selectAllPropertyClasses = new SQLStmt(
      " SELECT propertyclassid, propertyclassdatatype, propertyclassname, propertyclassunit "
      + "FROM PROPERTYCLASS "
      + "ORDER BY PROPERTYCLASSNAME, propertyclassid, propertyclassdatatype, propertyclassunit;" );

  public VoltTable[] run()
      throws VoltAbortException {

          voltQueueSQL( selectAllPropertyClasses );
          return voltExecuteSQL();

      }
}