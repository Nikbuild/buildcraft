/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import buildcraft.core.lib.engines.TileEngineBase;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
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
/*    */ public class SchematicEngine
/*    */   extends SchematicTile
/*    */ {
/*    */   public void rotateLeft(IBuilderContext context) {
/* 25 */     int o = this.tileNBT.func_74762_e("orientation");
/*    */     
/* 27 */     o = ForgeDirection.values()[o].getRotation(ForgeDirection.UP).ordinal();
/*    */     
/* 29 */     this.tileNBT.func_74768_a("orientation", o);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 34 */     super.initializeFromObjectAt(context, x, y, z);
/*    */     
/* 36 */     TileEngineBase engine = (TileEngineBase)context.world().func_147438_o(x, y, z);
/*    */     
/* 38 */     this.tileNBT.func_74768_a("orientation", engine.orientation.ordinal());
/* 39 */     this.tileNBT.func_82580_o("progress");
/* 40 */     this.tileNBT.func_82580_o("energy");
/* 41 */     this.tileNBT.func_82580_o("heat");
/* 42 */     this.tileNBT.func_82580_o("tankFuel");
/* 43 */     this.tileNBT.func_82580_o("tankCoolant");
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 48 */     super.placeInWorld(context, x, y, z, stacks);
/*    */     
/* 50 */     TileEngineBase engine = (TileEngineBase)context.world().func_147438_o(x, y, z);
/*    */     
/* 52 */     engine.orientation = ForgeDirection.getOrientation(this.tileNBT.func_74762_e("orientation"));
/* 53 */     engine.sendNetworkUpdate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void postProcessing(IBuilderContext context, int x, int y, int z) {
/* 58 */     TileEngineBase engine = (TileEngineBase)context.world().func_147438_o(x, y, z);
/*    */     
/* 60 */     if (engine != null) {
/* 61 */       engine.orientation = ForgeDirection.getOrientation(this.tileNBT.func_74762_e("orientation"));
/* 62 */       engine.sendNetworkUpdate();
/* 63 */       context.world().func_147471_g(x, y, z);
/* 64 */       context.world().func_147459_d(x, y, z, this.block);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Schematic.BuildingStage getBuildStage() {
/* 70 */     return Schematic.BuildingStage.STANDALONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\SchematicEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */