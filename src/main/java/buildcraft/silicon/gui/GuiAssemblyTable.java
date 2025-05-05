/*     */ package buildcraft.silicon.gui;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.api.recipes.CraftingResult;
/*     */ import buildcraft.core.CoreIconProvider;
/*     */ import buildcraft.core.lib.gui.AdvancedSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.silicon.TileAssemblyTable;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class GuiAssemblyTable
/*     */   extends GuiAdvancedInterface
/*     */ {
/*  35 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftsilicon:textures/gui/assembly_table.png");
/*     */   private final TileAssemblyTable table;
/*     */   
/*     */   private class LaserTableLedger extends GuiBuildCraft.Ledger {
/*  39 */     int headerColour = 14797103;
/*  40 */     int subheaderColour = 11186104;
/*  41 */     int textColour = 0;
/*     */     public LaserTableLedger() {
/*  43 */       super((GuiBuildCraft)GuiAssemblyTable.this);
/*  44 */       this.maxHeight = 94;
/*  45 */       this.overlayColor = 13921311;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void draw(int x, int y) {
/*  52 */       drawBackground(x, y);
/*     */ 
/*     */       
/*  55 */       (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110576_c);
/*  56 */       drawIcon(BuildCraftCore.iconProvider.getIcon(CoreIconProvider.ENERGY), x + 3, y + 4);
/*     */       
/*  58 */       if (!isFullyOpened()) {
/*     */         return;
/*     */       }
/*     */       
/*  62 */       GuiAssemblyTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.energy"), x + 22, y + 8, this.headerColour);
/*  63 */       GuiAssemblyTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.assemblyCurrentRequired") + ":", x + 22, y + 20, this.subheaderColour);
/*  64 */       GuiAssemblyTable.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf((GuiAssemblyTable.access$200(this.this$0)).clientRequiredEnergy) }), x + 22, y + 32, this.textColour);
/*  65 */       GuiAssemblyTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.stored") + ":", x + 22, y + 44, this.subheaderColour);
/*  66 */       GuiAssemblyTable.this.field_146289_q.func_78276_b(String.format("%d RF", new Object[] { Integer.valueOf(GuiAssemblyTable.access$200(this.this$0).getEnergy()) }), x + 22, y + 56, this.textColour);
/*  67 */       GuiAssemblyTable.this.field_146289_q.func_78261_a(StringUtils.localize("gui.assemblyRate") + ":", x + 22, y + 68, this.subheaderColour);
/*  68 */       GuiAssemblyTable.this.field_146289_q.func_78276_b(String.format("%.1f RF/t", new Object[] { Float.valueOf(GuiAssemblyTable.access$200(this.this$0).getRecentEnergyAverage() / 100.0F) }), x + 22, y + 80, this.textColour);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTooltip() {
/*  74 */       return String.format("%.1f RF/t", new Object[] { Float.valueOf(GuiAssemblyTable.access$200(this.this$0).getRecentEnergyAverage() / 100.0F) });
/*     */     }
/*     */   }
/*     */   
/*     */   class RecipeSlot
/*     */     extends AdvancedSlot
/*     */   {
/*     */     public CraftingResult<ItemStack> crafting;
/*     */     public boolean craftable;
/*     */     
/*     */     public RecipeSlot(int x, int y) {
/*  85 */       super(GuiAssemblyTable.this, x, y);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getItemStack() {
/*  90 */       if (this.crafting != null) {
/*  91 */         return (ItemStack)this.crafting.crafted;
/*     */       }
/*  93 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiAssemblyTable(IInventory playerInventory, TileAssemblyTable assemblyTable) {
/*  99 */     super(new ContainerAssemblyTable(playerInventory, assemblyTable), (IInventory)assemblyTable, TEXTURE);
/*     */     
/* 101 */     this.table = assemblyTable;
/* 102 */     this.field_146999_f = 176;
/* 103 */     this.field_147000_g = 207;
/*     */     
/* 105 */     for (int j = 0; j < 3; j++) {
/* 106 */       for (int i = 0; i < 4; i++) {
/* 107 */         this.slots.add(new RecipeSlot(116 + 18 * j, 36 + 18 * i));
/*     */       }
/*     */     } 
/*     */     
/* 111 */     updateRecipes();
/*     */   }
/*     */   
/*     */   public void updateRecipes() {
/* 115 */     Set<String> addedRecipes = new HashSet<String>();
/* 116 */     List<CraftingResult<ItemStack>> potentialRecipes = this.table.getPotentialOutputs();
/* 117 */     Iterator<CraftingResult<ItemStack>> cur = potentialRecipes.iterator();
/* 118 */     Collection<String> plannedIcons = this.table.plannedOutputIcons.keySet();
/* 119 */     Iterator<String> cur2 = plannedIcons.iterator();
/*     */     
/* 121 */     for (AdvancedSlot s : this.slots) {
/* 122 */       if (cur.hasNext()) {
/* 123 */         ((RecipeSlot)s).crafting = cur.next();
/* 124 */         ((RecipeSlot)s).craftable = true;
/* 125 */         addedRecipes.add(((RecipeSlot)s).crafting.recipe.getId()); continue;
/*     */       } 
/* 127 */       String recipe = null;
/* 128 */       while (cur2.hasNext() && (recipe == null || addedRecipes.contains(recipe))) {
/* 129 */         recipe = cur2.next();
/*     */       }
/* 131 */       if (recipe != null && !addedRecipes.contains(recipe)) {
/* 132 */         ((RecipeSlot)s).crafting = (CraftingResult<ItemStack>)this.table.plannedOutputIcons.get(recipe);
/* 133 */         ((RecipeSlot)s).craftable = false; continue;
/*     */       } 
/* 135 */       ((RecipeSlot)s).crafting = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 143 */     super.func_146979_b(par1, par2);
/* 144 */     String title = StringUtils.localize("tile.assemblyTableBlock.name");
/* 145 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 15, 4210752);
/* 146 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 97, 4210752);
/* 147 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 152 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 153 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 154 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/* 156 */     updateRecipes();
/*     */     
/* 158 */     for (AdvancedSlot slot2 : this.slots) {
/* 159 */       RecipeSlot slot = (RecipeSlot)slot2;
/*     */       
/* 161 */       if (slot.crafting != null) {
/* 162 */         if (!slot.craftable) {
/* 163 */           func_73729_b(this.field_147003_i + slot.x, this.field_147009_r + slot.y, 215, 1, 16, 16); continue;
/* 164 */         }  if (this.table.isAssembling(slot.crafting.recipe)) {
/* 165 */           func_73729_b(this.field_147003_i + slot.x, this.field_147009_r + slot.y, 196, 1, 16, 16); continue;
/* 166 */         }  if (this.table.isPlanned(slot.crafting.recipe)) {
/* 167 */           func_73729_b(this.field_147003_i + slot.x, this.field_147009_r + slot.y, 177, 1, 16, 16);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     int h = this.table.getProgressScaled(70);
/*     */     
/* 174 */     func_73729_b(this.field_147003_i + 86, this.field_147009_r + 36 + 70 - h, 176, 18, 4, h);
/*     */     
/* 176 */     drawBackgroundSlots(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slotClicked(AdvancedSlot aslot, int mouseButton) {
/* 181 */     super.slotClicked(aslot, mouseButton);
/*     */     
/* 183 */     if (aslot instanceof RecipeSlot) {
/* 184 */       boolean select; RecipeSlot slot = (RecipeSlot)aslot;
/*     */       
/* 186 */       if (slot.crafting == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 192 */       if (this.table.isPlanned(slot.crafting.recipe)) {
/* 193 */         select = false;
/*     */       } else {
/* 195 */         select = true;
/*     */       } 
/*     */       
/* 198 */       String id = slot.crafting.recipe.getId();
/*     */       
/* 200 */       this.table.rpcSelectRecipe(id, select);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initLedgers(IInventory inventory) {
/* 206 */     super.initLedgers(inventory);
/* 207 */     if (!BuildCraftCore.hidePowerNumbers)
/* 208 */       this.ledgerManager.add(new LaserTableLedger()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\gui\GuiAssemblyTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */