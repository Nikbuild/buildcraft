/*    */ package buildcraft.robotics.boards;
/*    */ 
/*    */ import buildcraft.api.boards.RedstoneBoardRobot;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import buildcraft.api.robots.EntityRobotBase;
/*    */ import buildcraft.core.DefaultProps;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class BCBoardNBT
/*    */   extends RedstoneBoardRobotNBT
/*    */ {
/* 26 */   public static final Map<String, BCBoardNBT> REGISTRY = new HashMap<String, BCBoardNBT>();
/*    */   
/*    */   private final ResourceLocation texture;
/*    */   
/*    */   private final String id;
/*    */   private final String upperName;
/*    */   
/*    */   public BCBoardNBT(String id, String name, Class<? extends RedstoneBoardRobot> board, String boardType) {
/*    */     Constructor<? extends RedstoneBoardRobot> boardInitLocal;
/* 35 */     this.id = id;
/* 36 */     this.boardType = boardType;
/* 37 */     this.upperName = name.substring(0, 1).toUpperCase() + name.substring(1);
/* 38 */     this.texture = new ResourceLocation(DefaultProps.TEXTURE_PATH_ROBOTS + "/robot_" + name + ".png");
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 43 */       boardInitLocal = board.getConstructor(new Class[] { EntityRobotBase.class });
/* 44 */     } catch (Exception e) {
/* 45 */       e.printStackTrace();
/* 46 */       boardInitLocal = null;
/*    */     } 
/* 48 */     this.boardInit = boardInitLocal;
/*    */     
/* 50 */     REGISTRY.put(name, this);
/*    */   }
/*    */   private final String boardType; private final Constructor<? extends RedstoneBoardRobot> boardInit; @SideOnly(Side.CLIENT)
/*    */   private IIcon icon;
/*    */   public String getID() {
/* 55 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
/* 60 */     list.add(EnumChatFormatting.BOLD + StringUtils.localize("buildcraft.boardRobot" + this.upperName));
/* 61 */     list.add(StringUtils.localize("buildcraft.boardRobot" + this.upperName + ".desc"));
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobot create(EntityRobotBase robot) {
/*    */     try {
/* 67 */       return this.boardInit.newInstance(new Object[] { robot });
/* 68 */     } catch (Exception e) {
/* 69 */       e.printStackTrace();
/* 70 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 77 */     this.icon = iconRegister.func_94245_a("buildcraftrobotics:board/" + this.boardType);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon(NBTTagCompound nbt) {
/* 83 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getRobotTexture() {
/* 88 */     return this.texture;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\boards\BCBoardNBT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */