/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.api.transport.IPipe;
/*    */ import buildcraft.api.transport.pluggable.IPipePluggableItem;
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import buildcraft.core.BCCreativeTab;
/*    */ import buildcraft.core.lib.items.ItemBuildCraft;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.creativetab.CreativeTabs;
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
/*    */ public class ItemRobotStation
/*    */   extends ItemBuildCraft
/*    */   implements IPipePluggableItem
/*    */ {
/*    */   public ItemRobotStation() {
/* 29 */     super((CreativeTabs)BCCreativeTab.get("boards"));
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_77667_c(ItemStack itemstack) {
/* 34 */     return "item.PipeRobotStation";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 39 */     return true;
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
/* 51 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public PipePluggable createPipePluggable(IPipe pipe, ForgeDirection side, ItemStack stack) {
/* 56 */     return new RobotStationPluggable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ItemRobotStation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */