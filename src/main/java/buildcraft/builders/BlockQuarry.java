/*     */ package buildcraft.builders;
/*     */ 
/*     */ import buildcraft.BuildCraftBuilders;
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.BlockHatched;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
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
/*     */ public class BlockQuarry
/*     */   extends BlockHatched
/*     */ {
/*     */   public BlockQuarry() {
/*  31 */     super(Material.field_151573_f);
/*     */     
/*  33 */     func_149711_c(10.0F);
/*  34 */     func_149752_b(10.0F);
/*  35 */     func_149672_a(field_149788_p);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149689_a(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack stack) {
/*  40 */     super.func_149689_a(world, i, j, k, entityliving, stack);
/*  41 */     if (entityliving instanceof EntityPlayer) {
/*  42 */       TileEntity tile = world.func_147438_o(i, j, k);
/*  43 */       if (tile instanceof TileQuarry) {
/*  44 */         ((TileQuarry)tile).placedBy = (EntityPlayer)entityliving;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/*  51 */     return (TileEntity)new TileQuarry();
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
/*  56 */     if (BuildCraftBuilders.quarryOneTimeUse) {
/*  57 */       return new ArrayList<ItemStack>();
/*     */     }
/*  59 */     return super.getDrops(world, x, y, z, metadata, fortune);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149749_a(World world, int i, int j, int k, Block block, int metadata) {
/*  64 */     if (world.field_72995_K) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     BuildCraftBuilders.frameBlock.removeNeighboringFrames(world, i, j, k);
/*     */     
/*  70 */     super.func_149749_a(world, i, j, k, block, metadata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/*  75 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/*  76 */       return true;
/*     */     }
/*     */     
/*  79 */     TileQuarry tile = (TileQuarry)world.func_147438_o(i, j, k);
/*     */ 
/*     */     
/*  82 */     if (entityplayer.func_70093_af()) {
/*  83 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  87 */     Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/*  88 */     if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(entityplayer, i, j, k)) {
/*     */       
/*  90 */       tile.reinitalize();
/*  91 */       ((IToolWrench)equipped).wrenchUsed(entityplayer, i, j, k);
/*  92 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 106 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockQuarry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */