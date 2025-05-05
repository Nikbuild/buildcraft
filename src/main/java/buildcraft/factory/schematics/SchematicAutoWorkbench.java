/*    */ package buildcraft.factory.schematics;
/*    */ 
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.Schematic;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import buildcraft.api.core.IInvSlot;
/*    */ import buildcraft.api.core.JavaTools;
/*    */ import buildcraft.core.lib.inventory.InventoryIterator;
/*    */ import buildcraft.factory.TileAutoWorkbench;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ public class SchematicAutoWorkbench
/*    */   extends SchematicTile
/*    */ {
/*    */   public void storeRequirements(IBuilderContext context, int x, int y, int z) {
/* 31 */     TileAutoWorkbench autoWb = getTile(context, x, y, z);
/* 32 */     if (autoWb != null) {
/* 33 */       ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();
/* 34 */       rqs.add(new ItemStack((Block)BuildCraftFactory.autoWorkbenchBlock));
/*    */       
/* 36 */       for (IInvSlot slot : InventoryIterator.getIterable((IInventory)autoWb.craftMatrix, ForgeDirection.UP)) {
/* 37 */         ItemStack stack = slot.getStackInSlot();
/* 38 */         if (stack != null) {
/* 39 */           stack = stack.func_77946_l();
/* 40 */           stack.field_77994_a = 1;
/* 41 */           rqs.add(stack);
/*    */         } 
/*    */       } 
/*    */       
/* 45 */       this.storedRequirements = (ItemStack[])JavaTools.concat((Object[])this.storedRequirements, rqs
/* 46 */           .toArray((Object[])new ItemStack[rqs.size()]));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void initializeFromObjectAt(IBuilderContext context, int x, int y, int z) {
/* 52 */     super.initializeFromObjectAt(context, x, y, z);
/*    */     
/* 54 */     this.tileNBT.func_82580_o("Items");
/*    */   }
/*    */ 
/*    */   
/*    */   public void placeInWorld(IBuilderContext context, int x, int y, int z, LinkedList<ItemStack> stacks) {
/* 59 */     super.placeInWorld(context, x, y, z, stacks);
/*    */     
/* 61 */     TileAutoWorkbench autoWb = getTile(context, x, y, z);
/* 62 */     if (autoWb != null) {
/* 63 */       for (IInvSlot slot : InventoryIterator.getIterable((IInventory)autoWb.craftMatrix, ForgeDirection.UP)) {
/* 64 */         ItemStack stack = slot.getStackInSlot();
/* 65 */         if (stack != null) {
/* 66 */           stack.field_77994_a = 1;
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Schematic.BuildingStage getBuildStage() {
/* 74 */     return Schematic.BuildingStage.STANDALONE;
/*    */   }
/*    */   
/*    */   private TileAutoWorkbench getTile(IBuilderContext context, int x, int y, int z) {
/* 78 */     TileEntity tile = context.world().func_147438_o(x, y, z);
/* 79 */     if (tile != null && tile instanceof TileAutoWorkbench) {
/* 80 */       return (TileAutoWorkbench)tile;
/*    */     }
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\schematics\SchematicAutoWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */