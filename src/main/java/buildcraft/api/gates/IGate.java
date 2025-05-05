package buildcraft.api.gates;

import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.StatementSlot;
import buildcraft.api.statements.containers.ISidedStatementContainer;
import buildcraft.api.transport.pipe.IPipeHolder;
import java.util.List;

public interface IGate extends ISidedStatementContainer {
  IPipeHolder getPipeHolder();
  
  List<IStatement> getTriggers();
  
  List<IStatement> getActions();
  
  List<StatementSlot> getActiveActions();
  
  List<IStatementParameter> getTriggerParameters(int paramInt);
  
  List<IStatementParameter> getActionParameters(int paramInt);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\gates\IGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */