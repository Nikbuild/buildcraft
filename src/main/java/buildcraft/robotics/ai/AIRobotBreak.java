/*     */ package buildcraft.robotics.ai;
/*     */ 
/*     */ import buildcraft.api.core.BlockIndex;
/*     */ import buildcraft.api.robots.AIRobot;
/*     */ import buildcraft.api.robots.EntityRobotBase;
/*     */ import buildcraft.core.lib.utils.BlockUtils;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AIRobotBreak
/*     */   extends AIRobot
/*     */ {
/*     */   private BlockIndex blockToBreak;
/*  28 */   private float blockDamage = 0.0F;
/*     */   
/*     */   private Block block;
/*     */   private int meta;
/*     */   private float hardness;
/*     */   private float speed;
/*     */   
/*     */   public AIRobotBreak(EntityRobotBase iRobot) {
/*  36 */     super(iRobot);
/*     */   }
/*     */   
/*     */   public AIRobotBreak(EntityRobotBase iRobot, BlockIndex iBlockToBreak) {
/*  40 */     this(iRobot);
/*     */     
/*  42 */     this.blockToBreak = iBlockToBreak;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  47 */     this.robot.aimItemAt(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
/*     */     
/*  49 */     this.robot.setItemActive(true);
/*  50 */     this.block = this.robot.field_70170_p.func_147439_a(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
/*  51 */     this.meta = this.robot.field_70170_p.func_72805_g(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
/*  52 */     this.hardness = BlockUtils.getBlockHardnessMining(this.robot.field_70170_p, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, this.block, this.robot.func_70694_bm());
/*  53 */     this.speed = getBreakSpeed(this.robot, this.robot.func_70694_bm(), this.block, this.meta);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  58 */     if (this.block == null) {
/*  59 */       this.block = this.robot.field_70170_p.func_147439_a(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
/*  60 */       if (this.block == null) {
/*  61 */         setSuccess(false);
/*  62 */         terminate();
/*     */         return;
/*     */       } 
/*  65 */       this.meta = this.robot.field_70170_p.func_72805_g(this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
/*  66 */       this.hardness = BlockUtils.getBlockHardnessMining(this.robot.field_70170_p, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, this.block, this.robot.func_70694_bm());
/*  67 */       this.speed = getBreakSpeed(this.robot, this.robot.func_70694_bm(), this.block, this.meta);
/*     */     } 
/*     */     
/*  70 */     if (this.block.isAir((IBlockAccess)this.robot.field_70170_p, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z) || this.hardness < 0.0F) {
/*  71 */       setSuccess(false);
/*  72 */       terminate();
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if (this.hardness != 0.0F) {
/*  77 */       this.blockDamage += this.speed / this.hardness / 30.0F;
/*     */     } else {
/*     */       
/*  80 */       this.blockDamage = 1.1F;
/*     */     } 
/*     */     
/*  83 */     if (this.blockDamage > 1.0F) {
/*  84 */       this.robot.field_70170_p.func_147443_d(this.robot.func_145782_y(), this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, -1);
/*     */       
/*  86 */       this.blockDamage = 0.0F;
/*     */       
/*  88 */       if (BlockUtils.harvestBlock((WorldServer)this.robot.field_70170_p, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, this.robot.func_70694_bm())) {
/*  89 */         this.robot.field_70170_p.func_72889_a(null, 2001, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, 
/*     */             
/*  91 */             Block.func_149682_b(this.block) + (this.meta << 12));
/*     */         
/*  93 */         if (this.robot.func_70694_bm() != null) {
/*  94 */           this.robot.func_70694_bm().func_77973_b()
/*  95 */             .func_150894_a(this.robot.func_70694_bm(), this.robot.field_70170_p, this.block, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, (EntityLivingBase)this.robot);
/*     */ 
/*     */           
/*  98 */           if ((this.robot.func_70694_bm()).field_77994_a == 0) {
/*  99 */             this.robot.setItemInUse(null);
/*     */           }
/*     */         } 
/*     */       } else {
/* 103 */         setSuccess(false);
/*     */       } 
/*     */       
/* 106 */       terminate();
/*     */     } else {
/* 108 */       this.robot.field_70170_p.func_147443_d(this.robot.func_145782_y(), this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, (int)(this.blockDamage * 10.0F) - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void end() {
/* 115 */     this.robot.setItemActive(false);
/* 116 */     this.robot.field_70170_p.func_147443_d(this.robot.func_145782_y(), this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, -1);
/*     */   }
/*     */ 
/*     */   
/*     */   private float getBreakSpeed(EntityRobotBase robot, ItemStack usingItem, Block block, int meta) {
/* 121 */     ItemStack stack = usingItem;
/* 122 */     float f = (stack == null) ? 1.0F : stack.func_77973_b().getDigSpeed(stack, block, meta);
/*     */     
/* 124 */     if (f > 1.0F) {
/* 125 */       int i = EnchantmentHelper.func_77509_b((EntityLivingBase)robot);
/*     */       
/* 127 */       if (i > 0) {
/* 128 */         float f1 = (i * i + 1);
/*     */         
/* 130 */         boolean canHarvest = ForgeHooks.canToolHarvestBlock(block, meta, usingItem);
/*     */         
/* 132 */         if (!canHarvest && f <= 1.0F) {
/* 133 */           f += f1 * 0.08F;
/*     */         } else {
/* 135 */           f += f1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     f = ForgeEventFactory.getBreakSpeed(BlockUtils.getFakePlayerWithTool((WorldServer)robot.field_70170_p, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z, robot.func_70694_bm()), block, meta, f, this.blockToBreak.x, this.blockToBreak.y, this.blockToBreak.z);
/*     */     
/* 142 */     return (f < 0.0F) ? 0.0F : f;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyCost() {
/* 147 */     return (int)Math.ceil(10.666666984558105D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLoadFromNBT() {
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeSelfToNBT(NBTTagCompound nbt) {
/* 157 */     super.writeSelfToNBT(nbt);
/*     */     
/* 159 */     if (this.blockToBreak != null) {
/* 160 */       NBTTagCompound sub = new NBTTagCompound();
/* 161 */       this.blockToBreak.writeTo(sub);
/* 162 */       nbt.func_74782_a("blockToBreak", (NBTBase)sub);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSelfFromNBT(NBTTagCompound nbt) {
/* 168 */     super.loadSelfFromNBT(nbt);
/*     */     
/* 170 */     if (nbt.func_74764_b("blockToBreak"))
/* 171 */       this.blockToBreak = new BlockIndex(nbt.func_74775_l("blockToBreak")); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ai\AIRobotBreak.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */