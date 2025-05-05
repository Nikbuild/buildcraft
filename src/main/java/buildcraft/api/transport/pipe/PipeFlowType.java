/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public final class PipeFlowType {
/*    */   public final IFlowCreator creator;
/*    */   public final IFlowLoader loader;
/*    */   
/*    */   public PipeFlowType(IFlowCreator creator, IFlowLoader loader) {
/* 10 */     this.creator = creator;
/* 11 */     this.loader = loader;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface IFlowLoader {
/*    */     PipeFlow loadFlow(IPipe param1IPipe, NBTTagCompound param1NBTTagCompound);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface IFlowCreator {
/*    */     PipeFlow createFlow(IPipe param1IPipe);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeFlowType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */