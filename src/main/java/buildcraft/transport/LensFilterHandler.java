/*    */ package buildcraft.transport;
/*    */ 
/*    */ import buildcraft.api.transport.IPipe;
/*    */ import buildcraft.api.transport.IPipeTile;
/*    */ import buildcraft.api.transport.pluggable.PipePluggable;
/*    */ import buildcraft.transport.pipes.events.PipeEventItem;
/*    */ import buildcraft.transport.pipes.events.PipeEventPriority;
/*    */ import buildcraft.transport.pluggable.LensPluggable;
/*    */ import java.util.LinkedList;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class LensFilterHandler
/*    */ {
/*    */   @PipeEventPriority(priority = -100)
/*    */   public void eventHandler(PipeEventItem.FindDest event) {
/* 17 */     IPipeTile container = event.pipe.getTile();
/* 18 */     LinkedList<ForgeDirection> correctColored = new LinkedList<ForgeDirection>();
/* 19 */     LinkedList<ForgeDirection> notColored = new LinkedList<ForgeDirection>();
/* 20 */     boolean encounteredColor = false;
/* 21 */     int myColor = (event.item.color == null) ? -1 : event.item.color.ordinal();
/*    */     
/* 23 */     for (ForgeDirection dir : event.destinations) {
/* 24 */       boolean hasFilter = false;
/* 25 */       boolean hasLens = false;
/* 26 */       int sideColor = -1;
/* 27 */       int sideLensColor = -1;
/*    */ 
/*    */ 
/*    */       
/* 31 */       PipePluggable pluggable = container.getPipePluggable(dir);
/* 32 */       if (pluggable != null && pluggable instanceof LensPluggable) {
/* 33 */         if (((LensPluggable)pluggable).isFilter) {
/* 34 */           hasFilter = true;
/* 35 */           sideColor = ((LensPluggable)pluggable).color;
/*    */         } else {
/* 37 */           hasLens = true;
/* 38 */           sideLensColor = ((LensPluggable)pluggable).color;
/*    */         } 
/*    */       }
/*    */ 
/*    */       
/* 43 */       IPipe otherPipe = container.getNeighborPipe(dir);
/* 44 */       if (otherPipe != null && otherPipe.getTile() != null) {
/* 45 */         IPipeTile otherContainer = otherPipe.getTile();
/* 46 */         pluggable = otherContainer.getPipePluggable(dir.getOpposite());
/* 47 */         if (pluggable != null && pluggable instanceof LensPluggable && ((LensPluggable)pluggable).isFilter) {
/* 48 */           int otherColor = ((LensPluggable)pluggable).color;
/* 49 */           if (hasFilter && otherColor != sideColor) {
/*    */             continue;
/*    */           }
/* 52 */           if (hasLens) {
/*    */             
/* 54 */             if (sideLensColor == otherColor) {
/* 55 */               hasFilter = false;
/* 56 */               sideColor = -1;
/*    */             } else {
/*    */               continue;
/*    */             } 
/*    */           } else {
/* 61 */             hasFilter = true;
/* 62 */             sideColor = otherColor;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 67 */       if (hasFilter) {
/* 68 */         if (myColor == sideColor) {
/* 69 */           encounteredColor = true;
/* 70 */           correctColored.add(dir);
/*    */         }  continue;
/*    */       } 
/* 73 */       notColored.add(dir);
/*    */     } 
/*    */ 
/*    */     
/* 77 */     event.destinations.clear();
/* 78 */     event.destinations.addAll(encounteredColor ? correctColored : notColored);
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-transport.jar!\buildcraft\transport\LensFilterHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */