/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.block.IComparatorInventory;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.World;
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
/*    */ public class BlockHopper
/*    */   extends BlockBuildCraft
/*    */   implements IComparatorInventory
/*    */ {
/*    */   private static IIcon icon;
/*    */   
/*    */   public BlockHopper() {
/* 33 */     super(Material.field_151573_f);
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 38 */     return (TileEntity)new TileHopper();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149645_b() {
/* 53 */     return BuildCraftCore.blockByEntityModel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 58 */     if (super.func_149727_a(world, x, y, z, entityplayer, par6, par7, par8, par9)) {
/* 59 */       return true;
/*    */     }
/*    */     
/* 62 */     if (entityplayer.func_70093_af()) {
/* 63 */       return false;
/*    */     }
/*    */     
/* 66 */     if (entityplayer.func_71045_bC() != null && 
/* 67 */       entityplayer.func_71045_bC().func_77973_b() instanceof buildcraft.api.transport.IItemPipe) {
/* 68 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 72 */     if (!world.field_72995_K) {
/* 73 */       entityplayer.openGui(BuildCraftFactory.instance, 32, world, x, y, z);
/*    */     }
/*    */     
/* 76 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister par1IconRegister) {
/* 82 */     icon = par1IconRegister.func_94245_a("buildcraftfactory:hopperBlock/bottom");
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIconAbsolute(int par1, int par2) {
/* 88 */     return icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSlotCountComparator(TileEntity tile, int slot, ItemStack stack) {
/* 93 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */