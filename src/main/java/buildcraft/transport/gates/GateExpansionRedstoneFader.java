/*    */ package buildcraft.transport.gates;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.gates.GateExpansionController;
/*    */ import buildcraft.api.gates.IGateExpansion;
/*    */ import buildcraft.api.statements.IActionInternal;
/*    */ import buildcraft.api.statements.ITriggerInternal;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
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
/*    */ public final class GateExpansionRedstoneFader
/*    */   extends GateExpansionBuildcraft
/*    */   implements IGateExpansion
/*    */ {
/* 24 */   public static GateExpansionRedstoneFader INSTANCE = new GateExpansionRedstoneFader();
/*    */   
/*    */   private GateExpansionRedstoneFader() {
/* 27 */     super("fader");
/*    */   }
/*    */ 
/*    */   
/*    */   public GateExpansionController makeController(TileEntity pipeTile) {
/* 32 */     return new GateExpansionControllerRedstoneFader(pipeTile);
/*    */   }
/*    */   
/*    */   private class GateExpansionControllerRedstoneFader
/*    */     extends GateExpansionController {
/*    */     public GateExpansionControllerRedstoneFader(TileEntity pipeTile) {
/* 38 */       super(GateExpansionRedstoneFader.this, pipeTile);
/*    */     }
/*    */ 
/*    */     
/*    */     public void addTriggers(List<ITriggerInternal> list) {
/* 43 */       super.addTriggers(list);
/* 44 */       list.addAll(Arrays.asList(BuildCraftTransport.triggerRedstoneLevel));
/*    */     }
/*    */ 
/*    */     
/*    */     public void addActions(List<IActionInternal> list) {
/* 49 */       super.addActions(list);
/* 50 */       list.addAll(Arrays.asList(BuildCraftTransport.actionRedstoneLevel));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateExpansionRedstoneFader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */