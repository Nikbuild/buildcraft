/*    */ package buildcraft.core;
/*    */ 
/*    */ import buildcraft.api.tiles.IDebuggable;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemDebugger
/*    */   extends ItemBuildCraft
/*    */ {
/*    */   public ItemDebugger() {
/* 22 */     func_77664_n();
/* 23 */     func_77625_d(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 28 */     if (world.field_72995_K) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     TileEntity tile = world.func_147438_o(x, y, z);
/* 33 */     if (tile instanceof IDebuggable) {
/* 34 */       ArrayList<String> info = new ArrayList<String>();
/* 35 */       ((IDebuggable)tile).getDebugInfo(info, ForgeDirection.getOrientation(side), stack, player);
/* 36 */       for (String s : info) {
/* 37 */         player.func_146105_b((IChatComponent)new ChatComponentText(s));
/*    */       }
/* 39 */       return true;
/*    */     } 
/* 41 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean debug) {
/* 51 */     list.add(StringUtils.localize("item.debugger.warning"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\ItemDebugger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */