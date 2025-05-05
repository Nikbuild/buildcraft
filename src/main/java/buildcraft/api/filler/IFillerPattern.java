package buildcraft.api.filler;

import buildcraft.api.core.render.ISprite;
import buildcraft.api.statements.IStatement;
import buildcraft.api.statements.IStatementParameter;
import buildcraft.api.statements.containers.IFillerStatementContainer;
import javax.annotation.Nullable;

public interface IFillerPattern extends IStatement {
  @Nullable
  IFilledTemplate createTemplate(IFillerStatementContainer paramIFillerStatementContainer, IStatementParameter[] paramArrayOfIStatementParameter);
  
  IFillerPattern[] getPossible();
  
  ISprite getSprite();
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\filler\IFillerPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */