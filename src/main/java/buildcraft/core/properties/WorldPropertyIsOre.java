/*    */ package buildcraft.core.properties;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.ForgeHooks;
/*    */ import net.minecraftforge.oredict.OreDictionary;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPropertyIsOre
/*    */   extends WorldProperty
/*    */ {
/* 26 */   private final HashSet<Integer> ores = new HashSet<Integer>();
/*    */   
/*    */   public WorldPropertyIsOre(int harvestLevel) {
/* 29 */     initBlockHarvestTools();
/* 30 */     for (String oreName : OreDictionary.getOreNames()) {
/* 31 */       if (oreName.startsWith("ore")) {
/* 32 */         ArrayList<ItemStack> oreStacks = OreDictionary.getOres(oreName);
/* 33 */         if (oreStacks.size() > 0) {
/* 34 */           Block block = Block.func_149634_a(((ItemStack)oreStacks.get(0)).func_77973_b());
/* 35 */           int meta = ((ItemStack)oreStacks.get(0)).func_77960_j();
/* 36 */           if (meta >= 16 || meta < 0) {
/* 37 */             meta = 0;
/*    */           }
/* 39 */           if (block != null)
/*    */           {
/*    */             
/* 42 */             if ("pickaxe".equals(block.getHarvestTool(meta)) && block
/* 43 */               .getHarvestLevel(meta) <= harvestLevel) {
/* 44 */               this.ores.add(Integer.valueOf(OreDictionary.getOreID(oreName)));
/*    */             }
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void initBlockHarvestTools() {
/* 53 */     ForgeHooks.canToolHarvestBlock(Blocks.field_150365_q, 0, new ItemStack(Items.field_151046_w));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean get(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/* 58 */     if (block == null) {
/* 59 */       return false;
/*    */     }
/* 61 */     List<ItemStack> toCheck = new ArrayList<ItemStack>();
/* 62 */     toCheck.add(new ItemStack(block, 1, meta));
/*    */     
/* 64 */     if (block.hasTileEntity(meta) && blockAccess instanceof World) {
/* 65 */       toCheck.addAll(block.getDrops((World)blockAccess, x, y, z, blockAccess.func_72805_g(x, y, z), 0));
/*    */     }
/*    */     
/* 68 */     for (ItemStack stack : toCheck) {
/* 69 */       if (stack.func_77973_b() != null) {
/* 70 */         for (int id : OreDictionary.getOreIDs(stack)) {
/* 71 */           if (this.ores.contains(Integer.valueOf(id))) {
/* 72 */             return true;
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 79 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\properties\WorldPropertyIsOre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */