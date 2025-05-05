/*    */ package buildcraft.builders.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicBlock;
/*    */ import java.util.LinkedList;
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
/*    */ 
/*    */ public class SchematicGlassPane
/*    */   extends SchematicBlock
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 22 */     requirements.add(new ItemStack(this.block, 1, this.meta));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAlreadyBuilt(IBuilderContext context, int x, int y, int z) {
/* 32 */     return (this.block == context.world().func_147439_a(x, y, z));
/*    */   }
/*    */ 
/*    */   
/*    */   public Schematic.BuildingStage getBuildStage() {
/* 37 */     return Schematic.BuildingStage.STANDALONE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\schematics\SchematicGlassPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */