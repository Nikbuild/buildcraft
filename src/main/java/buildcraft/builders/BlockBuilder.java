/*    */ package buildcraft.builders;
/*    */ 
/*    */ import buildcraft.BuildCraftBuilders;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.fluids.TankUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
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
/*    */ public class BlockBuilder
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon blockTopOn;
/*    */   
/*    */   public BlockBuilder() {
/* 35 */     super(Material.field_151573_f, (CreativeTabs)BCCreativeTab.get("main"));
/* 36 */     func_149711_c(5.0F);
/* 37 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_149651_a(IIconRegister register) {
/* 43 */     super.func_149651_a(register);
/* 44 */     this.blockTopOn = register.func_94245_a("buildcraftbuilders:builderBlock/top_on");
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 49 */     return (TileEntity)new TileBuilder();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {
/* 54 */     if (super.func_149727_a(world, x, y, z, entityplayer, par6, par7, par8, par9)) {
/* 55 */       return true;
/*    */     }
/*    */     
/* 58 */     if (entityplayer.func_70093_af()) {
/* 59 */       return false;
/*    */     }
/*    */     
/* 62 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 63 */     TileBuilder builder = (tile instanceof TileBuilder) ? (TileBuilder)tile : null;
/*    */     
/* 65 */     Item equipped = (entityplayer.func_71045_bC() != null) ? entityplayer.func_71045_bC().func_77973_b() : null;
/* 66 */     if (equipped instanceof ItemConstructionMarker) {
/* 67 */       if (ItemConstructionMarker.linkStarted(entityplayer.func_71045_bC())) {
/* 68 */         ItemConstructionMarker.link(entityplayer.func_71045_bC(), world, x, y, z);
/*    */       }
/*    */       
/* 71 */       return true;
/* 72 */     }  if (builder != null && TankUtils.handleRightClick(builder, ForgeDirection.UNKNOWN, entityplayer, true, false)) {
/* 73 */       return true;
/*    */     }
/* 75 */     if (!world.field_72995_K) {
/* 76 */       entityplayer.openGui(BuildCraftBuilders.instance, 11, world, x, y, z);
/*    */     }
/*    */     
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 85 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
/* 90 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightValue(IBlockAccess world, int x, int y, int z) {
/* 95 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-builders.jar!\buildcraft\builders\BlockBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */