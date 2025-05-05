/*    */ package buildcraft.factory.schematics;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchematicPump
/*    */   extends SchematicTile
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 23 */     requirements.add(new ItemStack((Block)BuildCraftFactory.pumpBlock));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 33 */     super.initializeFromObjectAt(context, x, y, z);
/*    */     
/* 35 */     this.tileNBT.func_82580_o("tank");
/* 36 */     this.tileNBT.func_82580_o("mjStored");
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 41 */     context.world().func_147465_d(x, y, z, this.block, 0, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public Schematic.BuildingStage getBuildStage() {
/* 46 */     return Schematic.BuildingStage.STANDALONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\schematics\SchematicPump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */