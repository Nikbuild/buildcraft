/*    */ package buildcraft.core.builders.patterns;
/*    */ 
/*    */ import buildcraft.api.blueprints.SchematicBlockBase;
/*    */ import buildcraft.api.blueprints.SchematicMask;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.Box;
/*    */ import buildcraft.core.blueprints.BlueprintBase;
/*    */ import buildcraft.core.blueprints.BptBuilderTemplate;
/*    */ import buildcraft.core.blueprints.Template;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatternFlatten
/*    */   extends FillerPattern
/*    */ {
/*    */   public PatternFlatten() {
/* 22 */     super("flatten");
/*    */   }
/*    */ 
/*    */   
/*    */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/* 27 */     int xMin = (int)(box.pMin()).x;
/* 28 */     int yMin = ((box.pMin()).y > 0.0D) ? ((int)(box.pMin()).y - 1) : 0;
/* 29 */     int zMin = (int)(box.pMin()).z;
/*    */     
/* 31 */     int xMax = (int)(box.pMax()).x;
/* 32 */     int yMax = (int)(box.pMax()).y;
/* 33 */     int zMax = (int)(box.pMax()).z;
/*    */     
/* 35 */     Template bpt = new Template(box.sizeX(), yMax - yMin + 1, box.sizeZ());
/*    */     
/* 37 */     if ((box.pMin()).y > 0.0D) {
/* 38 */       for (int x = xMin; x <= xMax; x++) {
/* 39 */         for (int z = zMin; z <= zMax; z++) {
/* 40 */           bpt.put(x - xMin, 0, z - zMin, (SchematicBlockBase)new SchematicMask(true));
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 45 */     return bpt;
/*    */   }
/*    */ 
/*    */   
/*    */   public BptBuilderTemplate getTemplateBuilder(Box box, World world, IStatementParameter[] parameters) {
/* 50 */     int yMin = ((box.pMin()).y > 0.0D) ? ((int)(box.pMin()).y - 1) : 0;
/*    */     
/* 52 */     return new BptBuilderTemplate((BlueprintBase)getTemplate(box, world, parameters), world, box.xMin, yMin, box.zMin);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternFlatten.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */