/*    */ package buildcraft.core.blueprints;
/*    */ 
/*    */ import buildcraft.api.blueprints.IBuilderContext;
/*    */ import buildcraft.api.blueprints.MappingRegistry;
/*    */ import buildcraft.api.core.IBox;
/*    */ import buildcraft.api.core.Position;
/*    */ import buildcraft.core.Box;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BptContext
/*    */   implements IBuilderContext
/*    */ {
/*    */   public BlueprintReadConfiguration readConfiguration;
/*    */   public Box box;
/*    */   public World world;
/*    */   private MappingRegistry mappingRegistry;
/*    */   
/*    */   BptContext(World world, Box box, MappingRegistry registry) {
/* 27 */     this.world = world;
/* 28 */     this.box = box;
/* 29 */     this.mappingRegistry = registry;
/*    */   }
/*    */ 
/*    */   
/*    */   public Position rotatePositionLeft(Position pos) {
/* 34 */     return new Position((this.box.sizeZ() - 1) - pos.z, pos.y, pos.x);
/*    */   }
/*    */ 
/*    */   
/*    */   public IBox surroundingBox() {
/* 39 */     return (IBox)this.box;
/*    */   }
/*    */ 
/*    */   
/*    */   public World world() {
/* 44 */     return this.world;
/*    */   }
/*    */   
/*    */   public void rotateLeft() {
/* 48 */     this.box = this.box.rotateLeft();
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingRegistry getMappingRegistry() {
/* 53 */     return this.mappingRegistry;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\blueprints\BptContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */