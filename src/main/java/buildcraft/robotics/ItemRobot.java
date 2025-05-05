/*     */ package buildcraft.robotics;
/*     */ 
/*     */ import buildcraft.BuildCraftRobotics;
/*     */ import buildcraft.api.boards.RedstoneBoardNBT;
/*     */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*     */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*     */ import buildcraft.api.events.RobotEvent;
/*     */ import buildcraft.api.robots.DockingStation;
/*     */ import buildcraft.core.BCCreativeTab;
/*     */ import buildcraft.core.lib.items.ItemBuildCraft;
/*     */ import buildcraft.core.lib.utils.NBTUtils;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.transport.BlockGenericPipe;
/*     */ import buildcraft.transport.Pipe;
/*     */ import cofh.api.energy.IEnergyContainerItem;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemRobot
/*     */   extends ItemBuildCraft
/*     */   implements IEnergyContainerItem
/*     */ {
/*     */   public ItemRobot() {
/*  46 */     super((CreativeTabs)BCCreativeTab.get("boards"));
/*  47 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/*  52 */     NBTTagCompound cpt = getNBT(stack);
/*  53 */     RedstoneBoardRobotNBT boardNBT = getRobotNBT(cpt);
/*     */     
/*  55 */     if (boardNBT != RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/*  56 */       return 1;
/*     */     }
/*  58 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityRobot createRobot(ItemStack stack, World world) {
/*     */     try {
/*  64 */       NBTTagCompound nbt = getNBT(stack);
/*     */       
/*  66 */       RedstoneBoardRobotNBT robotNBT = getRobotNBT(nbt);
/*  67 */       if (robotNBT == RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/*  68 */         return null;
/*     */       }
/*  70 */       EntityRobot robot = new EntityRobot(world, robotNBT);
/*  71 */       robot.getBattery().setEnergy(getEnergy(nbt));
/*     */       
/*  73 */       return robot;
/*  74 */     } catch (Throwable e) {
/*  75 */       e.printStackTrace();
/*  76 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static RedstoneBoardRobotNBT getRobotNBT(ItemStack stack) {
/*  81 */     return getRobotNBT(getNBT(stack));
/*     */   }
/*     */   
/*     */   public static int getEnergy(ItemStack stack) {
/*  85 */     return getEnergy(getNBT(stack));
/*     */   }
/*     */   
/*     */   public ResourceLocation getTextureRobot(ItemStack stack) {
/*  89 */     return getRobotNBT(stack).getRobotTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced) {
/*  94 */     NBTTagCompound cpt = getNBT(stack);
/*  95 */     RedstoneBoardRobotNBT boardNBT = getRobotNBT(cpt);
/*     */     
/*  97 */     if (boardNBT != RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/*  98 */       boardNBT.addInformation(stack, player, list, advanced);
/*     */       
/* 100 */       int energy = getEnergy(cpt);
/* 101 */       int pct = energy * 100 / 100000;
/* 102 */       String enInfo = pct + "% " + StringUtils.localize("tip.gate.charged");
/* 103 */       if (energy == 100000) {
/* 104 */         enInfo = StringUtils.localize("tip.gate.fullcharge");
/* 105 */       } else if (energy == 0) {
/* 106 */         enInfo = StringUtils.localize("tip.gate.nocharge");
/*     */       } 
/* 108 */       enInfo = ((pct >= 80) ? (String)EnumChatFormatting.GREEN : ((pct >= 50) ? (String)EnumChatFormatting.YELLOW : ((pct >= 30) ? (String)EnumChatFormatting.GOLD : ((pct >= 20) ? (String)EnumChatFormatting.RED : (String)EnumChatFormatting.DARK_RED)))) + enInfo;
/* 109 */       list.add(enInfo);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_94581_a(IIconRegister par1IconRegister) {}
/*     */ 
/*     */   
/*     */   public static ItemStack createRobotStack(RedstoneBoardRobotNBT board, int energy) {
/* 119 */     ItemStack robot = new ItemStack(BuildCraftRobotics.robotItem);
/* 120 */     NBTTagCompound boardCpt = new NBTTagCompound();
/* 121 */     board.createBoard(boardCpt);
/* 122 */     NBTUtils.getItemData(robot).func_74782_a("board", (NBTBase)boardCpt);
/* 123 */     NBTUtils.getItemData(robot).func_74768_a("energy", energy);
/* 124 */     return robot;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_150895_a(Item item, CreativeTabs par2CreativeTabs, List<ItemStack> itemList) {
/* 131 */     itemList.add(createRobotStack(RedstoneBoardRegistry.instance.getEmptyRobotBoard(), 0));
/*     */     
/* 133 */     for (RedstoneBoardNBT boardNBT : RedstoneBoardRegistry.instance.getAllBoardNBTs()) {
/* 134 */       if (boardNBT instanceof RedstoneBoardRobotNBT) {
/* 135 */         RedstoneBoardRobotNBT robotNBT = (RedstoneBoardRobotNBT)boardNBT;
/* 136 */         itemList.add(createRobotStack(robotNBT, 0));
/* 137 */         itemList.add(createRobotStack(robotNBT, 100000));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
/* 144 */     NBTTagCompound cpt = getNBT(container);
/* 145 */     if (getRobotNBT(cpt) == RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/* 146 */       return 0;
/*     */     }
/* 148 */     int currentEnergy = getEnergy(cpt);
/* 149 */     int energyReceived = Math.min(100000 - currentEnergy, maxReceive);
/* 150 */     if (!simulate) {
/* 151 */       setEnergy(cpt, currentEnergy + energyReceived);
/*     */     }
/* 153 */     return energyReceived;
/*     */   }
/*     */ 
/*     */   
/*     */   public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
/* 158 */     NBTTagCompound cpt = getNBT(container);
/* 159 */     if (getRobotNBT(cpt) == RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/* 160 */       return 0;
/*     */     }
/* 162 */     int currentEnergy = getEnergy(cpt);
/* 163 */     int energyExtracted = Math.min(currentEnergy, maxExtract);
/* 164 */     if (!simulate) {
/* 165 */       setEnergy(cpt, currentEnergy - energyExtracted);
/*     */     }
/* 167 */     return energyExtracted;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyStored(ItemStack container) {
/* 172 */     return getEnergy(container);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergyStored(ItemStack container) {
/* 177 */     if (getRobotNBT(container) == RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/* 178 */       return 0;
/*     */     }
/* 180 */     return 100000;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack currentItem, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/* 185 */     if (!world.field_72995_K) {
/* 186 */       Block b = world.func_147439_a(x, y, z);
/* 187 */       if (!(b instanceof BlockGenericPipe)) {
/* 188 */         return false;
/*     */       }
/*     */       
/* 191 */       Pipe<?> pipe = BlockGenericPipe.getPipe((IBlockAccess)world, x, y, z);
/* 192 */       if (pipe == null) {
/* 193 */         return false;
/*     */       }
/*     */       
/* 196 */       BlockGenericPipe pipeBlock = (BlockGenericPipe)b;
/* 197 */       BlockGenericPipe.RaytraceResult rayTraceResult = pipeBlock.doRayTrace(world, x, y, z, player);
/*     */       
/* 199 */       if (rayTraceResult != null && rayTraceResult.hitPart == BlockGenericPipe.Part.Pluggable && pipe.container
/* 200 */         .getPipePluggable(rayTraceResult.sideHit) instanceof RobotStationPluggable) {
/* 201 */         RobotStationPluggable pluggable = (RobotStationPluggable)pipe.container.getPipePluggable(rayTraceResult.sideHit);
/* 202 */         DockingStation station = pluggable.getStation();
/*     */         
/* 204 */         if (!station.isTaken()) {
/* 205 */           RedstoneBoardRobotNBT robotNBT = getRobotNBT(currentItem);
/* 206 */           if (robotNBT == RedstoneBoardRegistry.instance.getEmptyRobotBoard()) {
/* 207 */             return true;
/*     */           }
/*     */ 
/*     */           
/* 211 */           EntityRobot robot = ((ItemRobot)currentItem.func_77973_b()).createRobot(currentItem, world);
/*     */           
/* 213 */           RobotEvent.Place robotEvent = new RobotEvent.Place(robot, player);
/* 214 */           MinecraftForge.EVENT_BUS.post((Event)robotEvent);
/* 215 */           if (robotEvent.isCanceled()) {
/* 216 */             return true;
/*     */           }
/*     */           
/* 219 */           if (robot != null && robot.getRegistry() != null) {
/* 220 */             robot.setUniqueRobotId(robot.getRegistry().getNextRobotId());
/*     */             
/* 222 */             float px = x + 0.5F + rayTraceResult.sideHit.offsetX * 0.5F;
/* 223 */             float py = y + 0.5F + rayTraceResult.sideHit.offsetY * 0.5F;
/* 224 */             float pz = z + 0.5F + rayTraceResult.sideHit.offsetZ * 0.5F;
/*     */             
/* 226 */             robot.func_70107_b(px, py, pz);
/* 227 */             station.takeAsMain(robot);
/* 228 */             robot.dock(robot.getLinkedStation());
/* 229 */             world.func_72838_d((Entity)robot);
/*     */             
/* 231 */             if (!player.field_71075_bZ.field_75098_d) {
/* 232 */               (player.func_71045_bC()).field_77994_a--;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 237 */         return true;
/*     */       } 
/*     */     } 
/* 240 */     return false;
/*     */   }
/*     */   
/*     */   private static NBTTagCompound getNBT(ItemStack stack) {
/* 244 */     NBTTagCompound cpt = NBTUtils.getItemData(stack);
/* 245 */     if (!cpt.func_74764_b("board")) {
/* 246 */       RedstoneBoardRegistry.instance.getEmptyRobotBoard().createBoard(cpt);
/*     */     }
/* 248 */     return cpt;
/*     */   }
/*     */   
/*     */   private static RedstoneBoardRobotNBT getRobotNBT(NBTTagCompound cpt) {
/* 252 */     NBTTagCompound boardCpt = cpt.func_74775_l("board");
/* 253 */     return (RedstoneBoardRobotNBT)RedstoneBoardRegistry.instance.getRedstoneBoard(boardCpt);
/*     */   }
/*     */   
/*     */   private static int getEnergy(NBTTagCompound cpt) {
/* 257 */     return cpt.func_74762_e("energy");
/*     */   }
/*     */   
/*     */   private static void setEnergy(NBTTagCompound cpt, int energy) {
/* 261 */     cpt.func_74768_a("energy", energy);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ItemRobot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */