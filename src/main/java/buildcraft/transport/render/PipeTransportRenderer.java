/*    */ package buildcraft.transport.render;
/*    */ 
/*    */ import buildcraft.transport.Pipe;
/*    */ import buildcraft.transport.PipeTransport;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PipeTransportRenderer<T extends PipeTransport>
/*    */ {
/* 16 */   public static final Map<Class<? extends PipeTransport>, PipeTransportRenderer> RENDERER_MAP = new HashMap<Class<? extends PipeTransport>, PipeTransportRenderer>();
/*    */   
/*    */   public boolean useServerTileIfPresent() {
/* 19 */     return false;
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void bindTexture(ResourceLocation location) {
/* 24 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(location);
/*    */   }
/*    */   
/*    */   public abstract void render(Pipe<T> paramPipe, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat);
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\render\PipeTransportRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */