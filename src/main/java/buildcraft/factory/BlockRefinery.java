/*    */ package buildcraft.factory;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.BuildCraftFactory;
/*    */ import buildcraft.api.tools.IToolWrench;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.block.BlockBuildCraft;
/*    */ import buildcraft.core.lib.fluids.TankUtils;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockRefinery
/*    */   extends BlockBuildCraft
/*    */ {
/*    */   public BlockRefinery() {
/* 32 */     super(Material.field_151573_f);
/*    */     
/* 34 */     func_149711_c(5.0F);
/* 35 */     func_149647_a((CreativeTabs)BCCreativeTab.get("main"));
/* 36 */     setRotatable(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149662_c() {
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149686_d() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_149645_b() {
/* 51 */     return BuildCraftCore.blockByEntityModel;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity func_149915_a(World world, int metadata) {
/* 56 */     return (TileEntity)new TileRefinery();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_149727_a(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
/* 61 */     if (super.func_149727_a(world, x, y, z, player, side, hitX, hitY, hitZ)) {
/* 62 */       return true;
/*    */     }
/*    */     
/* 65 */     TileEntity tile = world.func_147438_o(x, y, z);
/*    */     
/* 67 */     if (!(tile instanceof TileRefinery)) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     ItemStack current = player.func_71045_bC();
/* 72 */     Item equipped = (current != null) ? current.func_77973_b() : null;
/* 73 */     if (player.func_70093_af() && equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(player, x, y, z)) {
/* 74 */       ((TileRefinery)tile).resetFilters();
/* 75 */       ((IToolWrench)equipped).wrenchUsed(player, x, y, z);
/* 76 */       return true;
/*    */     } 
/*    */     
/* 79 */     if (current != null && current.func_77973_b() != Items.field_151133_ar) {
/* 80 */       if (!world.field_72995_K) {
/* 81 */         if (TankUtils.handleRightClick((TileRefinery)tile, ForgeDirection.getOrientation(side), player, true, false)) {
/* 82 */           return true;
/*    */         }
/* 84 */       } else if (FluidContainerRegistry.isContainer(current)) {
/* 85 */         return true;
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 90 */     if (!world.field_72995_K) {
/* 91 */       player.openGui(BuildCraftFactory.instance, 31, world, x, y, z);
/*    */     }
/*    */     
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\BlockRefinery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */