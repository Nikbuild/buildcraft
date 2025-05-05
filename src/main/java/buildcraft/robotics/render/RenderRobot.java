/*     */ package buildcraft.robotics.render;
/*     */ 
/*     */ import buildcraft.BuildCraftRobotics;
/*     */ import buildcraft.api.robots.IRobotOverlayItem;
/*     */ import buildcraft.core.DefaultProps;
/*     */ import buildcraft.core.EntityLaser;
/*     */ import buildcraft.core.lib.render.RenderUtils;
/*     */ import buildcraft.core.render.RenderLaser;
/*     */ import buildcraft.robotics.EntityRobot;
/*     */ import buildcraft.robotics.ItemRobot;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderBiped;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.ForgeHooksClient;
/*     */ import net.minecraftforge.client.IItemRenderer;
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
/*     */ 
/*     */ public class RenderRobot
/*     */   extends Render
/*     */   implements IItemRenderer
/*     */ {
/*  51 */   private static final ResourceLocation overlay_red = new ResourceLocation(DefaultProps.TEXTURE_PATH_ROBOTS + "/overlay_side.png");
/*     */   
/*  53 */   private static final ResourceLocation overlay_cyan = new ResourceLocation(DefaultProps.TEXTURE_PATH_ROBOTS + "/overlay_bottom.png");
/*     */ 
/*     */   
/*  56 */   private final EntityItem dummyEntityItem = new EntityItem(null);
/*     */   
/*     */   private final RenderItem customRenderItem;
/*  59 */   private ModelBase model = new ModelBase() {  }
/*     */   ;
/*  61 */   private ModelBase modelHelmet = new ModelBase() {  }
/*     */   ;
/*  63 */   private ModelBase modelSkullOverlay = new ModelBase() {
/*     */     
/*     */     };
/*     */   private ModelRenderer box;
/*  67 */   private Map<String, GameProfile> gameProfileCache = new HashMap<String, GameProfile>(); private ModelRenderer helmetBox; private ModelRenderer skullOverlayBox;
/*     */   
/*     */   public RenderRobot() {
/*  70 */     this.customRenderItem = new RenderItem()
/*     */       {
/*     */         public boolean shouldBob() {
/*  73 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean shouldSpreadItems() {
/*  78 */           return false;
/*     */         }
/*     */       };
/*  81 */     this.customRenderItem.func_76976_a(RenderManager.field_78727_a);
/*     */     
/*  83 */     this.box = new ModelRenderer(this.model, 0, 0);
/*  84 */     this.box.func_78789_a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*  85 */     this.box.func_78793_a(0.0F, 0.0F, 0.0F);
/*  86 */     this.helmetBox = new ModelRenderer(this.modelHelmet, 0, 0);
/*  87 */     this.helmetBox.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
/*  88 */     this.helmetBox.func_78793_a(0.0F, 0.0F, 0.0F);
/*  89 */     this.skullOverlayBox = new ModelRenderer(this.modelSkullOverlay, 32, 0);
/*  90 */     this.skullOverlayBox.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
/*  91 */     this.skullOverlayBox.func_78793_a(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_76986_a(Entity entity, double x, double y, double z, float f, float f1) {
/*  96 */     doRender((EntityRobot)entity, x, y, z, f1);
/*     */   }
/*     */   
/*     */   private void doRender(EntityRobot robot, double x, double y, double z, float partialTicks) {
/* 100 */     GL11.glPushMatrix();
/* 101 */     GL11.glTranslated(x, y, z);
/*     */     
/* 103 */     float robotYaw = interpolateRotation(robot.field_70760_ar, robot.field_70761_aq, partialTicks);
/* 104 */     GL11.glRotatef(-robotYaw, 0.0F, 1.0F, 0.0F);
/*     */     
/* 106 */     if (robot.func_70301_a(0) != null) {
/* 107 */       GL11.glPushMatrix();
/* 108 */       GL11.glTranslatef(-0.125F, 0.0F, -0.125F);
/* 109 */       doRenderItem(robot.func_70301_a(0));
/* 110 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 111 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 114 */     if (robot.func_70301_a(1) != null) {
/* 115 */       GL11.glPushMatrix();
/* 116 */       GL11.glTranslatef(0.125F, 0.0F, -0.125F);
/* 117 */       doRenderItem(robot.func_70301_a(1));
/* 118 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 119 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 122 */     if (robot.func_70301_a(2) != null) {
/* 123 */       GL11.glPushMatrix();
/* 124 */       GL11.glTranslatef(0.125F, 0.0F, 0.125F);
/* 125 */       doRenderItem(robot.func_70301_a(2));
/* 126 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 127 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 130 */     if (robot.func_70301_a(3) != null) {
/* 131 */       GL11.glPushMatrix();
/* 132 */       GL11.glTranslatef(-0.125F, 0.0F, 0.125F);
/* 133 */       doRenderItem(robot.func_70301_a(3));
/* 134 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 135 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 138 */     if (robot.itemInUse != null) {
/* 139 */       GL11.glPushMatrix();
/*     */       
/* 141 */       GL11.glRotatef(robot.itemAngle2, 0.0F, 0.0F, 1.0F);
/*     */       
/* 143 */       if (robot.itemActive) {
/* 144 */         long newDate = (new Date()).getTime();
/* 145 */         robot.itemActiveStage = (robot.itemActiveStage + (float)((newDate - robot.lastUpdateTime) / 10L)) % 45.0F;
/* 146 */         GL11.glRotatef(robot.itemActiveStage, 0.0F, 0.0F, 1.0F);
/* 147 */         robot.lastUpdateTime = newDate;
/*     */       } 
/*     */       
/* 150 */       GL11.glTranslatef(-0.4F, 0.0F, 0.0F);
/* 151 */       GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
/* 152 */       GL11.glScalef(0.8F, 0.8F, 0.8F);
/*     */       
/* 154 */       ItemStack itemstack1 = robot.itemInUse;
/*     */       
/* 156 */       if (itemstack1.func_77973_b().func_77623_v()) {
/* 157 */         for (int k = 0; k < itemstack1.func_77973_b().getRenderPasses(itemstack1.func_77960_j()); k++) {
/* 158 */           RenderUtils.setGLColorFromInt(itemstack1.func_77973_b().func_82790_a(itemstack1, k));
/* 159 */           this.field_76990_c.field_78721_f.func_78443_a((EntityLivingBase)robot, itemstack1, k);
/*     */         } 
/*     */       } else {
/* 162 */         RenderUtils.setGLColorFromInt(itemstack1.func_77973_b().func_82790_a(itemstack1, 0));
/* 163 */         this.field_76990_c.field_78721_f.func_78443_a((EntityLivingBase)robot, itemstack1, 0);
/*     */       } 
/*     */       
/* 166 */       GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 167 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 170 */     if (robot.laser.isVisible) {
/* 171 */       robot.laser.head.x = robot.field_70165_t;
/* 172 */       robot.laser.head.y = robot.field_70163_u;
/* 173 */       robot.laser.head.z = robot.field_70161_v;
/*     */       
/* 175 */       RenderLaser.doRenderLaser(this.field_76990_c.field_78724_e, robot.laser, EntityLaser.LASER_TEXTURES[1]);
/*     */     } 
/*     */     
/* 178 */     if (robot.getTexture() != null) {
/* 179 */       this.field_76990_c.field_78724_e.func_110577_a(robot.getTexture());
/* 180 */       float storagePercent = robot.getBattery().getEnergyStored() / robot.getBattery().getMaxEnergyStored();
/* 181 */       if (robot.field_70737_aN > 0) {
/* 182 */         GL11.glColor3f(1.0F, 0.6F, 0.6F);
/* 183 */         GL11.glRotatef(robot.field_70737_aN * 0.01F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/* 185 */       doRenderRobot(0.0625F, this.field_76990_c.field_78724_e, storagePercent, robot.isActive());
/*     */     } 
/*     */     
/* 188 */     for (ItemStack s : robot.getWearables()) {
/* 189 */       doRenderWearable(robot, this.field_76990_c.field_78724_e, s);
/*     */     }
/*     */     
/* 192 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation func_110775_a(Entity entity) {
/* 197 */     return ((EntityRobot)entity).getTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/* 212 */     if (RenderManager.field_78727_a == null || RenderManager.field_78727_a.field_78724_e == null) {
/*     */       return;
/*     */     }
/*     */     
/* 216 */     GL11.glPushMatrix();
/*     */     
/* 218 */     if (item.func_77973_b() == BuildCraftRobotics.robotItem) {
/* 219 */       ItemRobot robot = (ItemRobot)item.func_77973_b();
/* 220 */       RenderManager.field_78727_a.field_78724_e.func_110577_a(robot.getTextureRobot(item));
/*     */     } 
/*     */     
/* 223 */     if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
/* 224 */       GL11.glTranslated(0.0D, 1.0D, 0.7D);
/* 225 */     } else if (type == IItemRenderer.ItemRenderType.ENTITY) {
/* 226 */       GL11.glScaled(0.6D, 0.6D, 0.6D);
/* 227 */     } else if (type == IItemRenderer.ItemRenderType.INVENTORY) {
/* 228 */       GL11.glScaled(1.5D, 1.5D, 1.5D);
/*     */     } 
/*     */     
/* 231 */     doRenderRobot(0.0625F, RenderManager.field_78727_a.field_78724_e, 0.9F, false);
/*     */     
/* 233 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void doRenderItem(ItemStack stack) {
/* 237 */     float renderScale = 0.5F;
/* 238 */     GL11.glPushMatrix();
/* 239 */     GL11.glTranslatef(0.0F, 0.28F, 0.0F);
/* 240 */     GL11.glScalef(renderScale, renderScale, renderScale);
/* 241 */     this.dummyEntityItem.func_92058_a(stack);
/* 242 */     this.customRenderItem.func_76986_a(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/*     */     
/* 244 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void doRenderWearable(EntityRobot entity, TextureManager textureManager, ItemStack wearable) {
/* 248 */     if (wearable.func_77973_b() instanceof IRobotOverlayItem) {
/* 249 */       ((IRobotOverlayItem)wearable.func_77973_b()).renderRobotOverlay(wearable, textureManager);
/* 250 */     } else if (wearable.func_77973_b() instanceof net.minecraft.item.ItemArmor) {
/* 251 */       GL11.glPushMatrix();
/* 252 */       GL11.glScalef(1.0125F, 1.0125F, 1.0125F);
/* 253 */       GL11.glTranslatef(0.0F, -0.25F, 0.0F);
/* 254 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/* 256 */       int color = wearable.func_77973_b().func_82790_a(wearable, 0);
/* 257 */       if (color != 16777215) {
/* 258 */         GL11.glPushAttrib(16384);
/* 259 */         GL11.glColor3ub((byte)(color >> 16), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF));
/*     */       } 
/*     */       
/* 262 */       textureManager.func_110577_a(RenderBiped.getArmorResource((Entity)entity, wearable, 0, null));
/* 263 */       ModelBiped armorModel = ForgeHooksClient.getArmorModel((EntityLivingBase)entity, wearable, 0, null);
/* 264 */       if (armorModel != null) {
/* 265 */         armorModel.func_78088_a((Entity)entity, 0.0F, 0.0F, 0.0F, -90.0F, 0.0F, 0.0625F);
/*     */         
/* 267 */         if (color != 16777215) {
/* 268 */           GL11.glPopAttrib();
/*     */         }
/*     */       } else {
/* 271 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 272 */         this.helmetBox.func_78785_a(0.0625F);
/*     */         
/* 274 */         if (color != 16777215) {
/* 275 */           func_110776_a(RenderBiped.getArmorResource((Entity)entity, wearable, 0, "overlay"));
/* 276 */           this.helmetBox.func_78785_a(0.0625F);
/* 277 */           GL11.glPopAttrib();
/*     */         } 
/*     */       } 
/*     */       
/* 281 */       GL11.glPopMatrix();
/* 282 */     } else if (wearable.func_77973_b() instanceof net.minecraft.item.ItemSkull) {
/* 283 */       doRenderSkull(wearable);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doRenderSkull(ItemStack wearable) {
/* 288 */     GL11.glPushMatrix();
/* 289 */     GL11.glScalef(1.0125F, 1.0125F, 1.0125F);
/* 290 */     GameProfile gameProfile = null;
/* 291 */     if (wearable.func_77942_o()) {
/* 292 */       NBTTagCompound nbt = wearable.func_77978_p();
/* 293 */       if (nbt.func_74764_b("Name")) {
/* 294 */         gameProfile = this.gameProfileCache.get(nbt.func_74779_i("Name"));
/* 295 */       } else if (nbt.func_150297_b("SkullOwner", 10)) {
/* 296 */         gameProfile = NBTUtil.func_152459_a(nbt.func_74775_l("SkullOwner"));
/* 297 */         nbt.func_74778_a("Name", gameProfile.getName());
/* 298 */         this.gameProfileCache.put(gameProfile.getName(), gameProfile);
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     TileEntitySkullRenderer.field_147536_b.func_152674_a(-0.5F, -0.25F, -0.5F, 1, -90.0F, wearable
/* 303 */         .func_77960_j(), gameProfile);
/* 304 */     if (gameProfile != null) {
/* 305 */       GL11.glTranslatef(0.0F, -0.25F, 0.0F);
/* 306 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 307 */       GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/* 308 */       this.skullOverlayBox.func_78785_a(0.0625F);
/*     */     } 
/* 310 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void doRenderRobot(float factor, TextureManager texManager, float storagePercent, boolean isAsleep) {
/* 314 */     this.box.func_78785_a(factor);
/*     */     
/* 316 */     if (!isAsleep) {
/* 317 */       float lastBrightnessX = OpenGlHelper.lastBrightnessX;
/* 318 */       float lastBrightnessY = OpenGlHelper.lastBrightnessY;
/*     */       
/* 320 */       GL11.glPushMatrix();
/* 321 */       GL11.glEnable(3042);
/* 322 */       GL11.glEnable(3008);
/* 323 */       GL11.glDisable(2896);
/* 324 */       GL11.glBlendFunc(770, 771);
/* 325 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
/*     */       
/* 327 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, storagePercent);
/* 328 */       texManager.func_110577_a(overlay_red);
/* 329 */       this.box.func_78785_a(factor);
/*     */       
/* 331 */       GL11.glDisable(3042);
/*     */       
/* 333 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 334 */       texManager.func_110577_a(overlay_cyan);
/* 335 */       this.box.func_78785_a(factor);
/*     */       
/* 337 */       GL11.glEnable(2896);
/* 338 */       GL11.glPopMatrix();
/*     */       
/* 340 */       OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, lastBrightnessX, lastBrightnessY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolateRotation(float prevRot, float rot, float partialTicks) {
/*     */     float angle;
/* 348 */     for (angle = rot - prevRot; angle < -180.0F; angle += 360.0F);
/*     */ 
/*     */     
/* 351 */     while (angle >= 180.0F) {
/* 352 */       angle -= 360.0F;
/*     */     }
/*     */     
/* 355 */     return prevRot + partialTicks * angle;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\render\RenderRobot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */