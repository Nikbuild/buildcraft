/*    */ package buildcraft.transport.gates;
/*    */ 
/*    */ import buildcraft.BuildCraftTransport;
/*    */ import buildcraft.api.gates.GateExpansionController;
/*    */ import buildcraft.api.gates.IGateExpansion;
/*    */ import buildcraft.api.statements.ITriggerInternal;
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
/*    */ public final class GateExpansionLightSensor
/*    */   extends GateExpansionBuildcraft
/*    */   implements IGateExpansion
/*    */ {
/* 22 */   public static GateExpansionLightSensor INSTANCE = new GateExpansionLightSensor();
/*    */   
/*    */   private GateExpansionLightSensor() {
/* 25 */     super("light_sensor");
/*    */   }
/*    */ 
/*    */   
/*    */   public GateExpansionController makeController(TileEntity pipeTile) {
/* 30 */     return new GateExpansionControllerLightSensor(pipeTile);
/*    */   }
/*    */   
/*    */   private class GateExpansionControllerLightSensor
/*    */     extends GateExpansionController {
/*    */     public GateExpansionControllerLightSensor(TileEntity pipeTile) {
/* 36 */       super(GateExpansionLightSensor.this, pipeTile);
/*    */     }
/*    */ 
/*    */     
/*    */     public void addTriggers(List<ITriggerInternal> list) {
/* 41 */       super.addTriggers(list);
/* 42 */       list.add(BuildCraftTransport.triggerLightSensorBright);
/* 43 */       list.add(BuildCraftTransport.triggerLightSensorDark);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\gates\GateExpansionLightSensor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */