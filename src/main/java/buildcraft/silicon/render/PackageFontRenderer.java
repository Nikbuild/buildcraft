/*    */ package buildcraft.silicon.render;
/*    */ 
/*    */ import buildcraft.core.lib.utils.NBTUtils;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class PackageFontRenderer
/*    */   extends FontRenderer
/*    */ {
/* 19 */   private static final RenderItem itemRender = new RenderItem();
/* 20 */   private static final Minecraft mc = Minecraft.func_71410_x();
/* 21 */   private static final FontRenderer realRenderer = mc.field_71466_p;
/*    */   private final NBTTagCompound pkgTag;
/* 23 */   private final Pattern stringPattern = Pattern.compile("^\\{\\{BC_PACKAGE_SPECIAL:([0-2])}}$");
/*    */   
/*    */   public PackageFontRenderer(ItemStack packageStack) {
/* 26 */     super(mc.field_71474_y, new ResourceLocation("textures/font/ascii.png"), mc.func_110434_K(), mc.field_71466_p.func_82883_a());
/* 27 */     this.pkgTag = NBTUtils.getItemData(packageStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_78256_a(String s) {
/* 32 */     Matcher m = this.stringPattern.matcher(EnumChatFormatting.func_110646_a(s));
/* 33 */     if (!m.find()) {
/* 34 */       return realRenderer.func_78256_a(s);
/*    */     }
/*    */     
/* 37 */     return 21;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_85187_a(String s, int x, int y, int color, boolean shadow) {
/* 42 */     Matcher m = this.stringPattern.matcher(EnumChatFormatting.func_110646_a(s));
/* 43 */     if (!m.find()) {
/* 44 */       return realRenderer.func_85187_a(s, x, y, color, shadow);
/*    */     }
/*    */     
/* 47 */     int begin = Integer.parseInt(m.group(1)) * 3;
/* 48 */     int rx = x;
/*    */     
/* 50 */     for (int slotPos = begin; slotPos < begin + 3; slotPos++) {
/* 51 */       GL11.glPushMatrix();
/*    */       
/* 53 */       if (this.pkgTag.func_74764_b("item" + slotPos)) {
/* 54 */         ItemStack slotStack = ItemStack.func_77949_a(this.pkgTag.func_74775_l("item" + slotPos));
/* 55 */         if (slotStack != null) {
/* 56 */           GL11.glTranslatef(0.0F, 0.0F, 32.0F);
/* 57 */           GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 58 */           FontRenderer font = slotStack.func_77973_b().getFontRenderer(slotStack);
/* 59 */           itemRender.field_77023_b = 200.0F;
/*    */           
/* 61 */           if (font == null || font instanceof PackageFontRenderer) {
/* 62 */             font = (Minecraft.func_71410_x()).field_71466_p;
/*    */           }
/*    */           
/* 65 */           itemRender.func_82406_b(font, mc.func_110434_K(), slotStack, rx * 2, y * 2);
/* 66 */           itemRender.func_77021_b(font, mc.func_110434_K(), slotStack, rx * 2, y * 2);
/* 67 */           itemRender.field_77023_b = 0.0F;
/*    */         } else {
/* 69 */           realRenderer.func_78276_b("X", rx, y, 16711680);
/*    */         } 
/*    */       } 
/*    */       
/* 73 */       rx += 7;
/*    */       
/* 75 */       GL11.glPopMatrix();
/*    */     } 
/* 77 */     return rx;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-silicon.jar!\buildcraft\silicon\render\PackageFontRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */