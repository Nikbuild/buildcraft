/*    */ package buildcraft.api.transport.pipe;
/*    */ 
/*    */ import buildcraft.api.transport.pluggable.IPlugDynamicRenderer;
/*    */ import buildcraft.api.transport.pluggable.IPluggableStaticBaker;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public enum PipeApiClient
/*    */ {
/* 13 */   INSTANCE;
/*    */   public static IClientRegistry registry;
/*    */   
/*    */   public static interface IClientRegistry {
/*    */     <F extends PipeFlow> void registerRenderer(Class<? extends F> param1Class, IPipeFlowRenderer<F> param1IPipeFlowRenderer);
/*    */     
/*    */     <B extends PipeBehaviour> void registerRenderer(Class<? extends B> param1Class, IPipeBehaviourRenderer<B> param1IPipeBehaviourRenderer);
/*    */     
/*    */     <P extends buildcraft.api.transport.pluggable.PipePluggable> void registerRenderer(Class<? extends P> param1Class, IPlugDynamicRenderer<P> param1IPlugDynamicRenderer);
/*    */     
/*    */     <P extends buildcraft.api.transport.pluggable.PluggableModelKey> void registerBaker(Class<? extends P> param1Class, IPluggableStaticBaker<P> param1IPluggableStaticBaker);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\transport\pipe\PipeApiClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */