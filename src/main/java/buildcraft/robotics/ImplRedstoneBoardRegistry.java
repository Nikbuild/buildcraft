/*    */ package buildcraft.robotics;
/*    */ 
/*    */ import buildcraft.BuildCraftRobotics;
/*    */ import buildcraft.api.boards.RedstoneBoardNBT;
/*    */ import buildcraft.api.boards.RedstoneBoardRegistry;
/*    */ import buildcraft.api.boards.RedstoneBoardRobotNBT;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImplRedstoneBoardRegistry
/*    */   extends RedstoneBoardRegistry
/*    */ {
/*    */   private static class BoardFactory
/*    */   {
/*    */     public RedstoneBoardNBT<?> boardNBT;
/*    */     public int energyCost;
/*    */     
/*    */     private BoardFactory() {}
/*    */   }
/* 29 */   private HashMap<String, BoardFactory> boards = new HashMap<String, BoardFactory>();
/*    */   
/*    */   private RedstoneBoardRobotNBT emptyRobotBoardNBT;
/*    */   
/*    */   public void registerBoardType(RedstoneBoardNBT<?> redstoneBoardNBT, int energyCost) {
/* 34 */     if (BuildCraftRobotics.blacklistedRobots.contains(redstoneBoardNBT.getID())) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     BoardFactory factory = new BoardFactory();
/* 39 */     factory.boardNBT = redstoneBoardNBT;
/* 40 */     factory.energyCost = energyCost;
/*    */     
/* 42 */     this.boards.put(redstoneBoardNBT.getID(), factory);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerBoardClass(RedstoneBoardNBT<?> redstoneBoardNBT, float probability) {
/* 47 */     registerBoardType(redstoneBoardNBT, Math.round(160000.0F / probability));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEmptyRobotBoard(RedstoneBoardRobotNBT redstoneBoardNBT) {
/* 52 */     this.emptyRobotBoardNBT = redstoneBoardNBT;
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardRobotNBT getEmptyRobotBoard() {
/* 57 */     return this.emptyRobotBoardNBT;
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardNBT<?> getRedstoneBoard(NBTTagCompound nbt) {
/* 62 */     return getRedstoneBoard(nbt.func_74779_i("id"));
/*    */   }
/*    */ 
/*    */   
/*    */   public RedstoneBoardNBT<?> getRedstoneBoard(String id) {
/* 67 */     BoardFactory factory = this.boards.get(id);
/*    */     
/* 69 */     if (factory != null) {
/* 70 */       return factory.boardNBT;
/*    */     }
/* 72 */     return (RedstoneBoardNBT<?>)this.emptyRobotBoardNBT;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void registerIcons(IIconRegister par1IconRegister) {
/* 78 */     this.emptyRobotBoardNBT.registerIcons(par1IconRegister);
/* 79 */     for (BoardFactory f : this.boards.values()) {
/* 80 */       f.boardNBT.registerIcons(par1IconRegister);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<RedstoneBoardNBT<?>> getAllBoardNBTs() {
/* 86 */     ArrayList<RedstoneBoardNBT<?>> result = new ArrayList<RedstoneBoardNBT<?>>();
/*    */     
/* 88 */     for (BoardFactory f : this.boards.values()) {
/* 89 */       result.add(f.boardNBT);
/*    */     }
/*    */     
/* 92 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyCost(RedstoneBoardNBT<?> board) {
/* 97 */     return ((BoardFactory)this.boards.get(board.getID())).energyCost;
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-7.1.26-robotics.jar!\buildcraft\robotics\ImplRedstoneBoardRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */