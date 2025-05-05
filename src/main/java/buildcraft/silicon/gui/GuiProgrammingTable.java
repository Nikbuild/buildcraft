/*     */ package buildcraft.silicon.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.CoreIconProvider;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.silicon.TileProgrammingTable;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiProgrammingTable
/*     */   extends GuiAdvancedInterface
/*     */ {
/*  30 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/programming_table.png");
/*     */   private final TileProgrammingTable table;
/*     */   
/*     */   private class LaserTableLedger extends GuiBuildCraft.Ledger {
/*  34 */     int headerColour = 14797103;
/*  35 */     int subheaderColour = 11186104;
/*  36 */     int textColour = 0;
/*     */     public LaserTableLedger() {
/*  38 */       super((GuiBuildCraft)GuiProgrammingTable.this);
/*  39 */       this.maxHeight = 94;
/*  40 */       this.overlayColor = 13921311;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(int x, int y) {
/*  47 */       drawBackground(x, y);
/*     */ 
/*     */       
/*  50 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110576_c);
/*  51 */       drawIcon(BuildCraftCore.iconProvider.getIcon(CoreIconProvider.ENERGY), x + 3, y + 4);
/*     */       
/*  53 */       if (!isFullyOpened()) {
/*     */         return;
/*     */       }
/*     */       
/*  57 */       GuiProgrammingTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.energy"), x + 22, y + 8, this.headerColour);
/*  58 */       GuiProgrammingTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.assemblyCurrentRequired") + ":", x + 22, y + 20, this.subheaderColour);
/*  59 */       GuiProgrammingTable.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf((GuiProgrammingTable.access$200(this.this$0)).clientRequiredEnergy) }), x + 22, y + 32, this.textColour);
/*  60 */       GuiProgrammingTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.stored") + ":", x + 22, y + 44, this.subheaderColour);
/*  61 */       GuiProgrammingTable.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf(GuiProgrammingTable.access$200(this.this$0).getEnergy()) }), x + 22, y + 56, this.textColour);
/*  62 */       GuiProgrammingTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.assemblyRate") + ":", x + 22, y + 68, this.subheaderColour);
/*  63 */       GuiProgrammingTable.this.field_146289_q.func_78276_b(String.format("%.1f RF/t", new Object[] { Float.valueOf(GuiProgrammingTable.access$200(this.this$0).getRecentEnergyAverage() / 100.0F) }), x + 22, y + 80, this.textColour);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTooltip() {
/*  69 */       return String.format("%.1f RF/t", new Object[] { Float.valueOf(GuiProgrammingTable.access$200(this.this$0).getRecentEnergyAverage() / 100.0F) });
/*     */     }
/*     */   }
/*     */   
/*     */   class RecipeSlot
/*     */     extends AdvancedSlot
/*     */   {
/*     */     public ItemStack slot;
/*     */     public int id;
/*     */     
/*     */     public RecipeSlot(int x, int y, int i) {
/*  80 */       super(GuiProgrammingTable.this, x, y);
/*  81 */       this.id = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getItemStack() {
/*  86 */       return this.slot;
/*     */     }
/*     */   }
/*     */   
/*     */   public GuiProgrammingTable(IInventory playerInventory, TileProgrammingTable programmingTable) {
/*  91 */     super(new ContainerProgrammingTable(playerInventory, programmingTable), (IInventory)programmingTable, TEXTURE);
/*     */     
/*  93 */     this.table = programmingTable;
/*  94 */     this.field_146999_f = 176;
/*  95 */     this.field_147000_g = 207;
/*     */     
/*  97 */     for (int j = 0; j < 4; j++) {
/*  98 */       for (int i = 0; i < 6; i++) {
/*  99 */         this.slots.add(new RecipeSlot(43 + 18 * i, 36 + 18 * j, j * 6 + i));
/*     */       }
/*     */     } 
/*     */     
/* 103 */     updateRecipes();
/*     */   }
/*     */   
/*     */   public void updateRecipes() {
/* 107 */     if (this.table.options != null) {
/* 108 */       Iterator<ItemStack> cur = this.table.options.iterator();
/*     */       
/* 110 */       for (AdvancedSlot s : this.slots) {
/* 111 */         if (cur.hasNext()) {
/* 112 */           ((RecipeSlot)s).slot = cur.next(); continue;
/*     */         } 
/* 114 */         ((RecipeSlot)s).slot = null;
/*     */       } 
/*     */     } else {
/*     */       
/* 118 */       for (AdvancedSlot s : this.slots) {
/* 119 */         ((RecipeSlot)s).slot = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 126 */     super.func_146979_b(par1, par2);
/* 127 */     String title = StringUtils.localize("tile.programmingTableBlock.name");
/* 128 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 15, 4210752);
/* 129 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/* 130 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 135 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 136 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 137 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/* 139 */     updateRecipes();
/*     */     
/* 141 */     int i = 0;
/*     */     
/* 143 */     for (AdvancedSlot slot2 : this.slots) {
/* 144 */       RecipeSlot slot = (RecipeSlot)slot2;
/*     */       
/* 146 */       if (slot.slot != null && 
/* 147 */         this.table.optionId == i) {
/* 148 */         func_73729_b(this.field_147003_i + slot.x, this.field_147009_r + slot.y, 196, 1, 16, 16);
/*     */       }
/*     */       
/* 151 */       i++;
/*     */     } 
/*     */     
/* 154 */     int h = this.table.getProgressScaled(70);
/*     */     
/* 156 */     func_73729_b(this.field_147003_i + 164, this.field_147009_r + 36 + 70 - h, 176, 18, 4, h);
/*     */     
/* 158 */     drawBackgroundSlots(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slotClicked(AdvancedSlot aslot, int mouseButton) {
/* 163 */     super.slotClicked(aslot, mouseButton);
/*     */     
/* 165 */     if (aslot instanceof RecipeSlot) {
/* 166 */       RecipeSlot slot = (RecipeSlot)aslot;
/*     */       
/* 168 */       if (slot.slot == null) {
/*     */         return;
/*     */       }
/*     */       
/* 172 */       if (this.table.optionId == slot.id) {
/* 173 */         this.table.rpcSelectOption(-1);
/*     */       } else {
/* 175 */         this.table.rpcSelectOption(slot.id);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initLedgers(IInventory inventory) {
/* 182 */     super.initLedgers(inventory);
/* 183 */     if (!BuildCraftCore.hidePowerNumbers)
/* 184 */       this.ledgerManager.add(new LaserTableLedger()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiProgrammingTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */