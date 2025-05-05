/*     */ package buildcraft.transport.render;
/*     */ 
/*     */ import buildcraft.BuildCraftTransport;
/*     */ import buildcraft.core.lib.render.RenderEntityBlock;
/*     */ import buildcraft.transport.Pipe;
/*     */ import buildcraft.transport.PipeIconProvider;
/*     */ import buildcraft.transport.PipeTransportPower;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PipeTransportPowerRenderer
/*     */   extends PipeTransportRenderer<PipeTransportPower>
/*     */ {
/*     */   public static final int POWER_STAGES = 256;
/*  20 */   public static final int[] displayPowerList = new int[256];
/*  21 */   public static final int[] displayPowerListOverload = new int[256];
/*  22 */   private static final int[] angleY = new int[] { 0, 0, 270, 90, 0, 180 };
/*  23 */   private static final int[] angleZ = new int[] { 90, 270, 0, 0, 0, 0 };
/*     */   
/*     */   private static final float POWER_MAGIC = 0.7F;
/*     */   private static boolean initialized = false;
/*     */   
/*     */   protected static void clear() {
/*  29 */     if (initialized) {
/*  30 */       for (int i = 0; i < 256; i++) {
/*  31 */         GL11.glDeleteLists(displayPowerList[i], 1);
/*  32 */         GL11.glDeleteLists(displayPowerListOverload[i], 1);
/*     */       } 
/*     */     }
/*     */     
/*  36 */     initialized = false;
/*     */   }
/*     */   
/*     */   public boolean useServerTileIfPresent() {
/*  40 */     return true;
/*     */   }
/*     */   
/*     */   private void initializeDisplayPowerList(World world) {
/*  44 */     if (initialized) {
/*     */       return;
/*     */     }
/*     */     
/*  48 */     initialized = true;
/*     */     
/*  50 */     RenderEntityBlock.RenderInfo block = new RenderEntityBlock.RenderInfo();
/*  51 */     block.texture = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.Power_Normal.ordinal());
/*     */     
/*  53 */     float size = 0.5F;
/*     */     int s;
/*  55 */     for (s = 0; s < 256; s++) {
/*  56 */       displayPowerList[s] = GLAllocation.func_74526_a(1);
/*  57 */       GL11.glNewList(displayPowerList[s], 4864);
/*     */       
/*  59 */       float minSize = 0.005F;
/*     */       
/*  61 */       float unit = (size - minSize) / 2.0F / 256.0F;
/*     */       
/*  63 */       block.minY = 0.5D - (minSize / 2.0F) - (unit * s);
/*  64 */       block.maxY = 0.5D + (minSize / 2.0F) + (unit * s);
/*     */       
/*  66 */       block.minZ = 0.5D - (minSize / 2.0F) - (unit * s);
/*  67 */       block.maxZ = 0.5D + (minSize / 2.0F) + (unit * s);
/*     */       
/*  69 */       block.minX = 0.0D;
/*  70 */       block.maxX = 0.5D + (minSize / 2.0F) + (unit * s);
/*     */       
/*  72 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */       
/*  74 */       GL11.glEndList();
/*     */     } 
/*     */     
/*  77 */     block.texture = BuildCraftTransport.instance.pipeIconProvider.getIcon(PipeIconProvider.TYPE.Power_Overload.ordinal());
/*     */     
/*  79 */     size = 0.5F;
/*     */     
/*  81 */     for (s = 0; s < 256; s++) {
/*  82 */       displayPowerListOverload[s] = GLAllocation.func_74526_a(1);
/*  83 */       GL11.glNewList(displayPowerListOverload[s], 4864);
/*     */       
/*  85 */       float minSize = 0.005F;
/*     */       
/*  87 */       float unit = (size - minSize) / 2.0F / 256.0F;
/*     */       
/*  89 */       block.minY = 0.5D - (minSize / 2.0F) - (unit * s);
/*  90 */       block.maxY = 0.5D + (minSize / 2.0F) + (unit * s);
/*     */       
/*  92 */       block.minZ = 0.5D - (minSize / 2.0F) - (unit * s);
/*  93 */       block.maxZ = 0.5D + (minSize / 2.0F) + (unit * s);
/*     */       
/*  95 */       block.minX = 0.0D;
/*  96 */       block.maxX = 0.5D + (minSize / 2.0F) + (unit * s);
/*     */       
/*  98 */       RenderEntityBlock.INSTANCE.renderBlock(block);
/*     */       
/* 100 */       GL11.glEndList();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Pipe<PipeTransportPower> pipe, double x, double y, double z, float f) {
/* 106 */     initializeDisplayPowerList(pipe.container.func_145831_w());
/*     */     
/* 108 */     PipeTransportPower pow = (PipeTransportPower)pipe.transport;
/*     */     
/* 110 */     GL11.glPushMatrix();
/* 111 */     GL11.glPushAttrib(8192);
/* 112 */     GL11.glDisable(2896);
/*     */     
/* 114 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/*     */     
/* 116 */     bindTexture(TextureMap.field_110575_b);
/*     */     
/* 118 */     int[] displayList = (pow.overload > 0) ? displayPowerListOverload : displayPowerList;
/*     */     
/* 120 */     for (int side = 0; side < 6; side++) {
/* 121 */       int stage = (int)Math.ceil(Math.pow(pow.displayPower[side], 0.699999988079071D));
/* 122 */       if (stage >= 1 && 
/* 123 */         pipe.container.isPipeConnected(ForgeDirection.getOrientation(side))) {
/*     */ 
/*     */ 
/*     */         
/* 127 */         GL11.glPushMatrix();
/*     */         
/* 129 */         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 130 */         GL11.glRotatef(angleY[side], 0.0F, 1.0F, 0.0F);
/* 131 */         GL11.glRotatef(angleZ[side], 0.0F, 0.0F, 1.0F);
/* 132 */         float scale = 1.0F - side * 1.0E-4F;
/* 133 */         GL11.glScalef(scale, scale, scale);
/* 134 */         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/*     */         
/* 136 */         if (stage < displayList.length) {
/* 137 */           GL11.glCallList(displayList[stage]);
/*     */         } else {
/* 139 */           GL11.glCallList(displayList[displayList.length - 1]);
/*     */         } 
/*     */         
/* 142 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */     
/* 170 */     GL11.glPopAttrib();
/* 171 */     GL11.glPopMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeTransportPowerRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */