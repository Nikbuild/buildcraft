/*    */ package buildcraft.core.config;
/*    */ 
/*    */ import cpw.mods.fml.client.config.IConfigElement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraftforge.common.config.ConfigCategory;
/*    */ import net.minecraftforge.common.config.ConfigElement;
/*    */ import net.minecraftforge.common.config.Property;
/*    */ 
/*    */ public class BCConfigElement<T>
/*    */   extends ConfigElement<T> {
/*    */   private ConfigCategory cat;
/*    */   private boolean isProp;
/*    */   
/*    */   public BCConfigElement(ConfigCategory ctgy) {
/* 17 */     super(ctgy);
/* 18 */     this.cat = ctgy;
/* 19 */     this.isProp = false;
/*    */   }
/*    */   
/*    */   public BCConfigElement(Property prop) {
/* 23 */     super(prop);
/* 24 */     this.isProp = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<IConfigElement> getChildElements() {
/* 29 */     if (!this.isProp) {
/* 30 */       List<IConfigElement> elements = new ArrayList<IConfigElement>();
/* 31 */       Iterator<ConfigCategory> ccI = this.cat.getChildren().iterator();
/* 32 */       Iterator<Property> pI = this.cat.getOrderedValues().iterator();
/*    */       
/* 34 */       while (ccI.hasNext()) {
/* 35 */         ConfigCategory child = ccI.next();
/* 36 */         if (!child.parent.getQualifiedName().equals(this.cat.getQualifiedName())) {
/*    */           continue;
/*    */         }
/*    */         
/* 40 */         ConfigElement<?> temp = new BCConfigElement(child);
/* 41 */         if (temp.showInGui()) {
/* 42 */           elements.add(temp);
/*    */         }
/*    */       } 
/*    */       
/* 46 */       while (pI.hasNext()) {
/* 47 */         ConfigElement<?> temp = getTypedElement(pI.next());
/* 48 */         if (temp.showInGui()) {
/* 49 */           elements.add(temp);
/*    */         }
/*    */       } 
/*    */       
/* 53 */       return elements;
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-core.jar!\buildcraft\core\config\BCConfigElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */