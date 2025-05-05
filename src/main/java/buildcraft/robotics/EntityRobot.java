/*      */ package buildcraft.robotics;
/*      */ 
/*      */ import buildcraft.BuildCraftCore;
/*      */ import buildcraft.api.boards.RedstoneBoardNBT;
/*      */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*      */ import buildcraft.api.boards.RedstoneBoardRobot;
/*      */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*      */ import buildcraft.api.core.BCLog;
/*      */ import buildcraft.api.core.BlockIndex;
/*      */ import buildcraft.api.core.IZone;
/*      */ import buildcraft.api.events.RobotEvent;
/*      */ import buildcraft.api.robots.AIRobot;
/*      */ import buildcraft.api.robots.DockingStation;
/*      */ import buildcraft.api.robots.EntityRobotBase;
/*      */ import buildcraft.api.robots.IRobotOverlayItem;
/*      */ import buildcraft.api.robots.IRobotRegistry;
/*      */ import buildcraft.api.robots.RobotManager;
/*      */ import buildcraft.api.statements.StatementSlot;
/*      */ import buildcraft.api.tiles.IDebuggable;
/*      */ import buildcraft.core.DefaultProps;
/*      */ import buildcraft.core.ItemWrench;
/*      */ import buildcraft.core.LaserData;
/*      */ import buildcraft.core.lib.RFBattery;
/*      */ import buildcraft.core.lib.network.Packet;
/*      */ import buildcraft.core.lib.network.command.CommandWriter;
/*      */ import buildcraft.core.lib.network.command.ICommandReceiver;
/*      */ import buildcraft.core.lib.network.command.PacketCommand;
/*      */ import buildcraft.core.lib.utils.NetworkUtils;
/*      */ import buildcraft.core.proxy.CoreProxy;
/*      */ import buildcraft.robotics.ai.AIRobotMain;
/*      */ import buildcraft.robotics.ai.AIRobotShutdown;
/*      */ import buildcraft.robotics.statements.ActionRobotWorkInArea;
/*      */ import cofh.api.energy.IEnergyStorage;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.properties.Property;
/*      */ import cpw.mods.fml.common.eventhandler.Event;
/*      */ import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
/*      */ import cpw.mods.fml.relauncher.Side;
/*      */ import cpw.mods.fml.relauncher.SideOnly;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.WeakHashMap;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTUtil;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntityDamageSource;
/*      */ import net.minecraft.util.IIcon;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraftforge.common.ForgeHooks;
/*      */ import net.minecraftforge.common.MinecraftForge;
/*      */ import net.minecraftforge.common.util.ForgeDirection;
/*      */ import net.minecraftforge.event.entity.player.AttackEntityEvent;
/*      */ import net.minecraftforge.fluids.Fluid;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.fluids.FluidTankInfo;
/*      */ import net.minecraftforge.fluids.IFluidHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntityRobot
/*      */   extends EntityRobotBase
/*      */   implements IEntityAdditionalSpawnData, IInventory, IFluidHandler, ICommandReceiver, IDebuggable
/*      */ {
/*  104 */   public static final ResourceLocation ROBOT_BASE = new ResourceLocation(DefaultProps.TEXTURE_PATH_ROBOTS + "/robot_base.png");
/*      */   
/*      */   public static final int MAX_WEARABLES = 8;
/*      */   
/*      */   public static final int TRANSFER_INV_SLOTS = 4;
/*  109 */   private static Set<Integer> blacklistedItemsForUpdate = Sets.newHashSet();
/*      */   
/*  111 */   public LaserData laser = new LaserData();
/*      */   
/*      */   public DockingStation linkedDockingStation;
/*      */   
/*      */   public BlockIndex linkedDockingStationIndex;
/*      */   
/*      */   public ForgeDirection linkedDockingStationSide;
/*      */   
/*      */   public BlockIndex currentDockingStationIndex;
/*      */   public ForgeDirection currentDockingStationSide;
/*      */   public boolean isDocked = false;
/*      */   public RedstoneBoardRobot board;
/*      */   public AIRobotMain mainAI;
/*      */   public ItemStack itemInUse;
/*  125 */   public float itemAngle1 = 0.0F;
/*  126 */   public float itemAngle2 = 0.0F;
/*      */   public boolean itemActive = false;
/*  128 */   public float itemActiveStage = 0.0F;
/*  129 */   public long lastUpdateTime = 0L;
/*      */   
/*      */   private DockingStation currentDockingStation;
/*  132 */   private List<ItemStack> wearables = new ArrayList<ItemStack>();
/*      */   
/*      */   private boolean needsUpdate = false;
/*  135 */   private ItemStack[] inv = new ItemStack[4];
/*      */   private FluidStack tank;
/*  137 */   private int maxFluid = 4000;
/*      */   
/*      */   private ResourceLocation texture;
/*  140 */   private WeakHashMap<Entity, Long> unreachableEntities = new WeakHashMap<Entity, Long>();
/*      */   
/*      */   private NBTTagList stackRequestNBT;
/*      */   
/*  144 */   private RFBattery battery = new RFBattery(100000, 100000, 100);
/*      */   
/*      */   private boolean firstUpdateDone = false;
/*      */   
/*      */   private boolean isActiveClient = false;
/*      */   
/*  150 */   private long robotId = Long.MAX_VALUE;
/*      */   
/*  152 */   private int energySpendPerCycle = 0;
/*  153 */   private int ticksCharging = 0;
/*  154 */   private float energyFX = 0.0F;
/*  155 */   private int steamDx = 0;
/*  156 */   private int steamDy = -1;
/*  157 */   private int steamDz = 0;
/*      */   
/*      */   public EntityRobot(World world, RedstoneBoardRobotNBT boardNBT) {
/*  160 */     this(world);
/*      */     
/*  162 */     this.board = boardNBT.create(this);
/*  163 */     this.field_70180_af.func_75692_b(16, this.board.getNBTHandler().getID());
/*      */     
/*  165 */     if (!world.field_72995_K) {
/*  166 */       this.mainAI = new AIRobotMain(this);
/*  167 */       this.mainAI.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   public EntityRobot(World world) {
/*  172 */     super(world);
/*      */     
/*  174 */     this.field_70159_w = 0.0D;
/*  175 */     this.field_70181_x = 0.0D;
/*  176 */     this.field_70179_y = 0.0D;
/*      */     
/*  178 */     this.field_70158_ak = true;
/*  179 */     this.laser.isVisible = false;
/*  180 */     this.field_70144_Y = 1.0F;
/*      */     
/*  182 */     this.field_70130_N = 0.25F;
/*  183 */     this.field_70131_O = 0.25F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_70088_a() {
/*  188 */     super.func_70088_a();
/*      */     
/*  190 */     setNullBoundingBox();
/*      */     
/*  192 */     this.field_70156_m = false;
/*  193 */     this.field_70145_X = true;
/*  194 */     this.field_70178_ae = true;
/*  195 */     func_110163_bv();
/*      */     
/*  197 */     this.field_70180_af.func_75682_a(12, Float.valueOf(0.0F));
/*  198 */     this.field_70180_af.func_75682_a(13, Float.valueOf(0.0F));
/*  199 */     this.field_70180_af.func_75682_a(14, Float.valueOf(0.0F));
/*  200 */     this.field_70180_af.func_75682_a(15, Byte.valueOf((byte)0));
/*  201 */     this.field_70180_af.func_75682_a(16, "");
/*  202 */     this.field_70180_af.func_75682_a(17, Float.valueOf(0.0F));
/*  203 */     this.field_70180_af.func_75682_a(18, Float.valueOf(0.0F));
/*  204 */     this.field_70180_af.func_75682_a(19, Integer.valueOf(0));
/*  205 */     this.field_70180_af.func_75682_a(20, Byte.valueOf((byte)0));
/*  206 */     this.field_70180_af.func_75682_a(21, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   protected void updateDataClient() {
/*  210 */     this.laser.tail.x = this.field_70180_af.func_111145_d(12);
/*  211 */     this.laser.tail.y = this.field_70180_af.func_111145_d(13);
/*  212 */     this.laser.tail.z = this.field_70180_af.func_111145_d(14);
/*  213 */     this.laser.isVisible = (this.field_70180_af.func_75683_a(15) == 1);
/*      */     
/*  215 */     RedstoneBoardNBT<?> boardNBT = RedstoneBoardRegistry.instance.getRedstoneBoard(this.field_70180_af
/*  216 */         .func_75681_e(16));
/*      */     
/*  218 */     if (boardNBT != null) {
/*  219 */       this.texture = ((RedstoneBoardRobotNBT)boardNBT).getRobotTexture();
/*      */     }
/*      */     
/*  222 */     this.itemAngle1 = this.field_70180_af.func_111145_d(17);
/*  223 */     this.itemAngle2 = this.field_70180_af.func_111145_d(18);
/*  224 */     this.energySpendPerCycle = this.field_70180_af.func_75679_c(19);
/*  225 */     this.isActiveClient = (this.field_70180_af.func_75683_a(20) == 1);
/*  226 */     this.battery.setEnergy(this.field_70180_af.func_75679_c(21));
/*      */   }
/*      */   
/*      */   protected void updateDataServer() {
/*  230 */     this.field_70180_af.func_75692_b(12, Float.valueOf((float)this.laser.tail.x));
/*  231 */     this.field_70180_af.func_75692_b(13, Float.valueOf((float)this.laser.tail.y));
/*  232 */     this.field_70180_af.func_75692_b(14, Float.valueOf((float)this.laser.tail.z));
/*  233 */     this.field_70180_af.func_75692_b(15, Byte.valueOf((byte)(this.laser.isVisible ? 1 : 0)));
/*  234 */     this.field_70180_af.func_75692_b(17, Float.valueOf(this.itemAngle1));
/*  235 */     this.field_70180_af.func_75692_b(18, Float.valueOf(this.itemAngle2));
/*      */   }
/*      */   
/*      */   public boolean isActive() {
/*  239 */     if (this.field_70170_p.field_72995_K) {
/*  240 */       return this.isActiveClient;
/*      */     }
/*  242 */     return (this.mainAI.getActiveAI() instanceof buildcraft.robotics.ai.AIRobotSleep || this.mainAI.getActiveAI() instanceof AIRobotShutdown);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void init() {
/*  247 */     if (this.field_70170_p.field_72995_K) {
/*  248 */       BuildCraftCore.instance.sendToServer((Packet)new PacketCommand(this, "requestInitialization", null));
/*      */     }
/*      */   }
/*      */   
/*      */   public void setLaserDestination(float x, float y, float z) {
/*  253 */     if (x != this.laser.tail.x || y != this.laser.tail.y || z != this.laser.tail.z) {
/*  254 */       this.laser.tail.x = x;
/*  255 */       this.laser.tail.y = y;
/*  256 */       this.laser.tail.z = z;
/*      */       
/*  258 */       this.needsUpdate = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void showLaser() {
/*  263 */     if (!this.laser.isVisible) {
/*  264 */       this.laser.isVisible = true;
/*  265 */       this.needsUpdate = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void hideLaser() {
/*  270 */     if (this.laser.isVisible) {
/*  271 */       this.laser.isVisible = false;
/*  272 */       this.needsUpdate = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void firstUpdate() {
/*  277 */     if (this.stackRequestNBT != null);
/*      */ 
/*      */ 
/*      */     
/*  281 */     if (!this.field_70170_p.field_72995_K) {
/*  282 */       getRegistry().registerRobot(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public String func_70005_c_() {
/*  288 */     return StatCollector.func_74838_a("item.robot.name");
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70030_z() {
/*  293 */     this.field_70170_p.field_72984_F.func_76320_a("bcEntityRobot");
/*  294 */     if (!this.firstUpdateDone) {
/*  295 */       firstUpdate();
/*  296 */       this.firstUpdateDone = true;
/*      */     } 
/*      */     
/*  299 */     if (this.ticksCharging > 0) {
/*  300 */       this.ticksCharging--;
/*      */     }
/*      */     
/*  303 */     if (!this.field_70170_p.field_72995_K) {
/*      */ 
/*      */       
/*  306 */       this.field_70180_af.func_75692_b(20, Byte.valueOf((byte)((isActive() && this.ticksCharging == 0) ? 1 : 0)));
/*  307 */       this.field_70180_af.func_75692_b(21, Integer.valueOf(getEnergy()));
/*      */       
/*  309 */       if (this.needsUpdate) {
/*  310 */         updateDataServer();
/*  311 */         this.needsUpdate = false;
/*      */       } 
/*      */     } 
/*      */     
/*  315 */     if (this.field_70170_p.field_72995_K) {
/*  316 */       updateDataClient();
/*  317 */       updateRotationYaw(60.0F);
/*  318 */       updateEnergyFX();
/*      */     } 
/*      */     
/*  321 */     if (this.currentDockingStation != null) {
/*  322 */       this.field_70159_w = 0.0D;
/*  323 */       this.field_70181_x = 0.0D;
/*  324 */       this.field_70179_y = 0.0D;
/*  325 */       this.field_70165_t = (this.currentDockingStation.x() + 0.5F + (this.currentDockingStation.side()).offsetX * 0.5F);
/*  326 */       this.field_70163_u = (this.currentDockingStation.y() + 0.5F + (this.currentDockingStation.side()).offsetY * 0.5F);
/*  327 */       this.field_70161_v = (this.currentDockingStation.z() + 0.5F + (this.currentDockingStation.side()).offsetZ * 0.5F);
/*      */     } 
/*      */     
/*  330 */     if (!this.field_70170_p.field_72995_K) {
/*  331 */       if (this.linkedDockingStation == null) {
/*  332 */         if (this.linkedDockingStationIndex != null) {
/*  333 */           this.linkedDockingStation = getRegistry().getStation(this.linkedDockingStationIndex.x, this.linkedDockingStationIndex.y, this.linkedDockingStationIndex.z, this.linkedDockingStationSide);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  338 */         if (this.linkedDockingStation == null) {
/*  339 */           shutdown("no docking station");
/*      */         }
/*  341 */         else if (this.linkedDockingStation.robotTaking() != this) {
/*  342 */           if (this.linkedDockingStation.robotIdTaking() == this.robotId) {
/*  343 */             BCLog.logger.warn("A robot entity was not properly unloaded");
/*  344 */             this.linkedDockingStation.invalidateRobotTakingEntity();
/*      */           } 
/*  346 */           if (this.linkedDockingStation.robotTaking() != this) {
/*  347 */             shutdown("wrong docking station");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  353 */       if (this.currentDockingStationIndex != null && this.currentDockingStation == null) {
/*  354 */         this.currentDockingStation = getRegistry().getStation(this.currentDockingStationIndex.x, this.currentDockingStationIndex.y, this.currentDockingStationIndex.z, this.currentDockingStationSide);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  361 */       if (this.field_70163_u < -128.0D) {
/*  362 */         this.field_70128_L = true;
/*      */         
/*  364 */         BCLog.logger.info("Destroying robot " + toString() + " - Fallen into Void");
/*  365 */         getRegistry().killRobot(this);
/*      */       } 
/*      */       
/*  368 */       if (this.linkedDockingStation == null || this.linkedDockingStation.isInitialized()) {
/*  369 */         this.field_70170_p.field_72984_F.func_76320_a("bcRobotAI");
/*  370 */         this.mainAI.cycle();
/*  371 */         this.field_70170_p.field_72984_F.func_76319_b();
/*      */         
/*  373 */         if (this.energySpendPerCycle != this.mainAI.getActiveAI().getEnergyCost()) {
/*  374 */           this.energySpendPerCycle = this.mainAI.getActiveAI().getEnergyCost();
/*  375 */           this.field_70180_af.func_75692_b(19, Integer.valueOf(this.energySpendPerCycle));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  382 */     for (int i = 0; i < this.inv.length; i++) {
/*  383 */       updateItem(this.inv[i], i, false);
/*      */     }
/*      */ 
/*      */     
/*  387 */     updateItem(this.itemInUse, 0, true);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  392 */     super.func_70030_z();
/*  393 */     this.field_70170_p.field_72984_F.func_76319_b();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70626_be() {}
/*      */ 
/*      */   
/*      */   public boolean func_70072_I() {
/*  402 */     return false;
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   private void updateEnergyFX() {
/*  407 */     this.energyFX += this.energySpendPerCycle;
/*      */     
/*  409 */     if (this.energyFX >= (100 << 2 * (Minecraft.func_71410_x()).field_71474_y.field_74362_aa)) {
/*  410 */       this.energyFX = 0.0F;
/*  411 */       spawnEnergyFX();
/*      */     } 
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   private void spawnEnergyFX() {
/*  417 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a(new EntityRobotEnergyParticle(this.field_70170_p, this.field_70165_t + this.steamDx * 0.25D, this.field_70163_u + this.steamDy * 0.25D, this.field_70161_v + this.steamDz * 0.25D, this.steamDx * 0.05D, this.steamDy * 0.05D, this.steamDz * 0.05D, (this.energySpendPerCycle * 0.075F < 1.0F) ? 1.0F : (this.energySpendPerCycle * 0.075F)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB func_70046_E() {
/*  426 */     return AxisAlignedBB.func_72330_a(this.field_70165_t - 0.25D, this.field_70163_u - 0.25D, this.field_70161_v - 0.25D, this.field_70165_t + 0.25D, this.field_70163_u + 0.25D, this.field_70161_v + 0.25D);
/*      */   }
/*      */   
/*      */   public void setNullBoundingBox() {
/*  430 */     this.field_70130_N = 0.0F;
/*  431 */     this.field_70131_O = 0.0F;
/*      */     
/*  433 */     this.field_70121_D.field_72340_a = this.field_70165_t;
/*  434 */     this.field_70121_D.field_72338_b = this.field_70163_u;
/*  435 */     this.field_70121_D.field_72339_c = this.field_70161_v;
/*      */     
/*  437 */     this.field_70121_D.field_72336_d = this.field_70165_t;
/*  438 */     this.field_70121_D.field_72337_e = this.field_70163_u;
/*  439 */     this.field_70121_D.field_72334_f = this.field_70161_v;
/*      */   }
/*      */   
/*      */   private void shutdown(String reason) {
/*  443 */     if (!(this.mainAI.getDelegateAI() instanceof AIRobotShutdown)) {
/*  444 */       BCLog.logger.info("Shutting down robot " + toString() + " - " + reason);
/*  445 */       this.mainAI.startDelegateAI((AIRobot)new AIRobotShutdown(this));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeSpawnData(ByteBuf data) {
/*  451 */     data.writeByte(this.wearables.size());
/*  452 */     for (ItemStack s : this.wearables) {
/*  453 */       NetworkUtils.writeStack(data, s);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void readSpawnData(ByteBuf data) {
/*  459 */     int amount = data.readUnsignedByte();
/*  460 */     while (amount > 0) {
/*  461 */       this.wearables.add(NetworkUtils.readStack(data));
/*  462 */       amount--;
/*      */     } 
/*  464 */     init();
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_70694_bm() {
/*  469 */     return this.itemInUse;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70062_b(int i, ItemStack itemstack) {}
/*      */ 
/*      */   
/*      */   public ItemStack[] func_70035_c() {
/*  478 */     return new ItemStack[0];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70069_a(float par1) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_70064_a(double par1, boolean par3) {}
/*      */ 
/*      */   
/*      */   public void func_70612_e(float par1, float par2) {
/*  491 */     func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_70617_f_() {
/*  496 */     return false;
/*      */   }
/*      */   
/*      */   public ResourceLocation getTexture() {
/*  500 */     return this.texture;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70014_b(NBTTagCompound nbt) {
/*  505 */     super.func_70014_b(nbt);
/*      */     
/*  507 */     if (this.linkedDockingStationIndex != null) {
/*  508 */       NBTTagCompound linkedStationNBT = new NBTTagCompound();
/*  509 */       NBTTagCompound linkedStationIndexNBT = new NBTTagCompound();
/*  510 */       this.linkedDockingStationIndex.writeTo(linkedStationIndexNBT);
/*  511 */       linkedStationNBT.func_74782_a("index", (NBTBase)linkedStationIndexNBT);
/*  512 */       linkedStationNBT.func_74774_a("side", (byte)this.linkedDockingStationSide.ordinal());
/*  513 */       nbt.func_74782_a("linkedStation", (NBTBase)linkedStationNBT);
/*      */     } 
/*      */     
/*  516 */     if (this.currentDockingStationIndex != null) {
/*  517 */       NBTTagCompound currentStationNBT = new NBTTagCompound();
/*  518 */       NBTTagCompound currentStationIndexNBT = new NBTTagCompound();
/*  519 */       this.currentDockingStationIndex.writeTo(currentStationIndexNBT);
/*  520 */       currentStationNBT.func_74782_a("index", (NBTBase)currentStationIndexNBT);
/*  521 */       currentStationNBT.func_74774_a("side", (byte)this.currentDockingStationSide.ordinal());
/*  522 */       nbt.func_74782_a("currentStation", (NBTBase)currentStationNBT);
/*      */     } 
/*      */     
/*  525 */     NBTTagCompound nbtLaser = new NBTTagCompound();
/*  526 */     this.laser.writeToNBT(nbtLaser);
/*  527 */     nbt.func_74782_a("laser", (NBTBase)nbtLaser);
/*      */     
/*  529 */     NBTTagCompound batteryNBT = new NBTTagCompound();
/*  530 */     this.battery.writeToNBT(batteryNBT);
/*  531 */     nbt.func_74782_a("battery", (NBTBase)batteryNBT);
/*      */     
/*  533 */     if (this.itemInUse != null) {
/*  534 */       NBTTagCompound itemNBT = new NBTTagCompound();
/*  535 */       this.itemInUse.func_77955_b(itemNBT);
/*  536 */       nbt.func_74782_a("itemInUse", (NBTBase)itemNBT);
/*  537 */       nbt.func_74757_a("itemActive", this.itemActive);
/*      */     } 
/*      */     
/*  540 */     for (int i = 0; i < this.inv.length; i++) {
/*  541 */       NBTTagCompound stackNbt = new NBTTagCompound();
/*      */       
/*  543 */       if (this.inv[i] != null) {
/*  544 */         nbt.func_74782_a("inv[" + i + "]", (NBTBase)this.inv[i].func_77955_b(stackNbt));
/*      */       }
/*      */     } 
/*      */     
/*  548 */     if (this.wearables.size() > 0) {
/*  549 */       NBTTagList wearableList = new NBTTagList();
/*      */       
/*  551 */       for (ItemStack wearable : this.wearables) {
/*  552 */         NBTTagCompound item = new NBTTagCompound();
/*  553 */         wearable.func_77955_b(item);
/*  554 */         wearableList.func_74742_a((NBTBase)item);
/*      */       } 
/*      */       
/*  557 */       nbt.func_74782_a("wearables", (NBTBase)wearableList);
/*      */     } 
/*      */     
/*  560 */     NBTTagCompound ai = new NBTTagCompound();
/*  561 */     this.mainAI.writeToNBT(ai);
/*  562 */     nbt.func_74782_a("mainAI", (NBTBase)ai);
/*      */     
/*  564 */     if (this.mainAI.getDelegateAI() != this.board) {
/*  565 */       NBTTagCompound boardNBT = new NBTTagCompound();
/*  566 */       this.board.writeToNBT(boardNBT);
/*  567 */       nbt.func_74782_a("board", (NBTBase)boardNBT);
/*      */     } 
/*      */     
/*  570 */     nbt.func_74772_a("robotId", this.robotId);
/*      */     
/*  572 */     if (this.tank != null) {
/*  573 */       NBTTagCompound tankNBT = new NBTTagCompound();
/*      */       
/*  575 */       this.tank.writeToNBT(tankNBT);
/*      */       
/*  577 */       nbt.func_74782_a("tank", (NBTBase)tankNBT);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70037_a(NBTTagCompound nbt) {
/*  583 */     super.func_70037_a(nbt);
/*      */     
/*  585 */     if (nbt.func_74764_b("linkedStation")) {
/*  586 */       NBTTagCompound linkedStationNBT = nbt.func_74775_l("linkedStation");
/*  587 */       this.linkedDockingStationIndex = new BlockIndex(linkedStationNBT.func_74775_l("index"));
/*  588 */       this.linkedDockingStationSide = ForgeDirection.values()[linkedStationNBT.func_74771_c("side")];
/*      */     } 
/*      */     
/*  591 */     if (nbt.func_74764_b("currentStation")) {
/*  592 */       NBTTagCompound currentStationNBT = nbt.func_74775_l("currentStation");
/*  593 */       this.currentDockingStationIndex = new BlockIndex(currentStationNBT.func_74775_l("index"));
/*  594 */       this.currentDockingStationSide = ForgeDirection.values()[currentStationNBT.func_74771_c("side")];
/*      */     } 
/*      */ 
/*      */     
/*  598 */     this.laser.readFromNBT(nbt.func_74775_l("laser"));
/*      */     
/*  600 */     this.battery.readFromNBT(nbt.func_74775_l("battery"));
/*      */     
/*  602 */     this.wearables.clear();
/*  603 */     if (nbt.func_74764_b("wearables")) {
/*  604 */       NBTTagList list = nbt.func_150295_c("wearables", 10);
/*  605 */       for (int j = 0; j < list.func_74745_c(); j++) {
/*  606 */         ItemStack stack = ItemStack.func_77949_a(list.func_150305_b(j));
/*  607 */         if (stack != null) {
/*  608 */           this.wearables.add(stack);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  613 */     if (nbt.func_74764_b("itemInUse")) {
/*  614 */       this.itemInUse = ItemStack.func_77949_a(nbt.func_74775_l("itemInUse"));
/*  615 */       this.itemActive = nbt.func_74767_n("itemActive");
/*      */     } 
/*      */     
/*  618 */     for (int i = 0; i < this.inv.length; i++) {
/*  619 */       this.inv[i] = ItemStack.func_77949_a(nbt.func_74775_l("inv[" + i + "]"));
/*      */     }
/*      */     
/*  622 */     NBTTagCompound ai = nbt.func_74775_l("mainAI");
/*  623 */     this.mainAI = (AIRobotMain)AIRobot.loadAI(ai, this);
/*      */     
/*  625 */     if (nbt.func_74764_b("board")) {
/*  626 */       this.board = (RedstoneBoardRobot)AIRobot.loadAI(nbt.func_74775_l("board"), this);
/*      */     } else {
/*  628 */       this.board = (RedstoneBoardRobot)this.mainAI.getDelegateAI();
/*      */     } 
/*      */     
/*  631 */     if (this.board == null) {
/*  632 */       this.board = RedstoneBoardRegistry.instance.getEmptyRobotBoard().create(this);
/*      */     }
/*      */     
/*  635 */     this.field_70180_af.func_75692_b(16, this.board.getNBTHandler().getID());
/*      */     
/*  637 */     this.stackRequestNBT = nbt.func_150295_c("stackRequests", 10);
/*      */     
/*  639 */     if (nbt.func_74764_b("robotId")) {
/*  640 */       this.robotId = nbt.func_74763_f("robotId");
/*      */     }
/*      */     
/*  643 */     if (nbt.func_74764_b("tank")) {
/*  644 */       this.tank = FluidStack.loadFluidStackFromNBT(nbt.func_74775_l("tank"));
/*      */     } else {
/*  646 */       this.tank = null;
/*      */     } 
/*      */ 
/*      */     
/*  650 */     func_110163_bv();
/*      */   }
/*      */ 
/*      */   
/*      */   public void dock(DockingStation station) {
/*  655 */     this.currentDockingStation = station;
/*      */     
/*  657 */     setSteamDirection(this.currentDockingStation.side.offsetX, this.currentDockingStation.side.offsetY, this.currentDockingStation.side.offsetZ);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     this.currentDockingStationIndex = this.currentDockingStation.index();
/*  663 */     this.currentDockingStationSide = this.currentDockingStation.side();
/*      */   }
/*      */ 
/*      */   
/*      */   public void undock() {
/*  668 */     if (this.currentDockingStation != null) {
/*  669 */       this.currentDockingStation.release(this);
/*  670 */       this.currentDockingStation = null;
/*      */       
/*  672 */       setSteamDirection(0, -1, 0);
/*      */       
/*  674 */       this.currentDockingStationIndex = null;
/*  675 */       this.currentDockingStationSide = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DockingStation getDockingStation() {
/*  681 */     return this.currentDockingStation;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMainStation(DockingStation station) {
/*  686 */     if (this.linkedDockingStation != null && this.linkedDockingStation != station) {
/*  687 */       this.linkedDockingStation.unsafeRelease(this);
/*      */     }
/*      */     
/*  690 */     this.linkedDockingStation = station;
/*  691 */     if (station != null) {
/*  692 */       this.linkedDockingStationIndex = this.linkedDockingStation.index();
/*  693 */       this.linkedDockingStationSide = this.linkedDockingStation.side();
/*      */     } else {
/*  695 */       this.linkedDockingStationIndex = null;
/*  696 */       this.linkedDockingStationSide = ForgeDirection.UNKNOWN;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_71124_b(int var1) {
/*  702 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_70302_i_() {
/*  707 */     return this.inv.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_70301_a(int var1) {
/*  712 */     return this.inv[var1];
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_70298_a(int var1, int var2) {
/*  717 */     ItemStack result = this.inv[var1].func_77979_a(var2);
/*      */     
/*  719 */     if ((this.inv[var1]).field_77994_a == 0) {
/*  720 */       this.inv[var1] = null;
/*      */     }
/*      */     
/*  723 */     updateClientSlot(var1);
/*      */     
/*  725 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_70304_b(int var1) {
/*  730 */     ItemStack stack = this.inv[var1];
/*  731 */     this.inv[var1] = null;
/*  732 */     return stack;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70299_a(int var1, ItemStack var2) {
/*  737 */     this.inv[var1] = var2;
/*      */     
/*  739 */     updateClientSlot(var1);
/*      */   }
/*      */ 
/*      */   
/*      */   public String func_145825_b() {
/*  744 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_145818_k_() {
/*  749 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_70297_j_() {
/*  754 */     return 64;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70296_d() {}
/*      */ 
/*      */   
/*      */   public void updateClientSlot(final int slot) {
/*  762 */     BuildCraftCore.instance.sendToEntity((Packet)new PacketCommand(this, "clientSetInventory", new CommandWriter() {
/*      */             public void write(ByteBuf data) {
/*  764 */               data.writeShort(slot);
/*  765 */               NetworkUtils.writeStack(data, EntityRobot.this.inv[slot]);
/*      */             }
/*      */           }), (Entity)this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_70300_a(EntityPlayer var1) {
/*  772 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70295_k_() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70305_f() {}
/*      */ 
/*      */   
/*      */   public boolean func_94041_b(int var1, ItemStack var2) {
/*  785 */     return (this.inv[var1] == null || (this.inv[var1]
/*  786 */       .func_77969_a(var2) && this.inv[var1].func_77985_e() && (this.inv[var1]).field_77994_a + var2.field_77994_a <= this.inv[var1]
/*  787 */       .func_77973_b().getItemStackLimit(this.inv[var1])));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMoving() {
/*  792 */     return (this.field_70159_w != 0.0D || this.field_70181_x != 0.0D || this.field_70179_y != 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack) {
/*  797 */     this.itemInUse = stack;
/*  798 */     BuildCraftCore.instance.sendToEntity((Packet)new PacketCommand(this, "clientSetItemInUse", new CommandWriter() {
/*      */             public void write(ByteBuf data) {
/*  800 */               NetworkUtils.writeStack(data, EntityRobot.this.itemInUse);
/*      */             }
/*      */           }), (Entity)this);
/*      */   }
/*      */   
/*      */   private void setSteamDirection(final int x, final int y, final int z) {
/*  806 */     if (!this.field_70170_p.field_72995_K) {
/*  807 */       BuildCraftCore.instance.sendToEntity((Packet)new PacketCommand(this, "setSteamDirection", new CommandWriter() {
/*      */               public void write(ByteBuf data) {
/*  809 */                 data.writeInt(x);
/*  810 */                 data.writeShort(y);
/*  811 */                 data.writeInt(z);
/*      */               }
/*      */             }), (Entity)this);
/*      */     } else {
/*  815 */       Vec3 v = Vec3.func_72443_a(x, y, z);
/*  816 */       v = v.func_72432_b();
/*      */       
/*  818 */       this.steamDx = (int)v.field_72450_a;
/*  819 */       this.steamDy = (int)v.field_72448_b;
/*  820 */       this.steamDz = (int)v.field_72449_c;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void receiveCommand(String command, Side side, Object sender, ByteBuf stream) {
/*  826 */     if (side.isClient()) {
/*  827 */       if ("clientSetItemInUse".equals(command)) {
/*  828 */         this.itemInUse = NetworkUtils.readStack(stream);
/*  829 */       } else if ("clientSetInventory".equals(command)) {
/*  830 */         int slot = stream.readUnsignedShort();
/*  831 */         this.inv[slot] = NetworkUtils.readStack(stream);
/*  832 */       } else if ("initialize".equals(command)) {
/*  833 */         this.itemInUse = NetworkUtils.readStack(stream);
/*  834 */         this.itemActive = stream.readBoolean();
/*  835 */       } else if ("setItemActive".equals(command)) {
/*  836 */         this.itemActive = stream.readBoolean();
/*  837 */         this.itemActiveStage = 0.0F;
/*  838 */         this.lastUpdateTime = (new Date()).getTime();
/*      */         
/*  840 */         if (!this.itemActive) {
/*  841 */           setSteamDirection(0, -1, 0);
/*      */         }
/*  843 */       } else if ("setSteamDirection".equals(command)) {
/*  844 */         setSteamDirection(stream.readInt(), stream.readShort(), stream.readInt());
/*  845 */       } else if ("syncWearables".equals(command)) {
/*  846 */         this.wearables.clear();
/*      */         
/*  848 */         int amount = stream.readUnsignedByte();
/*  849 */         while (amount > 0) {
/*  850 */           this.wearables.add(NetworkUtils.readStack(stream));
/*  851 */           amount--;
/*      */         } 
/*      */       } 
/*  854 */     } else if (side.isServer()) {
/*  855 */       EntityPlayer p = (EntityPlayer)sender;
/*  856 */       if ("requestInitialization".equals(command)) {
/*  857 */         BuildCraftCore.instance.sendToPlayer(p, (Packet)new PacketCommand(this, "initialize", new CommandWriter() {
/*      */                 public void write(ByteBuf data) {
/*  859 */                   NetworkUtils.writeStack(data, EntityRobot.this.itemInUse);
/*  860 */                   data.writeBoolean(EntityRobot.this.itemActive);
/*      */                 }
/*      */               }));
/*      */         
/*  864 */         for (int i = 0; i < this.inv.length; i++) {
/*  865 */           final int j = i;
/*  866 */           BuildCraftCore.instance.sendToPlayer(p, (Packet)new PacketCommand(this, "clientSetInventory", new CommandWriter() {
/*      */                   public void write(ByteBuf data) {
/*  868 */                     data.writeShort(j);
/*  869 */                     NetworkUtils.writeStack(data, EntityRobot.this.inv[j]);
/*      */                   }
/*      */                 }));
/*      */         } 
/*      */         
/*  874 */         if (this.currentDockingStation != null) {
/*  875 */           setSteamDirection(this.currentDockingStation.side.offsetX, this.currentDockingStation.side.offsetY, this.currentDockingStation.side.offsetZ);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  880 */           setSteamDirection(0, -1, 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70606_j(float par1) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_70097_a(DamageSource source, float f) {
/*  894 */     Entity src = source.func_76364_f();
/*  895 */     if (src != null && !(src instanceof net.minecraft.entity.item.EntityFallingBlock) && !(src instanceof net.minecraft.entity.monster.IMob) && this.currentDockingStation == null) {
/*  896 */       if (ForgeHooks.onLivingAttack((EntityLivingBase)this, source, f)) {
/*  897 */         return false;
/*      */       }
/*      */       
/*  900 */       if (!this.field_70170_p.field_72995_K) {
/*  901 */         this.field_70737_aN = this.field_70738_aO = 10;
/*      */         
/*  903 */         int mul = 2600;
/*  904 */         for (ItemStack s : this.wearables) {
/*  905 */           if (s.func_77973_b() instanceof ItemArmor) {
/*  906 */             mul = mul * 2 / (2 + ((ItemArmor)s.func_77973_b()).field_77879_b); continue;
/*      */           } 
/*  908 */           mul = (int)(mul * 0.7D);
/*      */         } 
/*      */ 
/*      */         
/*  912 */         int energy = Math.round(f * mul);
/*  913 */         if (this.battery.getEnergyStored() - energy > 0) {
/*  914 */           this.battery.setEnergy(this.battery.getEnergyStored() - energy);
/*  915 */           return true;
/*      */         } 
/*  917 */         onRobotHit(true);
/*      */       } 
/*      */       
/*  920 */       return true;
/*      */     } 
/*  922 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAimYaw() {
/*  927 */     return this.itemAngle1;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getAimPitch() {
/*  932 */     return this.itemAngle2;
/*      */   }
/*      */ 
/*      */   
/*      */   public void aimItemAt(float yaw, float pitch) {
/*  937 */     this.itemAngle1 = yaw;
/*  938 */     this.itemAngle2 = pitch;
/*      */     
/*  940 */     updateDataServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void aimItemAt(int x, int y, int z) {
/*  945 */     int deltaX = x - (int)Math.floor(this.field_70165_t);
/*  946 */     int deltaY = y - (int)Math.floor(this.field_70163_u);
/*  947 */     int deltaZ = z - (int)Math.floor(this.field_70161_v);
/*      */     
/*  949 */     if (deltaX != 0 || deltaZ != 0) {
/*  950 */       this.itemAngle1 = (float)(Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI) + 180.0F;
/*      */     }
/*  952 */     double d3 = MathHelper.func_76133_a((deltaX * deltaX + deltaZ * deltaZ));
/*  953 */     this.itemAngle2 = (float)-(Math.atan2(deltaY, d3) * 180.0D / Math.PI);
/*      */     
/*  955 */     setSteamDirection(deltaX, deltaY, deltaZ);
/*      */     
/*  957 */     updateDataServer();
/*      */   }
/*      */   
/*      */   private void updateRotationYaw(float maxStep) {
/*  961 */     float step = MathHelper.func_76142_g(this.itemAngle1 - this.field_70177_z);
/*      */     
/*  963 */     if (step > maxStep) {
/*  964 */       step = maxStep;
/*      */     }
/*      */     
/*  967 */     if (step < -maxStep) {
/*  968 */       step = -maxStep;
/*      */     }
/*      */     
/*  971 */     this.field_70177_z += step;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float func_110146_f(float targetYaw, float dist) {
/*  976 */     if (this.field_70170_p.field_72995_K) {
/*  977 */       float f2 = MathHelper.func_76142_g(this.field_70177_z - this.field_70761_aq);
/*  978 */       this.field_70761_aq += f2 * 0.5F;
/*  979 */       float f3 = MathHelper.func_76142_g(this.field_70177_z - this.field_70761_aq);
/*  980 */       boolean flag = (f3 < -90.0F || f3 >= 90.0F);
/*      */       
/*  982 */       this.field_70761_aq = this.field_70177_z - f3;
/*      */       
/*  984 */       if (f3 * f3 > 2500.0F) {
/*  985 */         this.field_70761_aq += f3 * 0.2F;
/*      */       }
/*      */       
/*  988 */       float newDist = dist;
/*  989 */       if (flag) {
/*  990 */         newDist *= -1.0F;
/*      */       }
/*      */       
/*  993 */       return newDist;
/*      */     } 
/*  995 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemActive(final boolean isActive) {
/* 1000 */     if (isActive != this.itemActive) {
/* 1001 */       this.itemActive = isActive;
/* 1002 */       BuildCraftCore.instance.sendToEntity((Packet)new PacketCommand(this, "setItemActive", new CommandWriter() {
/*      */               public void write(ByteBuf data) {
/* 1004 */                 data.writeBoolean(isActive);
/*      */               }
/*      */             }), (Entity)this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public RedstoneBoardRobot getBoard() {
/* 1012 */     return this.board;
/*      */   }
/*      */ 
/*      */   
/*      */   public DockingStation getLinkedStation() {
/* 1017 */     return this.linkedDockingStation;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean func_70112_a(double par1) {
/* 1023 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getEnergy() {
/* 1028 */     return this.battery.getEnergyStored();
/*      */   }
/*      */ 
/*      */   
/*      */   public RFBattery getBattery() {
/* 1033 */     return this.battery;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_70692_ba() {
/* 1038 */     return false;
/*      */   }
/*      */   
/*      */   public AIRobot getOverridingAI() {
/* 1042 */     return this.mainAI.getOverridingAI();
/*      */   }
/*      */   
/*      */   public void overrideAI(AIRobot ai) {
/* 1046 */     this.mainAI.setOverridingAI(ai);
/*      */   }
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity par1Entity) {
/* 1050 */     if (MinecraftForge.EVENT_BUS.post((Event)new AttackEntityEvent(CoreProxy.proxy
/* 1051 */           .getBuildCraftPlayer((WorldServer)this.field_70170_p, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v).get(), par1Entity))) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1056 */     if (par1Entity.func_70075_an() && 
/* 1057 */       !par1Entity.func_85031_j((Entity)this)) {
/* 1058 */       Multimap<String, AttributeModifier> attributes = (this.itemInUse != null) ? this.itemInUse.func_111283_C() : null;
/* 1059 */       float attackDamage = 2.0F;
/* 1060 */       int knockback = 0;
/*      */       
/* 1062 */       if (attributes != null) {
/* 1063 */         for (AttributeModifier modifier : attributes.get(SharedMonsterAttributes.field_111264_e.func_111108_a())) {
/* 1064 */           switch (modifier.func_111169_c()) {
/*      */             case 0:
/* 1066 */               attackDamage = (float)(attackDamage + modifier.func_111164_d());
/*      */             
/*      */             case 1:
/* 1069 */               attackDamage = (float)(attackDamage * modifier.func_111164_d());
/*      */             
/*      */             case 2:
/* 1072 */               attackDamage = (float)(attackDamage * (1.0D + modifier.func_111164_d()));
/*      */           } 
/*      */ 
/*      */         
/*      */         } 
/*      */       }
/* 1078 */       if (par1Entity instanceof EntityLivingBase) {
/* 1079 */         attackDamage += EnchantmentHelper.func_77512_a((EntityLivingBase)this, (EntityLivingBase)par1Entity);
/* 1080 */         knockback += EnchantmentHelper.func_77507_b((EntityLivingBase)this, (EntityLivingBase)par1Entity);
/*      */       } 
/*      */       
/* 1083 */       if (attackDamage > 0.0F) {
/* 1084 */         int fireAspect = EnchantmentHelper.func_90036_a((EntityLivingBase)this);
/*      */         
/* 1086 */         if (par1Entity instanceof EntityLivingBase && fireAspect > 0 && !par1Entity.func_70027_ad()) {
/* 1087 */           par1Entity.func_70015_d(fireAspect * 4);
/*      */         }
/*      */         
/* 1090 */         if (par1Entity.func_70097_a((DamageSource)new EntityDamageSource("robot", (Entity)this), attackDamage)) {
/* 1091 */           func_130011_c(par1Entity);
/*      */           
/* 1093 */           if (knockback > 0) {
/* 1094 */             par1Entity.func_70024_g((-MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F) * knockback * 0.5F), 0.1D, (MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * knockback * 0.5F));
/* 1095 */             this.field_70159_w *= 0.6D;
/* 1096 */             this.field_70179_y *= 0.6D;
/* 1097 */             func_70031_b(false);
/*      */           } 
/*      */           
/* 1100 */           if (par1Entity instanceof EntityLivingBase) {
/* 1101 */             EnchantmentHelper.func_151384_a((EntityLivingBase)par1Entity, (Entity)this);
/*      */           }
/*      */           
/* 1104 */           EnchantmentHelper.func_151385_b((EntityLivingBase)this, par1Entity);
/*      */           
/* 1106 */           ItemStack itemstack = this.itemInUse;
/*      */           
/* 1108 */           if (itemstack != null && par1Entity instanceof EntityLivingBase) {
/* 1109 */             itemstack.func_77973_b().func_77644_a(itemstack, (EntityLivingBase)par1Entity, (EntityLivingBase)this);
/*      */           }
/*      */           
/* 1112 */           if (this.itemInUse.field_77994_a == 0) {
/* 1113 */             setItemInUse((ItemStack)null);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IZone getZoneToWork() {
/* 1123 */     return getZone(ActionRobotWorkInArea.AreaType.WORK);
/*      */   }
/*      */ 
/*      */   
/*      */   public IZone getZoneToLoadUnload() {
/* 1128 */     IZone zone = getZone(ActionRobotWorkInArea.AreaType.LOAD_UNLOAD);
/* 1129 */     if (zone == null) {
/* 1130 */       zone = getZoneToWork();
/*      */     }
/* 1132 */     return zone;
/*      */   }
/*      */   
/*      */   private IZone getZone(ActionRobotWorkInArea.AreaType areaType) {
/* 1136 */     if (this.linkedDockingStation != null) {
/* 1137 */       for (StatementSlot s : this.linkedDockingStation.getActiveActions()) {
/* 1138 */         if (s.statement instanceof ActionRobotWorkInArea && ((ActionRobotWorkInArea)s.statement)
/* 1139 */           .getAreaType() == areaType) {
/* 1140 */           IZone zone = ActionRobotWorkInArea.getArea(s);
/*      */           
/* 1142 */           if (zone != null) {
/* 1143 */             return zone;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1149 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsItems() {
/* 1154 */     for (ItemStack element : this.inv) {
/* 1155 */       if (element != null) {
/* 1156 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1160 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasFreeSlot() {
/* 1165 */     for (ItemStack element : this.inv) {
/* 1166 */       if (element == null) {
/* 1167 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1171 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void unreachableEntityDetected(Entity entity) {
/* 1176 */     this.unreachableEntities.put(entity, Long.valueOf(this.field_70170_p.func_82737_E() + 1200L));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isKnownUnreachable(Entity entity) {
/* 1181 */     if (this.unreachableEntities.containsKey(entity)) {
/* 1182 */       if (((Long)this.unreachableEntities.get(entity)).longValue() >= this.field_70170_p.func_82737_E()) {
/* 1183 */         return true;
/*      */       }
/* 1185 */       this.unreachableEntities.remove(entity);
/* 1186 */       return false;
/*      */     } 
/*      */     
/* 1189 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onRobotHit(boolean attacked) {
/* 1194 */     if (!this.field_70170_p.field_72995_K) {
/* 1195 */       if (attacked) {
/* 1196 */         convertToItems();
/*      */       }
/* 1198 */       else if (this.wearables.size() > 0) {
/* 1199 */         func_70099_a(this.wearables.remove(this.wearables.size() - 1), 0.0F);
/* 1200 */         syncWearablesToClient();
/* 1201 */       } else if (this.itemInUse != null) {
/* 1202 */         func_70099_a(this.itemInUse, 0.0F);
/* 1203 */         this.itemInUse = null;
/*      */       } else {
/* 1205 */         convertToItems();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean func_70085_c(EntityPlayer player) {
/* 1213 */     ItemStack stack = player.func_71045_bC();
/* 1214 */     if (stack == null || stack.func_77973_b() == null) {
/* 1215 */       return false;
/*      */     }
/*      */     
/* 1218 */     RobotEvent.Interact robotInteractEvent = new RobotEvent.Interact(this, player, stack);
/* 1219 */     MinecraftForge.EVENT_BUS.post((Event)robotInteractEvent);
/* 1220 */     if (robotInteractEvent.isCanceled()) {
/* 1221 */       return false;
/*      */     }
/*      */     
/* 1224 */     if (player.func_70093_af() && stack.func_77973_b() == BuildCraftCore.wrenchItem) {
/* 1225 */       RobotEvent.Dismantle robotDismantleEvent = new RobotEvent.Dismantle(this, player);
/* 1226 */       MinecraftForge.EVENT_BUS.post((Event)robotDismantleEvent);
/* 1227 */       if (robotDismantleEvent.isCanceled()) {
/* 1228 */         return false;
/*      */       }
/*      */       
/* 1231 */       onRobotHit(false);
/*      */       
/* 1233 */       if (this.field_70170_p.field_72995_K) {
/* 1234 */         ((ItemWrench)stack.func_77973_b()).wrenchUsed(player, 0, 0, 0);
/*      */       }
/* 1236 */       return true;
/* 1237 */     }  if (this.wearables.size() < 8 && stack.func_77973_b().isValidArmor(stack, 0, (Entity)this)) {
/* 1238 */       if (!this.field_70170_p.field_72995_K) {
/* 1239 */         this.wearables.add(stack.func_77979_a(1));
/* 1240 */         syncWearablesToClient();
/*      */       } else {
/* 1242 */         player.func_71038_i();
/*      */       } 
/* 1244 */       return true;
/* 1245 */     }  if (this.wearables.size() < 8 && stack.func_77973_b() instanceof IRobotOverlayItem && ((IRobotOverlayItem)stack.func_77973_b()).isValidRobotOverlay(stack)) {
/* 1246 */       if (!this.field_70170_p.field_72995_K) {
/* 1247 */         this.wearables.add(stack.func_77979_a(1));
/* 1248 */         syncWearablesToClient();
/*      */       } else {
/* 1250 */         player.func_71038_i();
/*      */       } 
/* 1252 */       return true;
/* 1253 */     }  if (this.wearables.size() < 8 && stack.func_77973_b() instanceof net.minecraft.item.ItemSkull) {
/* 1254 */       if (!this.field_70170_p.field_72995_K) {
/* 1255 */         ItemStack skullStack = stack.func_77979_a(1);
/* 1256 */         initSkullItem(skullStack);
/* 1257 */         this.wearables.add(skullStack);
/* 1258 */         syncWearablesToClient();
/*      */       } else {
/* 1260 */         player.func_71038_i();
/*      */       } 
/* 1262 */       return true;
/*      */     } 
/* 1264 */     return super.func_70085_c(player);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initSkullItem(ItemStack skullStack) {
/* 1269 */     if (skullStack.func_77942_o()) {
/* 1270 */       NBTTagCompound nbttagcompound = skullStack.func_77978_p();
/* 1271 */       GameProfile gameProfile = null;
/*      */       
/* 1273 */       if (nbttagcompound.func_150297_b("SkullOwner", 10)) {
/* 1274 */         gameProfile = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("SkullOwner"));
/* 1275 */       } else if (nbttagcompound.func_150297_b("SkullOwner", 8) && 
/* 1276 */         !StringUtils.func_151246_b(nbttagcompound.func_74779_i("SkullOwner"))) {
/* 1277 */         gameProfile = new GameProfile(null, nbttagcompound.func_74779_i("SkullOwner"));
/*      */       } 
/* 1279 */       if (gameProfile != null && !StringUtils.func_151246_b(gameProfile.getName()) && (
/* 1280 */         !gameProfile.isComplete() || 
/* 1281 */         !gameProfile.getProperties().containsKey("textures"))) {
/*      */         
/* 1283 */         gameProfile = MinecraftServer.func_71276_C().func_152358_ax().func_152655_a(gameProfile.getName());
/*      */         
/* 1285 */         if (gameProfile != null) {
/* 1286 */           Property property = (Property)Iterables.getFirst(gameProfile
/* 1287 */               .getProperties().get("textures"), null);
/*      */           
/* 1289 */           if (property == null)
/*      */           {
/* 1291 */             gameProfile = MinecraftServer.func_71276_C().func_147130_as().fillProfileProperties(gameProfile, true);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1296 */       if (gameProfile != null && gameProfile.isComplete() && gameProfile
/* 1297 */         .getProperties().containsKey("textures")) {
/* 1298 */         NBTTagCompound profileNBT = new NBTTagCompound();
/* 1299 */         NBTUtil.func_152460_a(profileNBT, gameProfile);
/* 1300 */         nbttagcompound.func_74782_a("SkullOwner", (NBTBase)profileNBT);
/*      */       } else {
/* 1302 */         nbttagcompound.func_82580_o("SkullOwner");
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void syncWearablesToClient() {
/* 1308 */     BuildCraftCore.instance.sendToEntity((Packet)new PacketCommand(this, "syncWearables", new CommandWriter() {
/*      */             public void write(ByteBuf data) {
/* 1310 */               data.writeByte(EntityRobot.this.wearables.size());
/* 1311 */               for (ItemStack s : EntityRobot.this.wearables) {
/* 1312 */                 NetworkUtils.writeStack(data, s);
/*      */               }
/*      */             }
/*      */           }), (Entity)this);
/*      */   }
/*      */   
/*      */   private List<ItemStack> getDrops() {
/* 1319 */     List<ItemStack> drops = new ArrayList<ItemStack>();
/* 1320 */     drops.add(ItemRobot.createRobotStack(this.board.getNBTHandler(), this.battery.getEnergyStored()));
/* 1321 */     if (this.itemInUse != null) {
/* 1322 */       drops.add(this.itemInUse);
/*      */     }
/* 1324 */     for (ItemStack element : this.inv) {
/* 1325 */       if (element != null) {
/* 1326 */         drops.add(element);
/*      */       }
/*      */     } 
/* 1329 */     drops.addAll(this.wearables);
/* 1330 */     return drops;
/*      */   }
/*      */   
/*      */   private void convertToItems() {
/* 1334 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/* 1335 */       if (this.mainAI != null) {
/* 1336 */         this.mainAI.abort();
/*      */       }
/* 1338 */       List<ItemStack> drops = getDrops();
/* 1339 */       for (ItemStack stack : drops) {
/* 1340 */         func_70099_a(stack, 0.0F);
/*      */       }
/* 1342 */       this.field_70128_L = true;
/*      */     } 
/*      */     
/* 1345 */     getRegistry().killRobot(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_70106_y() {
/* 1350 */     if (this.field_70170_p.field_72995_K) {
/* 1351 */       super.func_70106_y();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/* 1357 */     getRegistry().unloadRobot(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_70104_M() {
/* 1362 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_82167_n(Entity par1Entity) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_70108_f(Entity par1Entity) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUniqueRobotId(long iRobotId) {
/* 1376 */     this.robotId = iRobotId;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getRobotId() {
/* 1381 */     return this.robotId;
/*      */   }
/*      */ 
/*      */   
/*      */   public RobotRegistry getRegistry() {
/* 1386 */     return (RobotRegistry)RobotManager.registryProvider.getRegistry(this.field_70170_p);
/*      */   }
/*      */ 
/*      */   
/*      */   public void releaseResources() {
/* 1391 */     getRegistry().releaseResources(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack receiveItem(TileEntity tile, ItemStack stack) {
/* 1400 */     if (this.currentDockingStation != null && this.currentDockingStation
/* 1401 */       .index().nextTo(new BlockIndex(tile)) && this.mainAI != null)
/*      */     {
/*      */       
/* 1404 */       return this.mainAI.getActiveAI().receiveItem(stack);
/*      */     }
/* 1406 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/*      */     int result;
/* 1414 */     if (this.tank != null && !this.tank.isFluidEqual(resource)) {
/* 1415 */       return 0;
/*      */     }
/*      */     
/* 1418 */     if (this.tank == null) {
/* 1419 */       this.tank = new FluidStack(resource.getFluid(), 0);
/*      */     }
/*      */     
/* 1422 */     if (this.tank.amount + resource.amount <= this.maxFluid) {
/* 1423 */       result = resource.amount;
/*      */       
/* 1425 */       if (doFill) {
/* 1426 */         this.tank.amount += resource.amount;
/*      */       }
/*      */     } else {
/* 1429 */       result = this.maxFluid - this.tank.amount;
/*      */       
/* 1431 */       if (doFill) {
/* 1432 */         this.tank.amount = this.maxFluid;
/*      */       }
/*      */     } 
/*      */     
/* 1436 */     if (this.tank != null && this.tank.amount == 0) {
/* 1437 */       this.tank = null;
/*      */     }
/*      */     
/* 1440 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 1445 */     if (this.tank != null && this.tank.isFluidEqual(resource)) {
/* 1446 */       return drain(from, resource.amount, doDrain);
/*      */     }
/* 1448 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/*      */     FluidStack result;
/* 1456 */     if (this.tank == null) {
/* 1457 */       result = null;
/* 1458 */     } else if (this.tank.amount <= maxDrain) {
/* 1459 */       result = this.tank.copy();
/*      */       
/* 1461 */       if (doDrain) {
/* 1462 */         this.tank = null;
/*      */       }
/*      */     } else {
/* 1465 */       result = this.tank.copy();
/* 1466 */       result.amount = maxDrain;
/*      */       
/* 1468 */       if (doDrain) {
/* 1469 */         this.tank.amount -= maxDrain;
/*      */       }
/*      */     } 
/*      */     
/* 1473 */     if (this.tank != null && this.tank.amount == 0) {
/* 1474 */       this.tank = null;
/*      */     }
/*      */     
/* 1477 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 1482 */     return (this.tank == null || this.tank.amount == 0 || (this.tank.amount < this.maxFluid && this.tank
/*      */ 
/*      */       
/* 1485 */       .getFluid().getID() == fluid.getID()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 1490 */     return (this.tank != null && this.tank.amount != 0 && this.tank
/*      */       
/* 1492 */       .getFluid().getID() == fluid.getID());
/*      */   }
/*      */ 
/*      */   
/*      */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 1497 */     return new FluidTankInfo[] { new FluidTankInfo(this.tank, this.maxFluid) };
/*      */   }
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public IIcon func_70620_b(ItemStack stack, int renderPass) {
/* 1502 */     IIcon iicon = super.func_70620_b(stack, renderPass);
/*      */     
/* 1504 */     if (iicon == null) {
/* 1505 */       iicon = stack.func_77973_b().getIcon(stack, renderPass, null, this.itemInUse, 0);
/*      */     }
/*      */     
/* 1508 */     return iicon;
/*      */   }
/*      */ 
/*      */   
/*      */   public void getDebugInfo(List<String> info, ForgeDirection side, ItemStack debugger, EntityPlayer player) {
/* 1513 */     info.add("Robot " + this.board.getNBTHandler().getID() + " (" + getBattery().getEnergyStored() + "/" + getBattery().getMaxEnergyStored() + " RF)");
/* 1514 */     info.add(String.format("Position: %.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.field_70165_t), Double.valueOf(this.field_70163_u), Double.valueOf(this.field_70161_v) }));
/* 1515 */     info.add("AI tree:");
/* 1516 */     AIRobotMain aIRobotMain = this.mainAI;
/* 1517 */     while (aIRobotMain != null) {
/* 1518 */       info.add("- " + RobotManager.getAIRobotName(aIRobotMain.getClass()) + " (" + aIRobotMain.getEnergyCost() + " RF/t)");
/* 1519 */       if (aIRobotMain instanceof IDebuggable) {
/* 1520 */         ((IDebuggable)aIRobotMain).getDebugInfo(info, side, debugger, player);
/*      */       }
/* 1522 */       AIRobot aIRobot = aIRobotMain.getDelegateAI();
/*      */     } 
/*      */   }
/*      */   
/*      */   public int receiveEnergy(int maxReceive, boolean simulate) {
/* 1527 */     int energyReceived = getBattery().receiveEnergy(maxReceive, simulate);
/*      */ 
/*      */     
/* 1530 */     if (!simulate && energyReceived > 5 && this.ticksCharging <= 25) {
/* 1531 */       this.ticksCharging += 5;
/*      */     }
/*      */     
/* 1534 */     return energyReceived;
/*      */   }
/*      */   
/*      */   public List<ItemStack> getWearables() {
/* 1538 */     return this.wearables;
/*      */   }
/*      */   
/*      */   private void updateItem(ItemStack stack, int i, boolean held) {
/* 1542 */     if (stack != null && stack.func_77973_b() != null) {
/* 1543 */       int id = Item.func_150891_b(stack.func_77973_b());
/*      */       
/* 1545 */       if (!blacklistedItemsForUpdate.contains(Integer.valueOf(id)))
/*      */         try {
/* 1547 */           stack.func_77973_b().func_77663_a(stack, this.field_70170_p, (Entity)this, i, held);
/* 1548 */         } catch (Exception e) {
/*      */           
/* 1550 */           e.printStackTrace();
/* 1551 */           blacklistedItemsForUpdate.add(Integer.valueOf(id));
/*      */         }  
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\EntityRobot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */