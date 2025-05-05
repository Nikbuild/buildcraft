/*    */ package buildcraft.core.builders.patterns;
/*    */ 
/*    */ import buildcraft.api.blueprints.SchematicBlockBase;
/*    */ import buildcraft.api.blueprints.SchematicMask;
/*    */ import buildcraft.api.statements.IStatementParameter;
/*    */ import buildcraft.core.Box;
/*    */ import buildcraft.core.blueprints.Template;
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
/*    */ public class PatternFrame
/*    */   extends FillerPattern
/*    */ {
/*    */   public PatternFrame() {
/* 22 */     super("frame");
/*    */   }
/*    */ 
/*    */   
/*    */   public Template getTemplate(Box box, World world, IStatementParameter[] parameters) {
/* 27 */     Template template = new Template(box.sizeX(), box.sizeY(), box.sizeZ());
/*    */     
/* 29 */     int xMax = box.sizeX() - 1;
/* 30 */     int zMax = box.sizeZ() - 1;
/*    */     
/* 32 */     for (int it = 0; it < 2; it++) {
/* 33 */       int y = it * (box.sizeY() - 1);
/* 34 */       for (int i = 0; i < template.sizeX; i++) {
/* 35 */         template.put(i, y, 0, (SchematicBlockBase)new SchematicMask(true));
/* 36 */         template.put(i, y, zMax, (SchematicBlockBase)new SchematicMask(true));
/*    */       } 
/*    */       
/* 39 */       for (int k = 0; k < template.sizeZ; k++) {
/* 40 */         template.put(0, y, k, (SchematicBlockBase)new SchematicMask(true));
/* 41 */         template.put(xMax, y, k, (SchematicBlockBase)new SchematicMask(true));
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     for (int h = 1; h < box.sizeY(); h++) {
/* 46 */       template.put(0, h, 0, (SchematicBlockBase)new SchematicMask(true));
/* 47 */       template.put(0, h, zMax, (SchematicBlockBase)new SchematicMask(true));
/* 48 */       template.put(xMax, h, 0, (SchematicBlockBase)new SchematicMask(true));
/* 49 */       template.put(xMax, h, zMax, (SchematicBlockBase)new SchematicMask(true));
/*    */     } 
/*    */     
/* 52 */     return template;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\builders\patterns\PatternFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */