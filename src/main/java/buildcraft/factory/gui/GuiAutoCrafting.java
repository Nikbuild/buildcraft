/*    */ package buildcraft.factory.gui;
/*    */ 
/*    */ import buildcraft.core.lib.gui.GuiBuildCraft;
/*    */ import buildcraft.core.lib.utils.StringUtils;
/*    */ import buildcraft.factory.TileAutoWorkbench;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiAutoCrafting
/*    */   extends GuiBuildCraft
/*    */ {
/* 23 */   public static final ResourceLocation TEXTURE = new ResourceLocation("buildcraftfactory:textures/gui/autobench.png");
/*    */   private TileAutoWorkbench bench;
/*    */   
/*    */   public GuiAutoCrafting(InventoryPlayer inventoryplayer, World world, TileAutoWorkbench tile) {
/* 27 */     super(new ContainerAutoWorkbench(inventoryplayer, tile), (IInventory)tile, TEXTURE);
/* 28 */     this.bench = tile;
/* 29 */     this.field_146999_f = 176;
/* 30 */     this.field_147000_g = 197;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 35 */     String title = StringUtils.localize("tile.autoWorkbenchBlock.name");
/* 36 */     this.field_146289_q.func_78276_b(title, getCenteredOffset(title), 6, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(StringUtils.localize("gui.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int mouseX, int mouseY) {
/* 42 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 43 */     this.field_146297_k.field_71446_o.func_110577_a(TEXTURE);
/* 44 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/* 45 */     if (this.bench.progress > 0) {
/* 46 */       int progress = this.bench.getProgressScaled(23);
/* 47 */       func_73729_b(this.field_147003_i + 89, this.field_147009_r + 45, 176, 0, progress + 1, 12);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\gui\GuiAutoCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */