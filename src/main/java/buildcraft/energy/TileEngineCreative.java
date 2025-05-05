/*     */ package buildcraft.energy;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.tools.IToolWrench;
/*     */ import buildcraft.core.PowerMode;
/*     */ import buildcraft.core.lib.engines.TileEngineBase;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
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
/*     */ 
/*     */ public class TileEngineCreative
/*     */   extends TileEngineBase
/*     */ {
/*  28 */   private PowerMode powerMode = PowerMode.M2;
/*     */ 
/*     */   
/*     */   protected TileEngineBase.EnergyStage computeEnergyStage() {
/*  32 */     return TileEngineBase.EnergyStage.BLUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(EntityPlayer player, ForgeDirection side) {
/*  37 */     if (!(func_145831_w()).field_72995_K) {
/*  38 */       Item equipped = (player.func_71045_bC() != null) ? player.func_71045_bC().func_77973_b() : null;
/*     */       
/*  40 */       if (equipped instanceof IToolWrench && ((IToolWrench)equipped).canWrench(player, this.field_145851_c, this.field_145848_d, this.field_145849_e)) {
/*  41 */         this.powerMode = this.powerMode.getNext();
/*  42 */         this.energy = 0;
/*     */         
/*  44 */         if (!(player instanceof net.minecraftforge.common.util.FakePlayer)) {
/*  45 */           if (BuildCraftCore.hidePowerNumbers) {
/*  46 */             player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.pipe.power.iron.mode.numberless", new Object[] {
/*  47 */                     StringUtils.localize("chat.pipe.power.iron.level." + this.powerMode.maxPower) }));
/*     */           } else {
/*  49 */             player.func_145747_a((IChatComponent)new ChatComponentTranslation("chat.pipe.power.iron.mode", new Object[] {
/*  50 */                     Integer.valueOf(this.powerMode.maxPower)
/*     */                   }));
/*     */           } 
/*     */         }
/*  54 */         sendNetworkUpdate();
/*     */         
/*  56 */         ((IToolWrench)equipped).wrenchUsed(player, this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  57 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return !player.func_70093_af();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound data) {
/*  66 */     super.func_145839_a(data);
/*     */     
/*  68 */     this.powerMode = PowerMode.fromId(data.func_74771_c("mode"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound data) {
/*  73 */     super.func_145841_b(data);
/*     */     
/*  75 */     data.func_74774_a("mode", (byte)this.powerMode.ordinal());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readData(ByteBuf stream) {
/*  80 */     super.readData(stream);
/*  81 */     this.powerMode = PowerMode.fromId(stream.readUnsignedByte());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeData(ByteBuf stream) {
/*  86 */     super.writeData(stream);
/*  87 */     stream.writeByte(this.powerMode.ordinal());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateHeat() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPistonSpeed() {
/*  97 */     return 0.02F * (this.powerMode.ordinal() + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void engineUpdate() {
/* 102 */     super.engineUpdate();
/*     */     
/* 104 */     if (this.isRedstonePowered) {
/* 105 */       addEnergy(getIdealOutput());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 111 */     return this.isRedstonePowered;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxEnergy() {
/* 116 */     return getIdealOutput();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIdealOutput() {
/* 121 */     return this.powerMode.maxPower;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-energy.jar!\buildcraft\energy\TileEngineCreative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */