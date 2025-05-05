/*     */ package buildcraft.core.crops;
/*     */ 
/*     */ import buildcraft.api.crops.ICropHandler;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.IPlantable;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropHandlerPlantable
/*     */   implements ICropHandler
/*     */ {
/*  31 */   private static final Set<Block> FORBIDDEN_BLOCKS = new HashSet<Block>();
/*     */   
/*     */   public static void forbidBlock(Block b) {
/*  34 */     FORBIDDEN_BLOCKS.add(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSeed(ItemStack stack) {
/*  39 */     if (stack.func_77973_b() instanceof IPlantable) {
/*  40 */       return true;
/*     */     }
/*     */     
/*  43 */     if (stack.func_77973_b() instanceof ItemBlock) {
/*  44 */       Block block = ((ItemBlock)stack.func_77973_b()).field_150939_a;
/*  45 */       if (block instanceof IPlantable && !FORBIDDEN_BLOCKS.contains(block)) {
/*  46 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSustainPlant(World world, ItemStack seed, int x, int y, int z) {
/*  55 */     if (seed.func_77973_b() instanceof IPlantable) {
/*  56 */       return (world.func_147439_a(x, y, z).canSustainPlant((IBlockAccess)world, x, y, z, ForgeDirection.UP, (IPlantable)seed
/*  57 */           .func_77973_b()) && world
/*  58 */         .func_147437_c(x, y + 1, z));
/*     */     }
/*  60 */     Block block = world.func_147439_a(x, y, z);
/*  61 */     IPlantable plantable = (IPlantable)((ItemBlock)seed.func_77973_b()).field_150939_a;
/*  62 */     return (block.canSustainPlant((IBlockAccess)world, x, y, z, ForgeDirection.UP, plantable) && block != ((ItemBlock)seed
/*  63 */       .func_77973_b()).field_150939_a && world
/*  64 */       .func_147437_c(x, y + 1, z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean plantCrop(World world, EntityPlayer player, ItemStack seed, int x, int y, int z) {
/*  70 */     return BlockUtils.useItemOnBlock(world, player, seed, x, y, z, ForgeDirection.UP);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMature(IBlockAccess blockAccess, Block block, int meta, int x, int y, int z) {
/*  75 */     if (block == null || FORBIDDEN_BLOCKS.contains(block))
/*  76 */       return false; 
/*  77 */     if (block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockMelon || block instanceof net.minecraft.block.BlockMushroom || block instanceof net.minecraft.block.BlockDoublePlant || block == Blocks.field_150423_aK)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  82 */       return true; } 
/*  83 */     if (block instanceof net.minecraft.block.BlockCrops)
/*  84 */       return (meta == 7); 
/*  85 */     if (block instanceof net.minecraft.block.BlockNetherWart)
/*  86 */       return (meta == 3); 
/*  87 */     if (block instanceof IPlantable && 
/*  88 */       y > 0 && blockAccess.func_147439_a(x, y - 1, z) == block) {
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean harvestCrop(World world, int x, int y, int z, List<ItemStack> drops) {
/*  97 */     if (!world.field_72995_K) {
/*  98 */       Block block = world.func_147439_a(x, y, z);
/*  99 */       int meta = world.func_72805_g(x, y, z);
/* 100 */       if (BlockUtils.breakBlock((WorldServer)world, x, y, z, drops)) {
/* 101 */         world.func_72889_a(null, 2001, x, y, z, Block.func_149682_b(block) + (meta << 12));
/*     */         
/* 103 */         return true;
/*     */       } 
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\crops\CropHandlerPlantable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */