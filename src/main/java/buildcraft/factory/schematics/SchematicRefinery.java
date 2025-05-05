/*    */ package buildcraft.factory.schematics;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.block.Block;
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
/*    */ public class SchematicRefinery
/*    */   extends SchematicTile
/*    */ {
/*    */   public void getRequirementsForPlacement(IBuilderContext context, LinkedList<ItemStack> requirements) {
/* 25 */     requirements.add(new ItemStack((Block)BuildCraftFactory.refineryBlock));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void rotateLeft(IBuilderContext context) {
/* 35 */     this.meta = ForgeDirection.values()[this.meta].getRotation(ForgeDirection.UP).ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 40 */     super.initializeFromObjectAt(context, x, y, z);
/*    */     
/* 42 */     this.tileNBT.func_82580_o("tank1");
/* 43 */     this.tileNBT.func_82580_o("tank2");
/* 44 */     this.tileNBT.func_82580_o("result");
/* 45 */     this.tileNBT.func_82580_o("mjStored");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 51 */     this.tileNBT.func_82580_o("tank1");
/* 52 */     this.tileNBT.func_82580_o("tank2");
/* 53 */     this.tileNBT.func_82580_o("result");
/* 54 */     this.tileNBT.func_82580_o("mjStored");
/*    */     
/* 56 */     super.placeInWorld(context, x, y, z, stacks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\schematics\SchematicRefinery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */