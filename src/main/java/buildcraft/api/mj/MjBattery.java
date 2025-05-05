/*     */ package buildcraft.api.mj;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.INBTSerializable;
/*     */ 
/*     */ 
/*     */ public class MjBattery
/*     */   implements INBTSerializable<NBTTagCompound>
/*     */ {
/*     */   private final long capacity;
/*  16 */   private long microJoules = 0L;
/*     */   
/*     */   public MjBattery(long capacity) {
/*  19 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound serializeNBT() {
/*  24 */     NBTTagCompound nbt = new NBTTagCompound();
/*  25 */     nbt.func_74772_a("stored", this.microJoules);
/*  26 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deserializeNBT(NBTTagCompound nbt) {
/*  31 */     this.microJoules = nbt.func_74763_f("stored");
/*     */   }
/*     */   
/*     */   public void writeToBuffer(ByteBuf buffer) {
/*  35 */     buffer.writeLong(this.microJoules);
/*     */   }
/*     */   
/*     */   public void readFromBuffer(ByteBuf buffer) {
/*  39 */     this.microJoules = buffer.readLong();
/*     */   }
/*     */   
/*     */   public long addPower(long microJoulesToAdd, boolean simulate) {
/*  43 */     if (!simulate) {
/*  44 */       this.microJoules += microJoulesToAdd;
/*     */     }
/*  46 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long addPowerChecking(long microJoulesToAdd, boolean simulate) {
/*  54 */     if (isFull()) {
/*  55 */       return microJoulesToAdd;
/*     */     }
/*  57 */     return addPower(microJoulesToAdd, simulate);
/*     */   }
/*     */ 
/*     */   
/*     */   public long extractAll() {
/*  62 */     return extractPower(0L, this.microJoules);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extractPower(long power) {
/*  70 */     return (extractPower(power, power) > 0L);
/*     */   }
/*     */   
/*     */   public long extractPower(long min, long max) {
/*  74 */     if (this.microJoules < min) return 0L; 
/*  75 */     long extracting = Math.min(this.microJoules, max);
/*  76 */     this.microJoules -= extracting;
/*  77 */     return extracting;
/*     */   }
/*     */   
/*     */   public boolean isFull() {
/*  81 */     return (this.microJoules >= this.capacity);
/*     */   }
/*     */   
/*     */   public long getStored() {
/*  85 */     return this.microJoules;
/*     */   }
/*     */   
/*     */   public long getCapacity() {
/*  89 */     return this.capacity;
/*     */   }
/*     */   
/*     */   public void tick(World world, BlockPos position) {
/*  93 */     tick(world, new Vec3d(position.func_177958_n() + 0.5D, position.func_177956_o() + 0.5D, position.func_177952_p() + 0.5D));
/*     */   }
/*     */   
/*     */   public void tick(World world, Vec3d position) {
/*  97 */     if (this.microJoules > this.capacity * 2L) {
/*  98 */       losePower(world, position);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void losePower(World world, Vec3d position) {
/* 103 */     long diff = this.microJoules - this.capacity * 2L;
/* 104 */     long lost = ceilDivide(diff, 32L);
/* 105 */     this.microJoules -= lost;
/* 106 */     MjAPI.EFFECT_MANAGER.createPowerLossEffect(world, position, lost);
/*     */   }
/*     */   
/*     */   private static long ceilDivide(long val, long by) {
/* 110 */     return val / by + ((val % by == 0L) ? 0L : 1L);
/*     */   }
/*     */   
/*     */   public String getDebugString() {
/* 114 */     return MjAPI.formatMj(this.microJoules) + " / " + MjAPI.formatMj(this.capacity) + " MJ";
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\mj\MjBattery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */