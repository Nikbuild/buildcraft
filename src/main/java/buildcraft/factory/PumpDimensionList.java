/*    */ package buildcraft.factory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import net.minecraftforge.fluids.Fluid;
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
/*    */ public class PumpDimensionList
/*    */ {
/* 24 */   private List<Entry> entries = new LinkedList<Entry>();
/*    */   public PumpDimensionList(String string) {
/* 26 */     for (String entryString : string.trim().split(",")) {
/*    */       
/* 28 */       Entry e = new Entry();
/*    */       
/* 30 */       if (entryString.startsWith("+/")) {
/* 31 */         e.isWhitelist = true;
/* 32 */       } else if (entryString.startsWith("-/")) {
/* 33 */         e.isWhitelist = false;
/*    */       } else {
/* 35 */         throw new RuntimeException("Malformed pumping.controlList entry: " + entryString + " (must start with +/ or -/)");
/*    */       } 
/*    */       
/* 38 */       String secondString = entryString.substring(2);
/* 39 */       int i = secondString.indexOf('/');
/*    */       
/* 41 */       if (i < 0) {
/* 42 */         throw new RuntimeException("Malformed pumping.controlList entry: " + secondString + " (missing second /)");
/*    */       }
/*    */ 
/*    */       
/* 46 */       String dimIDString = secondString.substring(0, i);
/*    */       
/* 48 */       if ("*".equals(dimIDString)) {
/* 49 */         e.matchAnyDim = true;
/*    */       } else {
/* 51 */         e.dimID = Integer.parseInt(dimIDString);
/*    */       } 
/*    */       
/* 54 */       e.fluidName = secondString.substring(i + 1).toLowerCase(Locale.ENGLISH);
/*    */       
/* 56 */       if (e.fluidName.equals("*")) {
/* 57 */         e.matchAnyFluid = true;
/*    */       }
/*    */       
/* 60 */       this.entries.add(0, e);
/*    */     } 
/*    */     
/* 63 */     this.entries = new ArrayList<Entry>(this.entries);
/*    */   }
/*    */ 
/*    */   
/*    */   private class Entry
/*    */   {
/*    */     boolean isWhitelist;
/*    */     String fluidName;
/*    */     int dimID;
/*    */     
/*    */     boolean matches(Fluid fluid, int dim) {
/* 74 */       if (!this.matchAnyFluid && 
/* 75 */         !fluid.getName().equals(this.fluidName)) {
/* 76 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 80 */       if (!this.matchAnyDim && this.dimID != dim) {
/* 81 */         return false;
/*    */       }
/*    */       
/* 84 */       return true;
/*    */     }
/*    */     boolean matchAnyFluid; boolean matchAnyDim;
/*    */     private Entry() {} }
/*    */   public boolean isFluidAllowed(Fluid fluid, int dim) {
/* 89 */     for (Entry e : this.entries) {
/* 90 */       if (e.matches(fluid, dim)) {
/* 91 */         return e.isWhitelist;
/*    */       }
/*    */     } 
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-factory.jar!\buildcraft\factory\PumpDimensionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */