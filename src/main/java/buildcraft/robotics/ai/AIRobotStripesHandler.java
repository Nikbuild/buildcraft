/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.core.Position;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.api.transport.IStripesActivator;
/*     */ import buildcraft.api.transport.IStripesHandler;
/*     */ import buildcraft.api.transport.PipeManager;
/*     */ import buildcraft.core.lib.inventory.InvUtils;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotStripesHandler
/*     */   extends AIRobot
/*     */   implements IStripesActivator
/*     */ {
/*     */   private BlockIndex useToBlock;
/*  30 */   private int useCycles = 0;
/*     */   
/*     */   public AIRobotStripesHandler(EntityRobotBase iRobot) {
/*  33 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotStripesHandler(EntityRobotBase iRobot, BlockIndex index) {
/*  37 */     this(iRobot);
/*     */     
/*  39 */     this.useToBlock = index;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  44 */     this.robot.aimItemAt(this.useToBlock.x, this.useToBlock.y, this.useToBlock.z);
/*  45 */     this.robot.setItemActive(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  50 */     if (this.useToBlock == null) {
/*  51 */       setSuccess(false);
/*  52 */       terminate();
/*     */       
/*     */       return;
/*     */     } 
/*  56 */     this.useCycles++;
/*     */     
/*  58 */     if (this.useCycles > 60) {
/*  59 */       ItemStack stack = this.robot.func_70694_bm();
/*     */       
/*  61 */       ForgeDirection direction = ForgeDirection.NORTH;
/*     */       
/*  63 */       Position p = new Position(this.useToBlock.x, this.useToBlock.y, this.useToBlock.z);
/*     */ 
/*     */ 
/*     */       
/*  67 */       EntityPlayer player = CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.robot.field_70170_p, (int)p.x, (int)p.y, (int)p.z).get();
/*  68 */       player.field_70125_A = 0.0F;
/*  69 */       player.field_70177_z = 180.0F;
/*     */       
/*  71 */       for (IStripesHandler handler : PipeManager.stripesHandlers) {
/*  72 */         if (handler.getType() == IStripesHandler.StripesHandlerType.ITEM_USE && handler
/*  73 */           .shouldHandle(stack) && 
/*  74 */           handler.handle(this.robot.field_70170_p, (int)p.x, (int)p.y, (int)p.z, direction, stack, player, this)) {
/*     */           
/*  76 */           this.robot.setItemInUse(null);
/*  77 */           terminate();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  82 */       terminate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  88 */     this.robot.setItemActive(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/*  93 */     return 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendItem(ItemStack stack, ForgeDirection direction) {
/*  98 */     InvUtils.dropItems(this.robot.field_70170_p, stack, (int)Math.floor(this.robot.field_70165_t), 
/*  99 */         (int)Math.floor(this.robot.field_70163_u), (int)Math.floor(this.robot.field_70161_v));
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropItem(ItemStack stack, ForgeDirection direction) {
/* 104 */     sendItem(stack, direction);
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotStripesHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */