/*     */ package buildcraft.core.statements;
/*     */ 
/*     */ import buildcraft.api.core.IInvSlot;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import buildcraft.api.statements.StatementParameterItemStack;
/*     */ import buildcraft.core.lib.inventory.InventoryIterator;
/*     */ import buildcraft.core.lib.inventory.StackHelper;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
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
/*     */ public class TriggerInventory
/*     */   extends BCStatement
/*     */   implements ITriggerExternal
/*     */ {
/*     */   public State state;
/*     */   
/*     */   public enum State
/*     */   {
/*  34 */     Empty, Contains, Space, Full;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TriggerInventory(State state) {
/*  40 */     super(new String[] { "buildcraft:inventory." + state.name().toLowerCase(Locale.ENGLISH), "buildcraft.inventory." + state.name().toLowerCase(Locale.ENGLISH) });
/*     */     
/*  42 */     this.state = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  47 */     return (this.state == State.Contains || this.state == State.Space) ? 1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  52 */     return StringUtils.localize("gate.trigger.inventory." + this.state.name().toLowerCase(Locale.ENGLISH));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection side, IStatementContainer container, IStatementParameter[] parameters) {
/*  57 */     ItemStack searchedStack = null;
/*     */     
/*  59 */     if (parameters != null && parameters.length >= 1 && parameters[0] != null) {
/*  60 */       searchedStack = parameters[0].getItemStack();
/*     */     }
/*     */     
/*  63 */     if (tile instanceof IInventory) {
/*  64 */       int i, j; boolean hasSlots = false;
/*  65 */       boolean foundItems = false;
/*  66 */       boolean foundSpace = false;
/*     */       
/*  68 */       for (IInvSlot slot : InventoryIterator.getIterable((IInventory)tile, side.getOpposite())) {
/*  69 */         hasSlots = true;
/*  70 */         ItemStack stack = slot.getStackInSlot();
/*     */ 
/*     */         
/*  73 */         i = foundItems | ((stack != null && (searchedStack == null || StackHelper.canStacksOrListsMerge(stack, searchedStack))) ? 1 : 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  79 */         j = foundSpace | (((stack == null || (StackHelper.canStacksOrListsMerge(stack, searchedStack) && stack.field_77994_a < stack.func_77976_d())) && (searchedStack == null || searchedStack.func_77973_b() instanceof buildcraft.core.ItemList || slot.canPutStackInSlot(searchedStack))) ? 1 : 0);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       if (!hasSlots) {
/*  87 */         return false;
/*     */       }
/*     */       
/*  90 */       switch (this.state) {
/*     */         case Empty:
/*  92 */           return (i == 0);
/*     */         case Contains:
/*  94 */           return i;
/*     */         case Space:
/*  96 */           return j;
/*     */       } 
/*  98 */       return (j == 0);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister register) {
/* 108 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_inventory_" + this.state.name().toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/* 113 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */