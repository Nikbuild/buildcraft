/*    */ package buildcraft.factory.render;
/*    */ 
/*    */ import buildcraft.BuildCraftCore;
/*    */ import buildcraft.core.lib.render.IInventoryRenderer;
/*    */ import buildcraft.core.lib.render.ModelFrustum;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
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
/*    */ 
/*    */ public class RenderHopper
/*    */   extends TileEntitySpecialRenderer
/*    */   implements IInventoryRenderer
/*    */ {
/* 28 */   private static final ResourceLocation HOPPER_TEXTURE = new ResourceLocation("buildcraftfactory:textures/blocks/hopperBlock/top.png");
/* 29 */   private static final ResourceLocation HOPPER_MIDDLE_TEXTURE = new ResourceLocation("buildcraftfactory:textures/blocks/hopperBlock/middle.png");
/* 30 */   private ModelBase model = new ModelBase()
/*    */     {
/*    */     
/*    */     };
/*    */   private final ModelRenderer top;
/*    */   
/*    */   public RenderHopper() {
/* 37 */     this.top = new ModelRenderer(this.model, 0, 0);
/* 38 */     this.top.func_78789_a(-8.0F, 1.0F, -8.0F, 16, 7, 16);
/* 39 */     this.top.field_78800_c = 8.0F;
/* 40 */     this.top.field_78797_d = 8.0F;
/* 41 */     this.top.field_78798_e = 8.0F;
/* 42 */     this.middle = new ModelFrustum(this.top, 32, 0, 0.0F, 3.0F, 0.0F, 8, 8, 16, 16, 7, 0.0625F);
/* 43 */     this.bottom = new ModelRenderer(this.model, 0, 23);
/* 44 */     this.bottom.func_78789_a(-3.0F, -8.0F, -3.0F, 6, 3, 6);
/* 45 */     this.bottom.field_78800_c = 8.0F;
/* 46 */     this.bottom.field_78797_d = 8.0F;
/* 47 */     this.bottom.field_78798_e = 8.0F;
/* 48 */     this.field_147501_a = TileEntityRendererDispatcher.field_147556_a;
/*    */   }
/*    */   private final ModelFrustum middle; private final ModelRenderer bottom;
/*    */   
/*    */   public void inventoryRender(double x, double y, double z, float f, float f1) {
/* 53 */     render(x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_147500_a(TileEntity var1, double x, double y, double z, float f) {
/* 58 */     render(x, y, z);
/*    */   }
/*    */   
/*    */   private void render(double x, double y, double z) {
/* 62 */     if (BuildCraftCore.render == BuildCraftCore.RenderMode.NoDynamic) {
/*    */       return;
/*    */     }
/*    */     
/* 66 */     GL11.glPushMatrix();
/* 67 */     GL11.glTranslated(x, y, z);
/* 68 */     func_147499_a(HOPPER_TEXTURE);
/* 69 */     this.top.func_78785_a(0.0625F);
/* 70 */     this.bottom.func_78785_a(0.0625F);
/* 71 */     func_147499_a(HOPPER_MIDDLE_TEXTURE);
/* 72 */     GL11.glTranslated(0.005D, 0.0D, 0.005D);
/* 73 */     GL11.glScaled(0.99D, 1.0D, 0.99D);
/* 74 */     this.middle.render(Tessellator.field_78398_a, 0.0625F);
/*    */     
/* 76 */     GL11.glPopMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\render\RenderHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */