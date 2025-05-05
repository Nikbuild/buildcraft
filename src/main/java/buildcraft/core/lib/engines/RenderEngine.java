/*     */ package buildcraft.core.lib.engines;
/*     */ 
/*     */ import buildcraft.BuildCraftCore;
/*     */ import buildcraft.core.lib.render.IInventoryRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
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
/*     */ 
/*     */ public class RenderEngine
/*     */   extends TileEntitySpecialRenderer
/*     */   implements IInventoryRenderer
/*     */ {
/*  28 */   private static final float[] angleMap = new float[6];
/*     */   
/*     */   static {
/*  31 */     angleMap[ForgeDirection.EAST.ordinal()] = -1.5707964F;
/*  32 */     angleMap[ForgeDirection.WEST.ordinal()] = 1.5707964F;
/*  33 */     angleMap[ForgeDirection.UP.ordinal()] = 0.0F;
/*  34 */     angleMap[ForgeDirection.DOWN.ordinal()] = 3.1415927F;
/*  35 */     angleMap[ForgeDirection.SOUTH.ordinal()] = 1.5707964F;
/*  36 */     angleMap[ForgeDirection.NORTH.ordinal()] = -1.5707964F;
/*     */   }
/*     */   
/*  39 */   private ModelBase model = new ModelBase()
/*     */     {
/*     */     
/*     */     };
/*     */   private ModelRenderer box;
/*     */   private ModelRenderer trunk;
/*     */   private ModelRenderer movingBox;
/*     */   private ModelRenderer chamber;
/*     */   private ResourceLocation baseTexture;
/*     */   private ResourceLocation chamberTexture;
/*     */   private ResourceLocation trunkTexture;
/*     */   
/*     */   public RenderEngine() {
/*  52 */     this.box = new ModelRenderer(this.model, 0, 1);
/*  53 */     this.box.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 4, 16);
/*  54 */     this.box.field_78800_c = 8.0F;
/*  55 */     this.box.field_78797_d = 8.0F;
/*  56 */     this.box.field_78798_e = 8.0F;
/*     */     
/*  58 */     this.trunk = new ModelRenderer(this.model, 1, 1);
/*  59 */     this.trunk.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 12, 8);
/*  60 */     this.trunk.field_78800_c = 8.0F;
/*  61 */     this.trunk.field_78797_d = 8.0F;
/*  62 */     this.trunk.field_78798_e = 8.0F;
/*     */     
/*  64 */     this.movingBox = new ModelRenderer(this.model, 0, 1);
/*  65 */     this.movingBox.func_78789_a(-8.0F, -4.0F, -8.0F, 16, 4, 16);
/*  66 */     this.movingBox.field_78800_c = 8.0F;
/*  67 */     this.movingBox.field_78797_d = 8.0F;
/*  68 */     this.movingBox.field_78798_e = 8.0F;
/*     */     
/*  70 */     this.chamber = new ModelRenderer(this.model, 1, 1);
/*  71 */     this.chamber.func_78789_a(-5.0F, -4.0F, -5.0F, 10, 2, 10);
/*  72 */     this.chamber.field_78800_c = 8.0F;
/*  73 */     this.chamber.field_78797_d = 8.0F;
/*  74 */     this.chamber.field_78798_e = 8.0F;
/*     */   }
/*     */   
/*     */   public RenderEngine(ResourceLocation baseTexture, ResourceLocation chamberTexture, ResourceLocation trunkTexture) {
/*  78 */     this();
/*  79 */     this.baseTexture = baseTexture;
/*  80 */     this.chamberTexture = chamberTexture;
/*  81 */     this.trunkTexture = trunkTexture;
/*  82 */     this.field_147501_a = TileEntityRendererDispatcher.field_147556_a;
/*     */   }
/*     */   
/*     */   public RenderEngine(TileEngineBase engine) {
/*  86 */     this(engine.getBaseTexture(), engine.getChamberTexture(), engine.getTrunkTexture(TileEngineBase.EnergyStage.BLUE));
/*     */   }
/*     */ 
/*     */   
/*     */   public void inventoryRender(double x, double y, double z, float f, float f1) {
/*  91 */     render(0.25F, ForgeDirection.UP, this.baseTexture, this.chamberTexture, this.trunkTexture, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147500_a(TileEntity tileentity, double x, double y, double z, float f) {
/*  96 */     TileEngineBase engine = (TileEngineBase)tileentity;
/*     */     
/*  98 */     if (engine != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       render(engine.progress, engine.orientation, engine.getBaseTexture(), engine.getChamberTexture(), engine.getTrunkTexture(engine.getEnergyStage()), x, y, z); } 
/*     */   }
/*     */   
/*     */   private void render(float progress, ForgeDirection orientation, ResourceLocation baseTexture, ResourceLocation chamberTexture, ResourceLocation trunkTexture, double x, double y, double z) {
/*     */     float step;
/* 109 */     if (BuildCraftCore.render == BuildCraftCore.RenderMode.NoDynamic) {
/*     */       return;
/*     */     }
/*     */     
/* 113 */     GL11.glPushMatrix();
/* 114 */     GL11.glPushAttrib(8192);
/* 115 */     GL11.glEnable(2896);
/* 116 */     GL11.glDisable(3042);
/* 117 */     GL11.glEnable(2884);
/* 118 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*     */     
/* 120 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (progress > 0.5D) {
/* 125 */       step = 7.99F - (progress - 0.5F) * 2.0F * 7.99F;
/*     */     } else {
/* 127 */       step = progress * 2.0F * 7.99F;
/*     */     } 
/*     */     
/* 130 */     float translatefact = step / 16.0F;
/*     */     
/* 132 */     float[] angle = { 0.0F, 0.0F, 0.0F };
/* 133 */     float[] translate = { orientation.offsetX, orientation.offsetY, orientation.offsetZ };
/*     */     
/* 135 */     switch (orientation) {
/*     */       case EAST:
/*     */       case WEST:
/*     */       case DOWN:
/* 139 */         angle[2] = angleMap[orientation.ordinal()];
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 144 */         angle[0] = angleMap[orientation.ordinal()];
/*     */         break;
/*     */     } 
/*     */     
/* 148 */     this.box.field_78795_f = angle[0];
/* 149 */     this.box.field_78796_g = angle[1];
/* 150 */     this.box.field_78808_h = angle[2];
/*     */     
/* 152 */     this.trunk.field_78795_f = angle[0];
/* 153 */     this.trunk.field_78796_g = angle[1];
/* 154 */     this.trunk.field_78808_h = angle[2];
/*     */     
/* 156 */     this.movingBox.field_78795_f = angle[0];
/* 157 */     this.movingBox.field_78796_g = angle[1];
/* 158 */     this.movingBox.field_78808_h = angle[2];
/*     */     
/* 160 */     this.chamber.field_78795_f = angle[0];
/* 161 */     this.chamber.field_78796_g = angle[1];
/* 162 */     this.chamber.field_78808_h = angle[2];
/*     */     
/* 164 */     float factor = 0.0625F;
/*     */     
/* 166 */     func_147499_a(baseTexture);
/*     */     
/* 168 */     this.box.func_78785_a(factor);
/*     */     
/* 170 */     GL11.glTranslatef(translate[0] * translatefact, translate[1] * translatefact, translate[2] * translatefact);
/* 171 */     this.movingBox.func_78785_a(factor);
/* 172 */     GL11.glTranslatef(-translate[0] * translatefact, -translate[1] * translatefact, -translate[2] * translatefact);
/*     */     
/* 174 */     func_147499_a(chamberTexture);
/*     */     
/* 176 */     float chamberf = 0.125F;
/* 177 */     int chamberc = ((int)step + 4) / 2;
/*     */     
/* 179 */     for (int i = 0; i <= step + 2.0F; i += 2) {
/* 180 */       this.chamber.func_78785_a(factor);
/* 181 */       GL11.glTranslatef(translate[0] * chamberf, translate[1] * chamberf, translate[2] * chamberf);
/*     */     } 
/*     */     
/* 184 */     GL11.glTranslatef(-translate[0] * chamberf * chamberc, -translate[1] * chamberf * chamberc, -translate[2] * chamberf * chamberc);
/*     */     
/* 186 */     func_147499_a(trunkTexture);
/*     */     
/* 188 */     this.trunk.func_78785_a(factor);
/*     */     
/* 190 */     GL11.glPopAttrib();
/* 191 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\lib\engines\RenderEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */