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
/*     */ public class TriggerInventoryLevel
/*     */   extends BCStatement
/*     */   implements ITriggerExternal
/*     */ {
/*     */   public TriggerType type;
/*     */   
/*     */   public enum TriggerType
/*     */   {
/*  33 */     BELOW25(0.25F), BELOW50(0.5F), BELOW75(0.75F);
/*     */     public final float level;
/*     */     
/*     */     TriggerType(float level) {
/*  37 */       this.level = level;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TriggerInventoryLevel(TriggerType type) {
/*  44 */     super(new String[] { "buildcraft:inventorylevel." + type.name().toLowerCase(Locale.ENGLISH), "buildcraft.inventorylevel." + type
/*  45 */           .name().toLowerCase(Locale.ENGLISH), "buildcraft.filteredBuffer." + type
/*  46 */           .name().toLowerCase(Locale.ENGLISH) });
/*  47 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/*  52 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minParameters() {
/*  57 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  62 */     return String.format(StringUtils.localize("gate.trigger.inventorylevel.below"), new Object[] { Integer.valueOf((int)(this.type.level * 100.0F)) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection side, IStatementContainer container, IStatementParameter[] parameters) {
/*  68 */     if (parameters == null || parameters.length < 1 || parameters[0] == null) {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     if (tile instanceof IInventory) {
/*  73 */       IInventory inventory = (IInventory)tile;
/*  74 */       ItemStack searchStack = parameters[0].getItemStack();
/*     */       
/*  76 */       if (searchStack == null) {
/*  77 */         return false;
/*     */       }
/*     */       
/*  80 */       int stackSpace = 0;
/*  81 */       int foundItems = 0;
/*  82 */       for (IInvSlot slot : InventoryIterator.getIterable(inventory, side.getOpposite())) {
/*  83 */         if (slot.canPutStackInSlot(searchStack)) {
/*  84 */           ItemStack stackInSlot = slot.getStackInSlot();
/*  85 */           if (stackInSlot == null || StackHelper.canStacksOrListsMerge(stackInSlot, searchStack)) {
/*  86 */             stackSpace++;
/*  87 */             foundItems += (stackInSlot == null) ? 0 : stackInSlot.field_77994_a;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       if (stackSpace > 0) {
/*  93 */         float percentage = foundItems / stackSpace * Math.min(searchStack.func_77976_d(), inventory.func_70297_j_());
/*  94 */         return (percentage < this.type.level);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerIcons(IIconRegister register) {
/* 104 */     this.icon = register.func_94245_a("buildcraftcore:triggers/trigger_inventory_" + this.type.name().toLowerCase());
/*     */   }
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int index) {
/* 109 */     return (IStatementParameter)new StatementParameterItemStack();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\statements\TriggerInventoryLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */