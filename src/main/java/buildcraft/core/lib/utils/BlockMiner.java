/*     */ package buildcraft.core.lib.utils;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.proxy.CoreProxy;
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import net.minecraftforge.event.world.BlockEvent;
/*     */ 
/*     */ 
/*     */ public class BlockMiner
/*     */ {
/*     */   protected final World world;
/*     */   protected final TileEntity owner;
/*     */   protected final int x;
/*     */   protected final int y;
/*     */   protected final int z;
/*     */   
/*     */   public BlockMiner(World world, TileEntity owner, int x, int y, int z) {
/*  28 */     this.world = world;
/*  29 */     this.owner = owner;
/*  30 */     this.x = x;
/*  31 */     this.y = y;
/*  32 */     this.z = z;
/*  33 */     this.minerId = world.field_73012_v.nextInt();
/*     */   }
/*     */   protected final int minerId; private boolean hasMined; private boolean hasFailed; private int energyRequired; private int energyAccepted;
/*     */   public boolean hasMined() {
/*  37 */     return this.hasMined;
/*     */   }
/*     */   
/*     */   public boolean hasFailed() {
/*  41 */     return this.hasFailed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mineStack(ItemStack stack) {
/*  46 */     stack.field_77994_a -= Utils.addToRandomInventoryAround(this.owner.func_145831_w(), this.owner.field_145851_c, this.owner.field_145848_d, this.owner.field_145849_e, stack);
/*     */ 
/*     */     
/*  49 */     if (stack.field_77994_a > 0) {
/*  50 */       stack.field_77994_a -= Utils.addToRandomInjectableAround(this.owner.func_145831_w(), this.owner.field_145851_c, this.owner.field_145848_d, this.owner.field_145849_e, ForgeDirection.UNKNOWN, stack);
/*     */     }
/*     */ 
/*     */     
/*  54 */     if (stack.field_77994_a > 0) {
/*  55 */       float f = this.world.field_73012_v.nextFloat() * 0.8F + 0.1F;
/*  56 */       float f1 = this.world.field_73012_v.nextFloat() * 0.8F + 0.1F;
/*  57 */       float f2 = this.world.field_73012_v.nextFloat() * 0.8F + 0.1F;
/*     */       
/*  59 */       EntityItem entityitem = new EntityItem(this.owner.func_145831_w(), (this.owner.field_145851_c + f), (this.owner.field_145848_d + f1 + 0.5F), (this.owner.field_145849_e + f2), stack);
/*     */       
/*  61 */       entityitem.lifespan = BuildCraftCore.itemLifespan * 20;
/*  62 */       entityitem.field_145804_b = 10;
/*     */       
/*  64 */       float f3 = 0.05F;
/*  65 */       entityitem.field_70159_w = ((float)this.world.field_73012_v.nextGaussian() * f3);
/*  66 */       entityitem.field_70181_x = ((float)this.world.field_73012_v.nextGaussian() * f3 + 1.0F);
/*  67 */       entityitem.field_70179_y = ((float)this.world.field_73012_v.nextGaussian() * f3);
/*  68 */       this.owner.func_145831_w().func_72838_d((Entity)entityitem);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void invalidate() {
/*  73 */     this.world.func_147443_d(this.minerId, this.x, this.y, this.z, -1);
/*     */   }
/*     */   
/*     */   public int acceptEnergy(int offeredAmount) {
/*  77 */     if (BlockUtils.isUnbreakableBlock(this.world, this.x, this.y, this.z)) {
/*  78 */       this.hasFailed = true;
/*  79 */       return 0;
/*     */     } 
/*     */     
/*  82 */     this.energyRequired = BlockUtils.computeBlockBreakEnergy(this.world, this.x, this.y, this.z);
/*     */     
/*  84 */     int usedAmount = MathUtils.clamp(offeredAmount, 0, Math.max(0, this.energyRequired - this.energyAccepted));
/*  85 */     this.energyAccepted += usedAmount;
/*     */     
/*  87 */     if (this.energyAccepted >= this.energyRequired) {
/*  88 */       this.world.func_147443_d(this.minerId, this.x, this.y, this.z, -1);
/*     */       
/*  90 */       this.hasMined = true;
/*     */       
/*  92 */       Block block = this.world.func_147439_a(this.x, this.y, this.z);
/*  93 */       int meta = this.world.func_72805_g(this.x, this.y, this.z);
/*     */ 
/*     */       
/*  96 */       BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(this.x, this.y, this.z, this.world, block, meta, CoreProxy.proxy.getBuildCraftPlayer((WorldServer)this.owner.func_145831_w(), this.owner.field_145851_c, this.owner.field_145848_d, this.owner.field_145849_e).get());
/*  97 */       MinecraftForge.EVENT_BUS.post((Event)breakEvent);
/*     */       
/*  99 */       if (!breakEvent.isCanceled()) {
/* 100 */         List<ItemStack> stacks = BlockUtils.getItemStackFromBlock((WorldServer)this.world, this.x, this.y, this.z);
/*     */         
/* 102 */         if (stacks != null) {
/* 103 */           for (ItemStack s : stacks) {
/* 104 */             if (s != null) {
/* 105 */               mineStack(s);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 110 */         this.world.func_72889_a(null, 2001, this.x, this.y, this.z, 
/*     */ 
/*     */ 
/*     */             
/* 114 */             Block.func_149682_b(block) + (meta << 12));
/*     */ 
/*     */         
/* 117 */         this.world.func_147468_f(this.x, this.y, this.z);
/*     */       } else {
/* 119 */         this.hasFailed = true;
/*     */       } 
/*     */     } else {
/* 122 */       this.world.func_147443_d(this.minerId, this.x, this.y, this.z, MathUtils.clamp((int)Math.floor((this.energyAccepted * 10 / this.energyRequired)), 0, 9));
/*     */     } 
/* 124 */     return usedAmount;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\li\\utils\BlockMiner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */