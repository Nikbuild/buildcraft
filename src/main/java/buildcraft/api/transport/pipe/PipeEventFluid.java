/*     */ package buildcraft.api.transport.pipe;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PipeEventFluid
/*     */   extends PipeEvent
/*     */ {
/*     */   public final IFlowFluid flow;
/*     */   
/*     */   protected PipeEventFluid(IPipeHolder holder, IFlowFluid flow) {
/*  20 */     super(holder);
/*  21 */     this.flow = flow;
/*     */   }
/*     */   
/*     */   protected PipeEventFluid(boolean canBeCancelled, IPipeHolder holder, IFlowFluid flow) {
/*  25 */     super(canBeCancelled, holder);
/*  26 */     this.flow = flow;
/*     */   }
/*     */   
/*     */   public static class TryInsert
/*     */     extends PipeEventFluid {
/*     */     public final EnumFacing from;
/*     */     @Nonnull
/*     */     public final FluidStack fluid;
/*     */     
/*     */     public TryInsert(IPipeHolder holder, IFlowFluid flow, EnumFacing from, @Nonnull FluidStack fluid) {
/*  36 */       super(true, holder, flow);
/*  37 */       this.from = from;
/*  38 */       this.fluid = fluid;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PreMoveToCentre
/*     */     extends PipeEventFluid
/*     */   {
/*     */     public final FluidStack fluid;
/*     */ 
/*     */     
/*     */     public final int totalAcceptable;
/*     */ 
/*     */     
/*     */     public final int[] totalOffered;
/*     */ 
/*     */     
/*     */     private final int[] totalOfferedCheck;
/*     */ 
/*     */     
/*     */     public final int[] actuallyOffered;
/*     */ 
/*     */     
/*     */     public PreMoveToCentre(IPipeHolder holder, IFlowFluid flow, FluidStack fluid, int totalAcceptable, int[] totalOffered, int[] actuallyOffered) {
/*  63 */       super(holder, flow);
/*  64 */       this.fluid = fluid;
/*  65 */       this.totalAcceptable = totalAcceptable;
/*  66 */       this.totalOffered = totalOffered;
/*  67 */       this.totalOfferedCheck = Arrays.copyOf(totalOffered, totalOffered.length);
/*  68 */       this.actuallyOffered = actuallyOffered;
/*     */     }
/*     */ 
/*     */     
/*     */     public String checkStateForErrors() {
/*  73 */       for (int i = 0; i < this.totalOffered.length; i++) {
/*  74 */         if (this.totalOffered[i] != this.totalOfferedCheck[i]) {
/*  75 */           return "Changed totalOffered";
/*     */         }
/*  77 */         if (this.actuallyOffered[i] > this.totalOffered[i]) {
/*  78 */           return "actuallyOffered[" + i + "](=" + this.actuallyOffered[i] + ") shouldn't be greater than totalOffered[" + i + "](=" + this.totalOffered[i] + ")";
/*     */         }
/*     */       } 
/*  81 */       return super.checkStateForErrors();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class OnMoveToCentre
/*     */     extends PipeEventFluid
/*     */   {
/*     */     public final FluidStack fluid;
/*     */     
/*     */     public final int[] fluidLeavingSide;
/*     */     
/*     */     public final int[] fluidEnteringCentre;
/*     */     private final int[] fluidLeaveCheck;
/*     */     private final int[] fluidEnterCheck;
/*     */     
/*     */     public OnMoveToCentre(IPipeHolder holder, IFlowFluid flow, FluidStack fluid, int[] fluidLeavingSide, int[] fluidEnteringCentre) {
/*  98 */       super(holder, flow);
/*  99 */       this.fluid = fluid;
/* 100 */       this.fluidLeavingSide = fluidLeavingSide;
/* 101 */       this.fluidEnteringCentre = fluidEnteringCentre;
/* 102 */       this.fluidLeaveCheck = Arrays.copyOf(fluidLeavingSide, fluidLeavingSide.length);
/* 103 */       this.fluidEnterCheck = Arrays.copyOf(fluidEnteringCentre, fluidEnteringCentre.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public String checkStateForErrors() {
/* 108 */       for (int i = 0; i < this.fluidLeavingSide.length; i++) {
/* 109 */         if (this.fluidLeavingSide[i] > this.fluidLeaveCheck[i]) {
/* 110 */           return "fluidLeavingSide[" + i + "](=" + this.fluidLeavingSide[i] + ") shouldn't be bigger than its original value!(=" + this.fluidLeaveCheck[i] + ")";
/*     */         }
/* 112 */         if (this.fluidEnteringCentre[i] > this.fluidEnterCheck[i]) {
/* 113 */           return "fluidEnteringCentre[" + i + "](=" + this.fluidEnteringCentre[i] + ") shouldn't be bigger than its original value!(=" + this.fluidEnterCheck[i] + ")";
/*     */         }
/* 115 */         if (this.fluidEnteringCentre[i] > this.fluidLeavingSide[i]) {
/* 116 */           return "fluidEnteringCentre[" + i + "](=" + this.fluidEnteringCentre[i] + ") shouldn't be bigger than fluidLeavingSide[" + i + "](=" + this.fluidLeavingSide[i] + ")";
/*     */         }
/*     */       } 
/* 119 */       return super.checkStateForErrors();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SideCheck
/*     */     extends PipeEventFluid
/*     */   {
/*     */     public final FluidStack fluid;
/* 128 */     private final int[] priority = new int[6];
/* 129 */     private final EnumSet<EnumFacing> allowed = EnumSet.allOf(EnumFacing.class);
/*     */     
/*     */     public SideCheck(IPipeHolder holder, IFlowFluid flow, FluidStack fluid) {
/* 132 */       super(holder, flow);
/* 133 */       this.fluid = fluid;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAllowed(EnumFacing side) {
/* 140 */       return this.allowed.contains(side);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void disallow(EnumFacing... sides) {
/* 146 */       for (EnumFacing side : sides) {
/* 147 */         this.allowed.remove(side);
/*     */       }
/*     */     }
/*     */     
/*     */     public void disallowAll(Collection<EnumFacing> sides) {
/* 152 */       this.allowed.removeAll(sides);
/*     */     }
/*     */     
/*     */     public void disallowAllExcept(EnumFacing... sides) {
/* 156 */       this.allowed.retainAll(Lists.newArrayList((Object[])sides));
/*     */     }
/*     */     
/*     */     public void disallowAllExcept(Collection<EnumFacing> sides) {
/* 160 */       this.allowed.retainAll(sides);
/*     */     }
/*     */     
/*     */     public void disallowAll() {
/* 164 */       this.allowed.clear();
/*     */     }
/*     */     
/*     */     public void increasePriority(EnumFacing side) {
/* 168 */       increasePriority(side, 1);
/*     */     }
/*     */     
/*     */     public void increasePriority(EnumFacing side, int by) {
/* 172 */       this.priority[side.ordinal()] = this.priority[side.ordinal()] - by;
/*     */     }
/*     */     
/*     */     public void decreasePriority(EnumFacing side) {
/* 176 */       decreasePriority(side, 1);
/*     */     }
/*     */     
/*     */     public void decreasePriority(EnumFacing side, int by) {
/* 180 */       increasePriority(side, -by);
/*     */     }
/*     */     
/*     */     public EnumSet<EnumFacing> getOrder() {
/* 184 */       if (this.allowed.isEmpty()) {
/* 185 */         return EnumSet.noneOf(EnumFacing.class);
/*     */       }
/* 187 */       if (this.allowed.size() == 1) {
/* 188 */         return this.allowed;
/*     */       }
/*     */       
/* 191 */       int val = this.priority[0];
/* 192 */       for (int i = 1; i < this.priority.length; last++) {
/* 193 */         int last; if (this.priority[i] != val) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 201 */           int[] ordered = Arrays.copyOf(this.priority, 6);
/* 202 */           Arrays.sort(ordered);
/* 203 */           last = 0;
/* 204 */           for (int j = 0; j < 6; j++) {
/* 205 */             int current = ordered[j];
/* 206 */             if (j == 0 || current != last) {
/*     */ 
/*     */               
/* 209 */               last = current;
/* 210 */               EnumSet<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/* 211 */               for (EnumFacing face : EnumFacing.field_82609_l) {
/* 212 */                 if (this.allowed.contains(face) && 
/* 213 */                   this.priority[face.ordinal()] == current) {
/* 214 */                   set.add(face);
/*     */                 }
/*     */               } 
/*     */               
/* 218 */               if (set.size() > 0)
/* 219 */                 return set; 
/*     */             } 
/*     */           } 
/* 222 */           return EnumSet.noneOf(EnumFacing.class);
/*     */         } 
/*     */       } 
/*     */       return this.allowed;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEventFluid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */