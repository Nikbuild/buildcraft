/*     */ package buildcraft.factory.gui;
/*     */ 
/*     */ import buildcraft.api.recipes.CraftingResult;
/*     */ import buildcraft.core.lib.fluids.SingleUseTank;
/*     */ import buildcraft.core.lib.gui.FluidSlot;
/*     */ import buildcraft.core.lib.gui.GuiAdvancedInterface;
/*     */ import buildcraft.core.lib.utils.StringUtils;
/*     */ import buildcraft.factory.TileRefinery;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
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
/*     */ 
/*     */ public class GuiRefinery
/*     */   extends GuiAdvancedInterface
/*     */ {
/*  28 */   private static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftfactory:textures/gui/refinery_filter.png");
/*     */   private final ContainerRefinery container;
/*     */   
/*     */   public GuiRefinery(InventoryPlayer inventory, TileRefinery refinery) {
/*  32 */     super(new ContainerRefinery(inventory, refinery), null, TEXTURE);
/*     */     
/*  34 */     this.field_146999_f = 175;
/*  35 */     this.field_147000_g = 207;
/*     */     
/*  37 */     this.container = (ContainerRefinery)this.field_147002_h;
/*     */     
/*  39 */     this.slots.add(new FluidSlot(this, 38, 54));
/*  40 */     this.slots.add(new FluidSlot(this, 126, 54));
/*  41 */     this.slots.add(new FluidSlot(this, 82, 54));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/*  46 */     String title = StringUtils.localize("tile.refineryBlock.name");
/*  47 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/*  48 */     this.field_146289_q.func_78276_b("->", 63, 59, 4210752);
/*  49 */     this.field_146289_q.func_78276_b("<-", 106, 59, 4210752);
/*  50 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/*     */     
/*  52 */     drawTooltipForSlotAt(par1, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/*  57 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  58 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/*  59 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */     
/*  61 */     updateSlots();
/*  62 */     drawBackgroundSlots(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int i, int j, int k) {
/*  67 */     super.func_73864_a(i, j, k);
/*     */     
/*  69 */     int position = getSlotIndexAtLocation(i, j);
/*     */     
/*  71 */     if (position >= 0 && position < 2) {
/*  72 */       if (k == 0) {
/*  73 */         if (!func_146272_n()) {
/*  74 */           FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(this.field_146297_k.field_71439_g.field_71071_by.func_70445_o());
/*     */           
/*  76 */           if (liquid == null) {
/*     */             return;
/*     */           }
/*     */           
/*  80 */           this.container.setFilter(position, liquid.getFluid());
/*  81 */           ((SingleUseTank)this.container.refinery.tankManager.get(position)).colorRenderCache = liquid.getFluid().getColor(liquid);
/*     */         } else {
/*  83 */           this.container.setFilter(position, null);
/*  84 */           ((SingleUseTank)this.container.refinery.tankManager.get(position)).colorRenderCache = 16777215;
/*     */         }
/*     */       
/*  87 */       } else if (position == 0) {
/*  88 */         this.container.setFilter(position, this.container.refinery.tanks[0].getFluidType());
/*  89 */       } else if (position == 1) {
/*  90 */         this.container.setFilter(position, this.container.refinery.tanks[1].getFluidType());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSlots() {
/*  97 */     Fluid filter0 = this.container.getFilter(0);
/*  98 */     Fluid filter1 = this.container.getFilter(1);
/*     */     
/* 100 */     ((FluidSlot)this.slots.get(0)).fluid = filter0;
/* 101 */     ((FluidSlot)this.slots.get(0)).colorRenderCache = (this.container.refinery.tanks[0]).colorRenderCache;
/* 102 */     ((FluidSlot)this.slots.get(1)).fluid = filter1;
/* 103 */     ((FluidSlot)this.slots.get(1)).colorRenderCache = (this.container.refinery.tanks[1]).colorRenderCache;
/*     */     
/* 105 */     CraftingResult<FluidStack> crafting = this.container.refinery.craftingResult;
/*     */     
/* 107 */     if (crafting != null) {
/* 108 */       ((FluidSlot)this.slots.get(2)).fluid = ((FluidStack)crafting.crafted).getFluid();
/* 109 */       ((FluidSlot)this.slots.get(2)).colorRenderCache = ((FluidStack)crafting.crafted).getFluid().getColor((FluidStack)crafting.crafted);
/*     */     } else {
/* 111 */       ((FluidSlot)this.slots.get(2)).fluid = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\GuiRefinery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */