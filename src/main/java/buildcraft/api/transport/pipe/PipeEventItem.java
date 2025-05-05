/*     */ package buildcraft.api.transport.pipe;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PipeEventItem
/*     */   extends PipeEvent
/*     */ {
/*     */   public final IFlowItems flow;
/*     */   
/*     */   protected PipeEventItem(IPipeHolder holder, IFlowItems flow) {
/*  26 */     super(holder);
/*  27 */     this.flow = flow;
/*     */   }
/*     */   
/*     */   protected PipeEventItem(boolean canBeCancelled, IPipeHolder holder, IFlowItems flow) {
/*  31 */     super(canBeCancelled, holder);
/*  32 */     this.flow = flow;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TryInsert
/*     */     extends PipeEventItem
/*     */   {
/*     */     public final EnumDyeColor colour;
/*     */ 
/*     */     
/*     */     public final EnumFacing from;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public final ItemStack attempting;
/*     */ 
/*     */     
/*     */     public int accepted;
/*     */ 
/*     */     
/*     */     public TryInsert(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, EnumFacing from, @Nonnull ItemStack attempting) {
/*  54 */       super(true, holder, flow);
/*  55 */       this.colour = colour;
/*  56 */       this.from = from;
/*  57 */       this.attempting = attempting;
/*  58 */       this.accepted = attempting.func_190916_E();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void cancel() {
/*  64 */       super.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class ReachDest extends PipeEventItem {
/*     */     public EnumDyeColor colour;
/*     */     @Nonnull
/*     */     private ItemStack stack;
/*     */     
/*     */     public ReachDest(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, @Nonnull ItemStack stack) {
/*  74 */       super(holder, flow);
/*  75 */       this.colour = colour;
/*  76 */       this.stack = stack;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public ItemStack getStack() {
/*  81 */       return this.stack;
/*     */     }
/*     */     
/*     */     public void setStack(ItemStack stack) {
/*  85 */       if (stack == null) {
/*  86 */         throw new NullPointerException("stack");
/*     */       }
/*  88 */       this.stack = stack;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OnInsert
/*     */     extends ReachDest
/*     */   {
/*     */     public final EnumFacing from;
/*     */     
/*     */     public OnInsert(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, @Nonnull ItemStack stack, EnumFacing from) {
/*  98 */       super(holder, flow, colour, stack);
/*  99 */       this.from = from;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ReachCenter
/*     */     extends ReachDest {
/*     */     public final EnumFacing from;
/*     */     
/*     */     public ReachCenter(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, @Nonnull ItemStack stack, EnumFacing from) {
/* 108 */       super(holder, flow, colour, stack);
/* 109 */       this.from = from;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ReachEnd
/*     */     extends ReachDest {
/*     */     public final EnumFacing to;
/*     */     
/*     */     public ReachEnd(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, @Nonnull ItemStack stack, EnumFacing to) {
/* 118 */       super(holder, flow, colour, stack);
/* 119 */       this.to = to;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SideCheck
/*     */     extends PipeEventItem
/*     */   {
/*     */     public final EnumDyeColor colour;
/*     */ 
/*     */     
/*     */     public final EnumFacing from;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public final ItemStack stack;
/*     */ 
/*     */     
/* 139 */     private final int[] priority = new int[6];
/* 140 */     private final EnumSet<EnumFacing> allowed = EnumSet.allOf(EnumFacing.class);
/*     */     
/*     */     public SideCheck(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, EnumFacing from, @Nonnull ItemStack stack) {
/* 143 */       super(holder, flow);
/* 144 */       this.colour = colour;
/* 145 */       this.from = from;
/* 146 */       this.stack = stack;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAllowed(EnumFacing side) {
/* 153 */       return this.allowed.contains(side);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void disallow(EnumFacing... sides) {
/* 159 */       for (EnumFacing side : sides) {
/* 160 */         this.allowed.remove(side);
/*     */       }
/*     */     }
/*     */     
/*     */     public void disallowAll(Collection<EnumFacing> sides) {
/* 165 */       this.allowed.removeAll(sides);
/*     */     }
/*     */     
/*     */     public void disallowAllExcept(EnumFacing... sides) {
/* 169 */       this.allowed.retainAll(Lists.newArrayList((Object[])sides));
/*     */     }
/*     */     
/*     */     public void disallowAll() {
/* 173 */       this.allowed.clear();
/*     */     }
/*     */     
/*     */     public void increasePriority(EnumFacing side) {
/* 177 */       increasePriority(side, 1);
/*     */     }
/*     */     
/*     */     public void increasePriority(EnumFacing side, int by) {
/* 181 */       this.priority[side.ordinal()] = this.priority[side.ordinal()] - by;
/*     */     }
/*     */     
/*     */     public void decreasePriority(EnumFacing side) {
/* 185 */       decreasePriority(side, 1);
/*     */     }
/*     */     
/*     */     public void decreasePriority(EnumFacing side, int by) {
/* 189 */       increasePriority(side, -by);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<EnumSet<EnumFacing>> getOrder() {
/* 194 */       switch (this.allowed.size()) {
/*     */         case 0:
/* 196 */           return (List<EnumSet<EnumFacing>>)ImmutableList.of();
/*     */         case 1:
/* 198 */           return (List<EnumSet<EnumFacing>>)ImmutableList.of(this.allowed);
/*     */       } 
/*     */       
/* 201 */       int val = this.priority[0];
/* 202 */       for (int i = 1; i < this.priority.length; last++) {
/* 203 */         int last; if (this.priority[i] != val) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 211 */           int[] ordered = Arrays.copyOf(this.priority, 6);
/* 212 */           Arrays.sort(ordered);
/* 213 */           last = 0;
/* 214 */           List<EnumSet<EnumFacing>> list = Lists.newArrayList();
/* 215 */           for (int j = 0; j < 6; j++) {
/* 216 */             int current = ordered[j];
/* 217 */             if (j == 0 || current != last) {
/*     */ 
/*     */               
/* 220 */               last = current;
/* 221 */               EnumSet<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/* 222 */               for (EnumFacing face : EnumFacing.field_82609_l) {
/* 223 */                 if (this.allowed.contains(face) && 
/* 224 */                   this.priority[face.ordinal()] == current) {
/* 225 */                   set.add(face);
/*     */                 }
/*     */               } 
/*     */               
/* 229 */               if (set.size() > 0)
/* 230 */                 list.add(set); 
/*     */             } 
/*     */           } 
/* 233 */           return list;
/*     */         } 
/*     */       } 
/*     */       return (List<EnumSet<EnumFacing>>)ImmutableList.of(this.allowed);
/*     */     } }
/*     */   
/*     */   public static class TryBounce extends PipeEventItem {
/*     */     public final EnumDyeColor colour;
/*     */     public final EnumFacing from;
/*     */     @Nonnull
/*     */     public final ItemStack stack;
/*     */     public boolean canBounce = false;
/*     */     
/*     */     public TryBounce(IPipeHolder holder, IFlowItems flow, EnumDyeColor colour, EnumFacing from, @Nonnull ItemStack stack) {
/* 247 */       super(holder, flow);
/* 248 */       this.colour = colour;
/* 249 */       this.from = from;
/* 250 */       this.stack = stack;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Drop extends PipeEventItem {
/*     */     private final EntityItem entity;
/*     */     
/*     */     public Drop(IPipeHolder holder, IFlowItems flow, EntityItem entity) {
/* 258 */       super(holder, flow);
/* 259 */       this.entity = entity;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public ItemStack getStack() {
/* 264 */       ItemStack item = this.entity.func_92059_d();
/* 265 */       return item.func_190926_b() ? ItemStack.field_190927_a : item;
/*     */     }
/*     */     
/*     */     public void setStack(ItemStack stack) {
/* 269 */       if (stack == null)
/* 270 */         throw new NullPointerException("stack"); 
/* 271 */       if (stack.func_190926_b()) {
/* 272 */         this.entity.func_92058_a(ItemStack.field_190927_a);
/*     */       } else {
/* 274 */         this.entity.func_92058_a(stack);
/*     */       } 
/*     */     }
/*     */     
/*     */     public EntityItem getEntity() {
/* 279 */       return this.entity;
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class OrderedEvent
/*     */     extends PipeEventItem {
/*     */     public final List<EnumSet<EnumFacing>> orderedDestinations;
/*     */     
/*     */     public OrderedEvent(IPipeHolder holder, IFlowItems flow, List<EnumSet<EnumFacing>> orderedDestinations) {
/* 288 */       super(holder, flow);
/* 289 */       this.orderedDestinations = orderedDestinations;
/*     */     }
/*     */     
/*     */     public EnumSet<EnumFacing> getAllPossibleDestinations() {
/* 293 */       EnumSet<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/* 294 */       for (EnumSet<EnumFacing> e : this.orderedDestinations) {
/* 295 */         set.addAll(e);
/*     */       }
/* 297 */       return set;
/*     */     }
/*     */     
/*     */     public ImmutableList<EnumFacing> generateRandomOrder() {
/* 301 */       ImmutableList.Builder<EnumFacing> builder = ImmutableList.builder();
/* 302 */       for (EnumSet<EnumFacing> set : this.orderedDestinations) {
/* 303 */         List<EnumFacing> faces = new ArrayList<>(set);
/* 304 */         Collections.shuffle(faces);
/* 305 */         builder.addAll(faces);
/*     */       } 
/* 307 */       return builder.build();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Split
/*     */     extends OrderedEvent
/*     */   {
/* 316 */     public final List<PipeEventItem.ItemEntry> items = new ArrayList<>();
/*     */     
/*     */     public Split(IPipeHolder holder, IFlowItems flow, List<EnumSet<EnumFacing>> order, PipeEventItem.ItemEntry toSplit) {
/* 319 */       super(holder, flow, order);
/* 320 */       this.items.add(toSplit);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FindDest
/*     */     extends OrderedEvent
/*     */   {
/*     */     public final ImmutableList<PipeEventItem.ItemEntry> items;
/*     */     
/*     */     public FindDest(IPipeHolder holder, IFlowItems flow, List<EnumSet<EnumFacing>> orderedDestinations, ImmutableList<PipeEventItem.ItemEntry> items) {
/* 331 */       super(holder, flow, orderedDestinations);
/* 332 */       this.items = items;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ModifySpeed
/*     */     extends PipeEventItem {
/*     */     public final PipeEventItem.ItemEntry item;
/*     */     public final double currentSpeed;
/* 340 */     public double targetSpeed = 0.0D;
/* 341 */     public double maxSpeedChange = 0.0D;
/*     */     
/*     */     public ModifySpeed(IPipeHolder holder, IFlowItems flow, PipeEventItem.ItemEntry item, double initSpeed) {
/* 344 */       super(holder, flow);
/* 345 */       this.item = item;
/* 346 */       this.currentSpeed = initSpeed;
/*     */     }
/*     */     
/*     */     public void modifyTo(double target, double maxDelta) {
/* 350 */       this.targetSpeed = target;
/* 351 */       this.maxSpeedChange = maxDelta;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ItemEntry
/*     */   {
/*     */     public final EnumDyeColor colour;
/*     */     @Nonnull
/*     */     public final ItemStack stack;
/*     */     public final EnumFacing from;
/*     */     @Nullable
/*     */     public List<EnumFacing> to;
/*     */     
/*     */     public ItemEntry(EnumDyeColor colour, @Nonnull ItemStack stack, EnumFacing from) {
/* 366 */       this.colour = colour;
/* 367 */       this.stack = stack;
/* 368 */       this.from = from;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeEventItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */