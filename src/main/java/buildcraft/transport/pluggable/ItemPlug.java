/*    */ package buildcraft.transport.pluggable;
/*    */ 
/*    */ import buildcraft.api.transport.IPipe;
/*    */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemPlug
/*    */   extends ItemBuildCraft
/*    */   implements IPipePluggableItem
/*    */ {
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 33 */     return "item.PipePlug";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int func_94901_k() {
/* 50 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 55 */     return new PlugPluggable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\pluggable\ItemPlug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */