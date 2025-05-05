package buildcraft.api.statements;

import net.minecraft.util.EnumFacing;

public interface ITriggerInternalSided extends ITrigger {
  boolean isTriggerActive(EnumFacing paramEnumFacing, IStatementContainer paramIStatementContainer, IStatementParameter[] paramArrayOfIStatementParameter);
}


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\statements\ITriggerInternalSided.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */