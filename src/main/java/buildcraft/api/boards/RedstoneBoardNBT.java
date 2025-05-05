/*    */ package buildcraft.api.boards;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RedstoneBoardNBT<T>
/*    */ {
/* 18 */   private static Random rand = new Random();
/*    */   
/*    */   public abstract String getID();
/*    */   
/*    */   public abstract void addInformation(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, List<String> paramList, boolean paramBoolean);
/*    */   
/*    */   public abstract String getDisplayName();
/*    */   
/*    */   public abstract IRedstoneBoard<T> create(NBTTagCompound paramNBTTagCompound, T paramT);
/*    */   
/*    */   public abstract String getItemModelLocation();
/*    */   
/*    */   public void createBoard(NBTTagCompound nbt) {
/* 31 */     nbt.func_74778_a("id", getID());
/*    */   }
/*    */   
/*    */   public int getParameterNumber(NBTTagCompound nbt) {
/* 35 */     if (!nbt.func_74764_b("parameters")) {
/* 36 */       return 0;
/*    */     }
/* 38 */     return nbt.func_150295_c("parameters", 10).func_74745_c();
/*    */   }
/*    */ 
/*    */   
/*    */   public float nextFloat(int difficulty) {
/* 43 */     return 1.0F - (float)Math.pow(rand.nextFloat(), (1.0F / difficulty));
/*    */   }
/*    */ }


/* Location:              C:\Users\Nikit\files\buildcraft-api-7.99.8.2.jar!\buildcraft\api\boards\RedstoneBoardNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */