/*    */ package buildcraft.transport.gates;
/*    */ 
/*    */ import buildcraft.api.gates.GateExpansionController;
/*    */ import buildcraft.api.gates.IGateExpansion;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GateExpansionNote
/*    */   extends GateExpansionBuildcraft
/*    */   implements IGateExpansion
/*    */ {
/* 18 */   public static GateExpansionNote INSTANCE = new GateExpansionNote();
/*    */   
/*    */   private GateExpansionNote() {
/* 21 */     super("note");
/*    */   }
/*    */ 
/*    */   
/*    */   public GateExpansionController makeController(TileEntity pipeTile) {
/* 26 */     return new GateExpansionControllerNote(pipeTile);
/*    */   }
/*    */   
/*    */   private class GateExpansionControllerNote
/*    */     extends GateExpansionController {
/*    */     public GateExpansionControllerNote(TileEntity pipeTile) {
/* 32 */       super(GateExpansionNote.this, pipeTile);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateExpansionNote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */