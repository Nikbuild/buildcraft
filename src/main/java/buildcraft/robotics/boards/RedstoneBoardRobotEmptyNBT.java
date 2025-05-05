/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.robotics.EntityRobot;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RedstoneBoardRobotEmptyNBT
/*    */   extends RedstoneBoardRobotNBT
/*    */ {
/* 23 */   public static RedstoneBoardRobotEmptyNBT instance = new RedstoneBoardRobotEmptyNBT();
/*    */   
/*    */   private IIcon icon;
/*    */   
/*    */   public RedstoneBoardRobot create(EntityRobotBase robot) {
/* 28 */     return new BoardRobotEmpty(robot);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getRobotTexture() {
/* 33 */     return EntityRobot.ROBOT_BASE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getID() {
/* 38 */     return "buildcraft:boardRobotEmpty";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List<?> list, boolean advanced) {}
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 48 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:board/clean");
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon(NBTTagCompound nbt) {
/* 54 */     return this.icon;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\RedstoneBoardRobotEmptyNBT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */