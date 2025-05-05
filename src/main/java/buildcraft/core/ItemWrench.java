/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.tools.IToolWrench;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import buildcraft.core.lib.utils.BlockUtils;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBed;
/*    */ import net.minecraft.block.BlockButton;
/*    */ import net.minecraft.block.BlockChest;
/*    */ import net.minecraft.block.BlockLever;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public class ItemWrench
/*    */   extends ItemBuildCraft
/*    */   implements IToolWrench
/*    */ {
/* 30 */   private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();
/* 31 */   private final Set<Class<? extends Block>> blacklistedRotations = new HashSet<Class<? extends Block>>();
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemWrench() {
/* 36 */     func_77664_n();
/* 37 */     func_77625_d(1);
/* 38 */     this.shiftRotations.add(BlockLever.class);
/* 39 */     this.shiftRotations.add(BlockButton.class);
/* 40 */     this.shiftRotations.add(BlockChest.class);
/* 41 */     this.blacklistedRotations.add(BlockBed.class);
/* 42 */     setHarvestLevel("wrench", 0);
/*    */   }
/*    */   
/*    */   private boolean isClass(Set<Class<? extends Block>> set, Class<? extends Block> cls) {
/* 46 */     for (Class<? extends Block> shift : set) {
/* 47 */       if (shift.isAssignableFrom(cls)) {
/* 48 */         return true;
/*    */       }
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 56 */     Block block = world.func_147439_a(x, y, z);
/*    */     
/* 58 */     if (block == null || isClass(this.blacklistedRotations, (Class)block.getClass())) {
/* 59 */       return false;
/*    */     }
/*    */     
/* 62 */     if (player.func_70093_af() != isClass(this.shiftRotations, (Class)block.getClass())) {
/* 63 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 67 */     if (block instanceof BlockChest && BlockUtils.getOtherDoubleChest(world.func_147438_o(x, y, z)) != null) {
/* 68 */       return false;
/*    */     }
/*    */     
/* 71 */     if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
/* 72 */       player.func_71038_i();
/* 73 */       return !world.field_72995_K;
/*    */     } 
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canWrench(EntityPlayer player, int x, int y, int z) {
/* 80 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
/* 85 */     player.func_71038_i();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ItemWrench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */