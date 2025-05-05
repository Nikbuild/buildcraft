/*    */ package buildcraft.transport.render;
/*    */ 
/*    */ import buildcraft.transport.utils.TransportUtils;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.particle.EntityFX;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.tileentity.TileEntity;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TileEntityPickupFX
/*    */   extends EntityFX
/*    */ {
/*    */   private Entity entityToPickUp;
/*    */   private TileEntity entityPickingUp;
/* 36 */   private int age = 0;
/* 37 */   private int maxAge = 0;
/*    */ 
/*    */   
/*    */   private double yOffs;
/*    */ 
/*    */   
/*    */   public TileEntityPickupFX(World par1World, EntityItem par2Entity, TileEntity par3Entity) {
/* 44 */     super(par1World, par2Entity.field_70165_t, par2Entity.field_70163_u, par2Entity.field_70161_v, par2Entity.field_70159_w, par2Entity.field_70181_x, par2Entity.field_70179_y);
/* 45 */     this.entityToPickUp = (Entity)par2Entity;
/* 46 */     this.entityPickingUp = par3Entity;
/* 47 */     this.maxAge = 3;
/* 48 */     this.yOffs = TransportUtils.getPipeFloorOf(par2Entity.func_92059_d());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70539_a(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
/* 54 */     float var8 = (this.age + par2) / this.maxAge;
/* 55 */     var8 *= var8;
/* 56 */     double var9 = this.entityToPickUp.field_70165_t;
/* 57 */     double var11 = this.entityToPickUp.field_70163_u;
/* 58 */     double var13 = this.entityToPickUp.field_70161_v;
/* 59 */     double var15 = this.entityPickingUp.field_145851_c + 0.5D;
/* 60 */     double var17 = this.entityPickingUp.field_145848_d + this.yOffs;
/* 61 */     double var19 = this.entityPickingUp.field_145849_e + 0.5D;
/* 62 */     double var21 = var9 + (var15 - var9) * var8;
/* 63 */     double var23 = var11 + (var17 - var11) * var8;
/* 64 */     double var25 = var13 + (var19 - var13) * var8;
/* 65 */     int var30 = func_70070_b(par2);
/* 66 */     int var31 = var30 % 65536;
/* 67 */     int var32 = var30 / 65536;
/* 68 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, var31 / 1.0F, var32 / 1.0F);
/*    */     
/* 70 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 71 */     var21 -= field_70556_an;
/* 72 */     var23 -= field_70554_ao;
/* 73 */     var25 -= field_70555_ap;
/* 74 */     if (RenderManager.field_78727_a.field_78724_e != null) {
/* 75 */       RenderManager.field_78727_a.func_147940_a(this.entityToPickUp, var21, var23, var25, this.entityToPickUp.field_70177_z, par2);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70071_h_() {
/* 85 */     this.age++;
/*    */     
/* 87 */     if (this.age == this.maxAge) {
/* 88 */       func_70106_y();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_70537_b() {
/* 94 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\TileEntityPickupFX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */