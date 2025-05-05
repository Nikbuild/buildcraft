/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.core.IIconProvider;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.IIcon;
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
/*    */ public class WireIconProvider
/*    */   implements IIconProvider
/*    */ {
/*    */   public static final int Texture_Red_Dark = 0;
/*    */   public static final int Texture_Red_Lit = 1;
/*    */   public static final int Texture_Blue_Dark = 2;
/*    */   public static final int Texture_Blue_Lit = 3;
/*    */   public static final int Texture_Green_Dark = 4;
/*    */   public static final int Texture_Green_Lit = 5;
/*    */   public static final int Texture_Yellow_Dark = 6;
/*    */   public static final int Texture_Yellow_Lit = 7;
/*    */   public static final int MAX = 8;
/*    */   @SideOnly(Side.CLIENT)
/*    */   private IIcon[] icons;
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon getIcon(int pipeIconIndex) {
/* 38 */     return this.icons[pipeIconIndex];
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IIconRegister iconRegister) {
/* 44 */     this.icons = new IIcon[8];
/*    */     
/* 46 */     this.icons[0] = iconRegister.func_94245_a("buildcraftcore:misc/texture_red_dark");
/* 47 */     this.icons[1] = iconRegister.func_94245_a("buildcraftcore:misc/texture_red_lit");
/* 48 */     this.icons[2] = iconRegister.func_94245_a("buildcraftcore:misc/texture_blue_dark");
/* 49 */     this.icons[3] = iconRegister.func_94245_a("buildcraftcore:misc/texture_blue_lit");
/* 50 */     this.icons[4] = iconRegister.func_94245_a("buildcraftcore:misc/texture_green_dark");
/* 51 */     this.icons[5] = iconRegister.func_94245_a("buildcraftcore:misc/texture_green_lit");
/* 52 */     this.icons[6] = iconRegister.func_94245_a("buildcraftcore:misc/texture_yellow_dark");
/* 53 */     this.icons[7] = iconRegister.func_94245_a("buildcraftcore:misc/texture_yellow_lit");
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\WireIconProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */