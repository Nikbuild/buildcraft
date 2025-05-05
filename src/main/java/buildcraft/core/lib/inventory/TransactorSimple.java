/*     */ package buildcraft.core.lib.inventory;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.core.lib.inventory.filters.IStackFilter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ 
/*     */ public class TransactorSimple
/*     */   extends Transactor
/*     */ {
/*     */   protected IInventory inventory;
/*     */   
/*     */   public TransactorSimple(IInventory inventory) {
/*  27 */     this.inventory = inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public int inject(ItemStack stack, ForgeDirection orientation, boolean doAdd) {
/*  32 */     List<IInvSlot> filledSlots = new ArrayList<IInvSlot>(this.inventory.func_70302_i_());
/*  33 */     List<IInvSlot> emptySlots = new ArrayList<IInvSlot>(this.inventory.func_70302_i_());
/*  34 */     for (IInvSlot slot : InventoryIterator.getIterable(this.inventory, orientation)) {
/*  35 */       if (slot.canPutStackInSlot(stack)) {
/*  36 */         if (slot.getStackInSlot() == null) {
/*  37 */           emptySlots.add(slot); continue;
/*     */         } 
/*  39 */         filledSlots.add(slot);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  44 */     int injected = 0;
/*  45 */     injected = tryPut(stack, filledSlots, injected, doAdd);
/*  46 */     injected = tryPut(stack, emptySlots, injected, doAdd);
/*     */     
/*  48 */     if (injected > 0 && doAdd) {
/*  49 */       this.inventory.func_70296_d();
/*     */     }
/*  51 */     return injected;
/*     */   }
/*     */   
/*     */   private int tryPut(ItemStack stack, List<IInvSlot> slots, int injected, boolean doAdd) {
/*  55 */     int realInjected = injected;
/*     */     
/*  57 */     if (realInjected >= stack.field_77994_a) {
/*  58 */       return realInjected;
/*     */     }
/*     */     
/*  61 */     for (IInvSlot slot : slots) {
/*  62 */       ItemStack stackInSlot = slot.getStackInSlot();
/*  63 */       if (stackInSlot == null || StackHelper.canStacksMerge(stackInSlot, stack)) {
/*  64 */         int used = addToSlot(slot, stack, realInjected, doAdd);
/*  65 */         if (used > 0) {
/*  66 */           realInjected += used;
/*  67 */           if (realInjected >= stack.field_77994_a) {
/*  68 */             return realInjected;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     return realInjected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int addToSlot(IInvSlot slot, ItemStack stack, int injected, boolean doAdd) {
/*  86 */     int available = stack.field_77994_a - injected;
/*  87 */     int max = Math.min(stack.func_77976_d(), this.inventory.func_70297_j_());
/*     */     
/*  89 */     ItemStack stackInSlot = slot.getStackInSlot();
/*  90 */     if (stackInSlot == null) {
/*  91 */       int i = Math.min(available, max);
/*  92 */       if (doAdd) {
/*  93 */         stackInSlot = stack.func_77946_l();
/*  94 */         stackInSlot.field_77994_a = i;
/*  95 */         slot.setStackInSlot(stackInSlot);
/*     */       } 
/*  97 */       return i;
/*     */     } 
/*     */     
/* 100 */     if (!StackHelper.canStacksMerge(stack, stackInSlot)) {
/* 101 */       return 0;
/*     */     }
/*     */     
/* 104 */     int wanted = max - stackInSlot.field_77994_a;
/* 105 */     if (wanted <= 0) {
/* 106 */       return 0;
/*     */     }
/*     */     
/* 109 */     if (wanted > available) {
/* 110 */       wanted = available;
/*     */     }
/*     */     
/* 113 */     if (doAdd) {
/* 114 */       stackInSlot.field_77994_a += wanted;
/* 115 */       slot.setStackInSlot(stackInSlot);
/*     */     } 
/* 117 */     return wanted;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack remove(IStackFilter filter, ForgeDirection orientation, boolean doRemove) {
/* 122 */     for (IInvSlot slot : InventoryIterator.getIterable(this.inventory, orientation)) {
/* 123 */       ItemStack stack = slot.getStackInSlot();
/* 124 */       if (stack != null && slot.canTakeStackFromSlot(stack) && filter.matches(stack)) {
/* 125 */         if (doRemove) {
/* 126 */           return slot.decreaseStackInSlot(1);
/*     */         }
/* 128 */         ItemStack output = stack.func_77946_l();
/* 129 */         output.field_77994_a = 1;
/* 130 */         return output;
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\inventory\TransactorSimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */