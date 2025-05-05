/*    */ package buildcraft.transport.schematics;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.SchematicTile;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.item.Item;
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
/*    */ public class BptPipeExtension
/*    */ {
/* 20 */   private static final HashMap<Item, BptPipeExtension> bptPipeExtensionRegistry = new HashMap<Item, BptPipeExtension>();
/*    */   
/*    */   public BptPipeExtension(Item i) {
/* 23 */     bptPipeExtensionRegistry.put(i, this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void postProcessing(SchematicTile slot, IBuilderContext context) {}
/*    */ 
/*    */   
/*    */   public void rotateLeft(SchematicTile slot, IBuilderContext context) {}
/*    */ 
/*    */   
/*    */   public static boolean contains(Item i) {
/* 35 */     return bptPipeExtensionRegistry.containsKey(i);
/*    */   }
/*    */   
/*    */   public static BptPipeExtension get(Item i) {
/* 39 */     return bptPipeExtensionRegistry.get(i);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\schematics\BptPipeExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */