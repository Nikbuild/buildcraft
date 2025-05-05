/*    */ package buildcraft.api.enums;
/*    */ 
/*    */ import buildcraft.api.properties.BuildCraftProperties;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.IStringSerializable;
/*    */ 
/*    */ public enum EnumMachineState
/*    */   implements IStringSerializable {
/*  9 */   OFF,
/* 10 */   ON,
/* 11 */   DONE;
/*    */   
/*    */   public static EnumMachineState getType(IBlockState state) {
/* 14 */     return (EnumMachineState)state.func_177229_b(BuildCraftProperties.MACHINE_STATE);
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_176610_l() {
/* 19 */     return name();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\enums\EnumMachineState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */