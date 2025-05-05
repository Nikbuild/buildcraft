/*     */ package buildcraft.silicon;
/*     */ 
/*     */ import buildcraft.BuildCraftSilicon;
/*     */ import buildcraft.api.power.ILaserTargetBlock;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.block.BlockBuildCraft;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockLaserTable
/*     */   extends BlockBuildCraft
/*     */   implements ILaserTargetBlock
/*     */ {
/*     */   protected static final int TABLE_MAX = 6;
/*     */   
/*     */   public BlockLaserTable() {
/*  33 */     super(Material.field_151573_f);
/*     */     
/*  35 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  36 */     func_149711_c(10.0F);
/*  37 */     func_149647_a((CreativeTabs)BCCreativeTab.get("main"));
/*  38 */     setPassCount(2);
/*  39 */     setAlphaPass(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149645_b() {
/*  54 */     return SiliconProxy.laserTableModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/*  59 */     if (super.func_149727_a(world, i, j, k, entityplayer, par6, par7, par8, par9)) {
/*  60 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  64 */     if (entityplayer.func_70093_af()) {
/*  65 */       return false;
/*     */     }
/*     */     
/*  68 */     if (!world.field_72995_K) {
/*  69 */       int meta = world.func_72805_g(i, j, k);
/*  70 */       entityplayer.openGui(BuildCraftSilicon.instance, meta, world, i, j, k);
/*     */     } 
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity createTileEntity(World world, int metadata) {
/*  77 */     switch (metadata) {
/*     */       case 0:
/*  79 */         return (TileEntity)new TileAssemblyTable();
/*     */       case 1:
/*  81 */         return (TileEntity)new TileAdvancedCraftingTable();
/*     */       case 2:
/*  83 */         return (TileEntity)new TileIntegrationTable();
/*     */       case 3:
/*  85 */         return (TileEntity)new TileChargingTable();
/*     */       case 4:
/*  87 */         return (TileEntity)new TileProgrammingTable();
/*     */       case 5:
/*  89 */         return (TileEntity)new TileStampingTable();
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_149915_a(World world, int metadata) {
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149692_a(int par1) {
/* 101 */     return par1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_149666_a(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
/* 108 */     for (int i = 0; i < 6; i++) {
/* 109 */       par3List.add(new ItemStack((Block)this, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public String[] getIconBlockNames() {
/* 116 */     return new String[] { "BuildCraft|Silicon:assemblyTable", "BuildCraft|Silicon:advancedCraftingTable", "BuildCraft|Silicon:integrationTable", "BuildCraft|Silicon:chargingTable", "BuildCraft|Silicon:programmingTable", "BuildCraft|Silicon:stampingTable" };
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\BlockLaserTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */