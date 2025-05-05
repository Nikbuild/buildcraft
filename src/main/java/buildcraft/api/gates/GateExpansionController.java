/*    */ package buildcraft.api.gates;
/*    */ 
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.IStatement;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import java.util.List;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GateExpansionController
/*    */ {
/*    */   public final IGateExpansion type;
/*    */   public final TileEntity pipeTile;
/*    */   
/*    */   public GateExpansionController(IGateExpansion type, TileEntity pipeTile) {
/* 27 */     this.pipeTile = pipeTile;
/* 28 */     this.type = type;
/*    */   }
/*    */   
/*    */   public IGateExpansion getType() {
/* 32 */     return this.type;
/*    */   }
/*    */   
/*    */   public boolean isActive() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(IGate gate) {}
/*    */ 
/*    */   
/*    */   public void startResolution() {}
/*    */   
/*    */   public boolean resolveAction(IStatement action, int count) {
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isTriggerActive(IStatement trigger, IStatementParameter[] parameters) {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public void addTriggers(List<ITriggerInternal> list) {}
/*    */   
/*    */   public void addActions(List<IActionInternal> list) {}
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {}
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {}
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\api\gates\GateExpansionController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */